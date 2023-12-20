package ro.mycode.onlineschoolapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.mycode.onlineschoolapi.dto.CourseRequest;
import ro.mycode.onlineschoolapi.dto.CreateBookRequest;
import ro.mycode.onlineschoolapi.dto.EnrollRequestStudentToCourse;
import ro.mycode.onlineschoolapi.dto.StudentDTO;
import ro.mycode.onlineschoolapi.exception.*;
import ro.mycode.onlineschoolapi.model.Book;
import ro.mycode.onlineschoolapi.model.Course;
import ro.mycode.onlineschoolapi.model.Student;
import ro.mycode.onlineschoolapi.repository.BookRepository;
import ro.mycode.onlineschoolapi.repository.CourseRepo;
import ro.mycode.onlineschoolapi.repository.StudentRepo;
import ro.mycode.onlineschoolapi.security.UserRole;
import ro.mycode.onlineschoolapi.services.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepo studentRepo;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CourseRepo courseRepo;

    @InjectMocks
    private StudentService studentService;

    @Captor
    ArgumentCaptor<String> studentFiledsArgumentCapture;

    @BeforeEach
    public void test() {
        studentRepo.deleteAll();
        bookRepository.deleteAll();
        courseRepo.deleteAll();
    }

    @Test
    public void getAllStudent() {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            students.add(new Student().builder().age(18 + i).email("denis" + i + "@yahoo.com").firstName("Flore" + i).secondName("Denis" + i).build());
        }
        doReturn(students).when(studentRepo).findAll();
        assertEquals(students, studentService.getAllStudents());
    }

    @Test
    public void getAllStudentException() {
        doReturn(new ArrayList<>()).when(studentRepo).findAll();
        assertThrows(EmptyDataBase.class, () -> {
            studentService.getAllStudents();
        });
    }


//    @Test
//    void addStudent() {
//        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
//        Optional<Student> student = Optional.of(s);
//        studentService.addStudent(s);
//        doReturn(student).when(studentRepo).findStudentsByEmail(s.getEmail());
//
//        assertEquals("Flore", studentRepo.findStudentsByEmail(s.getEmail()).get().getFirstName());
//    }

    @Test
    void removeStudent() {
        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        Optional<Student> student = Optional.of(s);
        doReturn(student).when(studentRepo).findStudentsByEmail(s.getEmail());
        studentService.removeStudent(s.getEmail());
        verify(studentRepo, times(1)).removeByEmail(studentFiledsArgumentCapture.capture());
        assertEquals("denis@yahoo.com", studentFiledsArgumentCapture.getValue());
    }

    @Test
    void removeStudentException() {
        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        doReturn(Optional.empty()).when(studentRepo).findStudentsByEmail(s.getEmail());
        assertThrows(StudentDosentExist.class, () -> {
            studentService.removeStudent(s.getEmail());
        });
    }

