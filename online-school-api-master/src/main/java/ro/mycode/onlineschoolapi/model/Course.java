package ro.mycode.onlineschoolapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Table(name = "course")
@Entity(name = "Course")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class Course {

    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    private Long id;


    @NotNull
    @Size(min = 2, message = "Numele are cel putin 2 litere!")
    private String name;

    @NotNull
    @Size(min = 2, message = "Departamentul are cel putin 2 litere !")
    private String department;


    @ManyToMany(mappedBy = "enrolledCourses", fetch = FetchType.EAGER)
    @JsonBackReference
    List<Student> students = new ArrayList<>();

    public Course(String name, String department) {
        this.name=name;
        this.department=department;
    }

    @Override
    public boolean equals(Object o) {
        Course course = (Course) o;
        return course.getName().compareTo(this.getName()) == 0 && course.getDepartment().compareTo(this.getDepartment()) == 0;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }


}
