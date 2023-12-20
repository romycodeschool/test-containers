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
import ro.mycode.onlineschoolapi.model.Request;
import ro.mycode.onlineschoolapi.model.Student;
import ro.mycode.onlineschoolapi.security.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OnlineSchoolApiApplication.class)
class RequestRepositoryTest {

@Autowired
StudentRepo studentRepo;
@Autowired
CourseRepo courseRepo;

@Autowired
RequestRepository requestRepository;

    @BeforeEach
    public void test() {
        studentRepo.deleteAll();
        courseRepo.deleteAll();
        requestRepository.deleteAll();
    }

    @Test
    void getRequestByStudentIdAndCourseIdTest(){
        List<Student> students = new ArrayList<>();
        List<Course> courses = new ArrayList<>();



        for (int i = 0; i < 4; i++) {
            students.add(new Student().builder().age(18 + i).email("denis" + i + "@yahoo.com").firstName("Flore" + i).secondName("Denis" + i).userRole(UserRole.STUDENT).password(new BCryptPasswordEncoder().encode("parola")).build());
        }
        studentRepo.saveAllAndFlush(students);



        for (int i = 0; i < 4; i++) {
            courses.add(new Course().builder().id(i + 1L).name("Name" + i).department("Depart" + i).build());
        }
        courseRepo.saveAllAndFlush(courses);

        Optional<Request> requests = Optional.of(new Request().builder().courseId(1L).studentId(2L).status(true).build());

        requestRepository.saveAndFlush(requests.get());

        assertEquals(true,requestRepository.getRequestByCourseIdAndStudentId(1L,2L).get().getStatus());
    }

    @Test
    void removeByCourseIdAndStudentIdTest() {
        Course course = new Course();
        course.setName("Math");
        course.setDepartment("Science");
        courseRepo.saveAndFlush(course);

        Student student = new Student();
        student.setFirstName("John");
        student.setSecondName("Doe");
        student.setEmail("john@example.com");
        student.setAge(20);
        student.setPassword("password");
        student.setUserRole(UserRole.STUDENT);
        studentRepo.saveAndFlush(student);

        Request request = new Request();
        request.setCourseId(course.getId());
        request.setStudentId(student.getId());
        requestRepository.saveAndFlush(request);

        requestRepository.removeByCourseIdAndStudentId(course.getId(), student.getId());

        assertEquals(null, requestRepository.findById(request.getId()).orElse(null));
    }


}