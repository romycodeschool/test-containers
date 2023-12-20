package ro.mycode.onlineschoolapi.repository;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.mycode.onlineschoolapi.OnlineSchoolApiApplication;
import ro.mycode.onlineschoolapi.model.Book;
import ro.mycode.onlineschoolapi.model.Course;
import ro.mycode.onlineschoolapi.model.Student;
import ro.mycode.onlineschoolapi.security.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OnlineSchoolApiApplication.class)
class CourseRepoTest {

    @Autowired
    StudentRepo studentRepo;
    @Autowired
    CourseRepo courseRepo;

    @BeforeEach
    public void deleteAllRepo(){
        studentRepo.deleteAll();
        courseRepo.deleteAll();
    }

    @Test
    void getCoursesOrderByDepartmentAsc() {
        Faker faker = new Faker();
        List<Course> coureses = new ArrayList<>();
        Student x = new Student().builder().age(18).email("denis@yahoo.com").firstName("Flore").secondName("Denis").userRole(UserRole.STUDENT).password(new BCryptPasswordEncoder().encode("parola")).build();

        studentRepo.saveAndFlush(x);


        for (int i = 0; i < 3; i++) {
            coureses.add(new Course().builder().name("Name"+(3-i)).department("Depart"+(3-i)).build());
        }
        courseRepo.saveAllAndFlush(coureses);


        Optional<Student> find = studentRepo.findStudentsByEmail("denis@yahoo.com");
        List<Course> courseRepoAll = courseRepo.findAll();
        find.get().addCourse(courseRepoAll.get(0));
        find.get().addCourse(courseRepoAll.get(1));
        find.get().addCourse(courseRepoAll.get(2));
        assertEquals("Depart1", courseRepo.getCoursesOrderByDepartmentAsc().get().get(0).getDepartment());

    }

}