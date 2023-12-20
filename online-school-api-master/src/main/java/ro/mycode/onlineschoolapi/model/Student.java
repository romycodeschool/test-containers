package ro.mycode.onlineschoolapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ro.mycode.onlineschoolapi.security.UserRole;


import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Table(name = "student")
@Entity(name = "Student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class Student implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "student_sequence")
    Long id;

    @Column(name = "first_name",
            nullable = false)
    String firstName;

    @Column(name = "second_name",
            nullable = false)
    String secondName;

    @Column(name = "email",
            nullable = false)
    @Email
    String email;

    @DecimalMax(value = "25", message = "Cursul este alocat persoanelor sub 25 de ani")
    @Column(name = "age",
            nullable = false)
    double age;
    @Column(name = "password",
            nullable = false)
    String password;

    @Enumerated
    @Column(name = "role",
            nullable = false)
    UserRole userRole;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL

    )
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    @Builder.Default
    List<Book> books = new ArrayList<>();
    public void addBook(Book book) {
        this.books.add(book);
        book.setStudent(this);
    }
    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL

    )
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    @Builder.Default
    List<Image> images = new ArrayList<>();
    public void addImage(Image image) {
        this.images.add(image);
        image.setStudent(this);
    }

    public Student(String firstName, String secondName, String email, double age, String password,UserRole role) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.age = age;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.userRole=role;
    }

    public Student(String firstName, String secondName, String email, double age, String password) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.age = age;
        this.password = new BCryptPasswordEncoder().encode(password);
    }


    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "enrolled_coruse", joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")})
    @Builder.Default
    private List<Course> enrolledCourses=new ArrayList<>();

    public void addCourse(Course course){
        enrolledCourses.add(course);
    }

    public void removeCourse(Course course){
        enrolledCourses.remove(course);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", role='" + userRole.toString() + '\'' +
                '}';
    }



    public boolean vfExistsBook(Book book) {
        return this.books.contains(book);
    }

    public boolean vfExistCourse(Course course){
        for (Course x : enrolledCourses){
                if(x.getId()==course.getId()){
                  return true;
            }
        }
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return userRole.getGrantedAuthorities();

    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