//    @Test
//    void addStudentException() {
//        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
//        Optional<Student> student = Optional.of(s);
//
//        doReturn(student).when(studentRepo).findStudentsByEmail(s.getEmail());
//        assertThrows(StudentAlreadyExist.class, () -> {
//            studentService.addStudent(s);
//        });
//    }

    @Test
    void addBook() {
        CreateBookRequest createBookRequest = new CreateBookRequest().builder().idStudent(1L).stars(5L).title("Harry Potter").price(20).author("Flore Denis").build();
        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        Optional<Student> student = Optional.of(s);
        doReturn(student).when(studentRepo).findById(createBookRequest.getIdStudent());
        studentService.addBook(createBookRequest);
        assertEquals("Harry Potter", studentRepo.findById(createBookRequest.getIdStudent()).get().getBooks().get(0).getTitle());
    }

    @Test
    void addBookException() {
        CreateBookRequest createBookRequest = new CreateBookRequest().builder().idStudent(1L).stars(5L).title("Harry Potter").price(20).author("Flore Denis").build();
        doReturn(Optional.empty()).when(studentRepo).findById(createBookRequest.getIdStudent());
        assertThrows(StudentDosentExist.class, () -> {
            studentService.addBook(createBookRequest);
        });
    }

    @Test
    void addBookException2() {
        CreateBookRequest createBookRequest = new CreateBookRequest().builder().idStudent(1L).stars(5L).title("Harry Potter").price(20).author("Flore Denis").build();
        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        Optional<Student> student = Optional.of(s);
        doReturn(student).when(studentRepo).findById(createBookRequest.getIdStudent());
        Book book = Book.builder().
                title(createBookRequest.getTitle()).
                author(createBookRequest.getAuthor())
                .price(createBookRequest.getPrice()).stars(createBookRequest.getStars()).build();
        Optional<Book> bookopt = Optional.of(book);
        doReturn(bookopt).when(bookRepository).getBookByStudentAndTitle(createBookRequest.getIdStudent(), createBookRequest.getTitle());
        assertThrows(BookDosentExist.class, () -> {
            studentService.addBook(createBookRequest);
        });
    }

    @Test
    void addCourse() {
        CourseRequest courseRequest = new CourseRequest("IT", "Java Developer", 1L);

        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        Optional<Student> student = Optional.of(s);
        doReturn(student).when(studentRepo).findById(courseRequest.studentId());
        Course course = new Course().builder().department("IT").name("Java Developer").build();
        Optional<Course> courseOptional = Optional.of(course);
        doReturn(courseOptional).when(courseRepo).getCourseByDepartamentAndName(courseRequest.department(),courseRequest.name());
        studentService.addEnrolment(courseRequest);
        assertEquals("Java Developer", student.get().getEnrolledCourses().get(0).getName());
    }




    @Test
    void addCourseException2() {
        CourseRequest courseRequest = new CourseRequest("IT", "Java Developer", 1L);

        Course course = new Course().builder().department("IT").name("Java Developer").build();
        Optional<Course> courseOptional = Optional.of(course);
        doReturn(courseOptional).when(courseRepo).getCourseByDepartamentAndName(courseRequest.department(),courseRequest.name());
        doReturn(Optional.empty()).when(studentRepo).findById(courseRequest.studentId());

        assertThrows(StudentDosentExist.class, () -> {
            studentService.addEnrolment(courseRequest);
        });
    }

    @Test
    void addCourseException3() {
        CourseRequest courseRequest = new CourseRequest("IT", "Java Developer", 1L);

        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        Optional<Student> student = Optional.of(s);
        doReturn(student).when(studentRepo).findById(courseRequest.studentId());
        Course course = new Course().builder().department("IT").name("Java Developer").build();
        Optional<Course> courseOptional = Optional.of(course);
        doReturn(courseOptional).when(courseRepo).getCourseByDepartamentAndName(courseRequest.department(),courseRequest.name());
        studentService.addEnrolment(courseRequest);
        assertThrows(StatusCourseException.class, () -> {
            studentService.addEnrolment(courseRequest);
        });
    }

    @Test
    void removeEnrolment() {
        CourseRequest courseRequest=new CourseRequest("Algebra","Matematica",1L);
        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        Optional<Student> student = Optional.of(s);
        doReturn(student).when(studentRepo).findById(courseRequest.studentId());
        Course course = new Course().builder().department("IT").name("Java Developer").build();
        Optional<Course> courseOptional = Optional.of(course);
        doReturn(courseOptional).when(courseRepo).getCourseByDepartamentAndName(courseRequest.department(),courseRequest.name());
        studentService.addEnrolment(courseRequest);
        studentService.removeEnrolment(courseRequest);
        assertEquals(new ArrayList<>(), student.get().getEnrolledCourses());
    }

    @Test
    void removeEnrolmentException() {
        CourseRequest courseRequest=new CourseRequest("Algebra","Matematica",1L);

        doReturn(Optional.empty()).when(studentRepo).findById(courseRequest.studentId());
        assertThrows(StudentDosentExist.class, () -> {
            studentService.removeEnrolment(courseRequest);
        });
    }

    @Test
    void removeEnrolmentException2() {
        CourseRequest courseRequest=new CourseRequest("Algebra","Matematica",1L);

        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        Optional<Student> student = Optional.of(s);
        doReturn(student).when(studentRepo).findById(courseRequest.studentId());
        Course course = new Course().builder().department("IT").name("Java Developer").build();
        Optional<Course> courseOptional = Optional.of(course);
        doReturn(courseOptional).when(courseRepo).getCourseByDepartamentAndName(courseRequest.department(),courseRequest.name());
        assertThrows(StatusCourseException.class, () -> {
            studentService.removeEnrolment(courseRequest);
        });
    }

    @Test
    void removeEnrolmentException3() {
        CourseRequest courseRequest=new CourseRequest("Algebra","Matematica",1L);

        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        Optional<Student> student = Optional.of(s);
        doReturn(student).when(studentRepo).findById(courseRequest.studentId());
        Course course = new Course().builder().department("IT").name("Java Developer").build();
        doReturn(Optional.empty()).when(courseRepo).getCourseByDepartamentAndName(courseRequest.department(),courseRequest.name());
        assertThrows(StatusCourseException.class, () -> {
            studentService.removeEnrolment(courseRequest);
        });
    }


    @Test
    void bestCourse() {
        Course course = new Course().builder().id(1L).department("IT").name("Java Developer").build();
        Optional<Long> courseOptional = Optional.of(course.getId());
        doReturn(courseOptional).when(studentRepo).bestCourseId();
        when(courseRepo.findById(studentRepo.bestCourseId().get())).thenReturn(Optional.of(course));
        assertEquals("IT", studentService.bestCourse().getDepartment());
    }


    @Test
    void findStudentByIdTest() {
        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        doReturn(Optional.of(s)).when(studentRepo).findById(1L);
        assertEquals(21, studentService.getStudentById(1L).get().age());
    }

    @Test
    void findStudentByIdTestException() {
        doReturn(Optional.empty()).when(studentRepo).findById(1L);
        assertThrows(StudentDosentExist.class, () -> {
            studentService.getStudentById(1L);
        });
    }

    @Test
    void findStudentByEmailTest() {
        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        doReturn(Optional.of(s)).when(studentRepo).findStudentsByEmail("denis@yahoo.com");
        assertEquals(21, studentService.findStudentByEmail("denis@yahoo.com").getAge());
    }

    @Test
    void findStudentByEmailException() {
        doReturn(Optional.empty()).when(studentRepo).findStudentsByEmail("denis@yahoo.com");
        assertThrows(StudentDosentExist.class, () -> {
            studentService.findStudentByEmail("denis@yahoo.com");
        });
    }

    @Test
    void addStudentTest() {
        StudentDTO studentDTO = new StudentDTO("Flore", "Denis", "denis@yahoo.com", 21, "parola","STUDENT");
        studentService.addStudent(studentDTO);
        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        doReturn(Optional.of(s)).when(studentRepo).findStudentsByEmail("denis@yahoo.com");
        assertEquals(21, studentService.findStudentByEmail("denis@yahoo.com").getAge());
    }



    @Test
    void addStudentTestException() {
        StudentDTO studentDTO = new StudentDTO("Flore", "Denis", "denis@yahoo.com", 21, "parola","STUDENT");

        doReturn(Optional.of(new Student())).when(studentRepo).findStudentsByEmail("denis@yahoo.com");

        assertThrows(StudentAlreadyExist.class, () -> {
            studentService.addStudent(studentDTO);
        });
    }



}