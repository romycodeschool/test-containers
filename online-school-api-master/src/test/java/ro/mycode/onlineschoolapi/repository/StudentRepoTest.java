package ro.mycode.onlineschoolapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.mycode.onlineschoolapi.OnlineSchoolApiApplication;
import ro.mycode.onlineschoolapi.model.Course;
import ro.mycode.onlineschoolapi.model.Student;
import ro.mycode.onlineschoolapi.security.UserRole;

import javax.transaction.Transactional;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OnlineSchoolApiApplication.class)
class StudentRepoTest {
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    CourseRepo courseRepo;

    @BeforeEach
    public void test() {
        studentRepo.deleteAll();
        courseRepo.deleteAll();
    }

    @Test
    void findStudentsByEmail() {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            students.add(new Student().builder().age(18 + i).email("denis" + i + "@yahoo.com").firstName("Flore" + i).secondName("Denis" + i).userRole(UserRole.STUDENT).password(new BCryptPasswordEncoder().encode("parola")).build());
        }

        studentRepo.saveAll(students);
        assertEquals(18, studentRepo.findStudentsByEmail("denis0@yahoo.com").get().getAge());

    }

//    @Test
//    @Transactional
//    public void bestCourseId() {
//        List<Student> students = new ArrayList<>();
//        List<Course> courses = new ArrayList<>();
//
//
//
//        for (int i = 0; i < 4; i++) {
//            students.add(new Student().builder().age(18 + i).email("denis" + i + "@yahoo.com").firstName("Flore" + i).secondName("Denis" + i).userRole(UserRole.STUDENT).password(new BCryptPasswordEncoder().encode("parola")).build());
//        }
//        studentRepo.saveAllAndFlush(students);
//
//
//
//        for (int i = 0; i < 4; i++) {
//            courses.add(new Course().builder().id(i + 1L).name("Name" + i).department("Depart" + i).build());
//        }
//        courseRepo.saveAllAndFlush(courses);
//
//
//        Optional<Student> x = studentRepo.findStudentsByEmail("denis0@yahoo.com");
//
//        Optional<Course> course=courseRepo.getAllCoursesByDepartamentAndName("Depart0","Name0");
//
//        x.get().addCourse(course.get());
//
//        studentRepo.saveAndFlush(x.get());
//
//        assertEquals(courses.get(0).getId(),studentRepo.bestCourseId().get());
//
//    }



}