package ro.mycode.onlineschoolapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ro.mycode.onlineschoolapi.OnlineSchoolApiApplication;
import ro.mycode.onlineschoolapi.exception.EmptyDataBase;
import ro.mycode.onlineschoolapi.exception.StudentDosentExist;
import ro.mycode.onlineschoolapi.model.Book;
import ro.mycode.onlineschoolapi.model.Course;
import ro.mycode.onlineschoolapi.model.Student;
import ro.mycode.onlineschoolapi.repository.BookRepository;
import ro.mycode.onlineschoolapi.repository.CourseRepo;
import ro.mycode.onlineschoolapi.repository.StudentRepo;
import ro.mycode.onlineschoolapi.services.BookService;
import ro.mycode.onlineschoolapi.services.CourseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = OnlineSchoolApiApplication.class)
class CourseServiceTest {
    @Mock
    private CourseRepo courseRepo;

    @Mock
    private StudentRepo studentRepo;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    public void test() {
        courseRepo.deleteAll();
        studentRepo.deleteAll();
    }

    @Test
    void getAllCourse() {
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            courses.add(new Course().builder().department("IT"+i).name("Name"+i).build());
        }

        doReturn(courses).when(courseRepo).findAll();

        assertEquals(3, courseService.getAllCourse().size());
    }

    @Test
    void getAllBooksException() {
        doReturn(new ArrayList<>()).when(courseRepo).findAll();

        assertThrows(EmptyDataBase.class, () -> {
            courseService.getAllCourse();
        });
    }

    @Test
    void getAllBooksByStudent(){
        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();

        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            courses.add(new Course().builder().department("IT"+i).name("Name"+i).build());
        }
        s.setEnrolledCourses(courses);
        studentRepo.saveAndFlush(s);
        doReturn(Optional.of(s)).when(studentRepo).findStudentsByEmail(s.getEmail());
        assertEquals(5, courseService.getAllCourseByStudent(s.getEmail()).size());
    }
    @Test
    void getAllBooksByStudentException(){

        doReturn(Optional.empty()).when(studentRepo).findStudentsByEmail("denis@yahoo.com");
        assertThrows(StudentDosentExist.class,()->{
             courseService.getAllCourseByStudent("denis@yahoo.com");
        });
    }




}