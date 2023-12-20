package ro.mycode.onlineschoolapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ro.mycode.onlineschoolapi.OnlineSchoolApiApplication;
import ro.mycode.onlineschoolapi.dto.EnrollRequestStudentToCourse;
import ro.mycode.onlineschoolapi.dto.RequestDTORequest;
import ro.mycode.onlineschoolapi.exception.RequestAlreadyExist;
import ro.mycode.onlineschoolapi.exception.StatusCourseException;
import ro.mycode.onlineschoolapi.exception.StudentDosentExist;
import ro.mycode.onlineschoolapi.model.Course;
import ro.mycode.onlineschoolapi.model.Request;
import ro.mycode.onlineschoolapi.model.Student;
import ro.mycode.onlineschoolapi.repository.CourseRepo;
import ro.mycode.onlineschoolapi.repository.RequestRepository;
import ro.mycode.onlineschoolapi.repository.StudentRepo;
import ro.mycode.onlineschoolapi.services.RequestService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = OnlineSchoolApiApplication.class)
class RequestServiceTest {

    @InjectMocks
    private RequestService requestService;

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private CourseRepo courseRepo;

    @BeforeEach
    public void test() {
        studentRepo.deleteAll();
        courseRepo.deleteAll();
        requestRepository.deleteAll();
    }

    @Test
    void getAllRequestTest() {
        Request request1 = new Request();
        request1.setId(1L);
        request1.setStudentId(1L);
        request1.setCourseId(1L);
        request1.setStatus(true);

        Request request2 = new Request();
        request2.setId(2L);
        request2.setStudentId(2L);
        request2.setCourseId(2L);
        request2.setStatus(false);

        List<Request> requests = new ArrayList<>();
        requests.add(request1);
        requests.add(request2);
        Student student1 = new Student();
        student1.setId(1L);
        student1.setEmail("student1@example.com");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setEmail("student2@example.com");
        Course course1 = new Course();
        course1.setId(1L);
        course1.setName("Course 1");

        Course course2 = new Course();
        course2.setId(2L);
        course2.setName("Course 2");
        doReturn(Optional.of(student1)).when(studentRepo).findById(1L);
        doReturn(Optional.of(student2)).when(studentRepo).findById(2L);
        doReturn(Optional.of(course1)).when(courseRepo).findById(1L);
        doReturn(Optional.of(course2)).when(courseRepo).findById(2L);
        doReturn(requests).when(requestRepository).findAll();


//        when(studentRepo.findById(1L)).thenReturn(Optional.of(student1));
//        when(studentRepo.findById(2L)).thenReturn(Optional.of(student2));
//        when(courseRepo.findById(1L)).thenReturn(Optional.of(course1));
//        when(courseRepo.findById(2L)).thenReturn(Optional.of(course2));
//        when(requestRepository.findAll()).thenReturn(requests);

        assertEquals(2, requestService.getAllRequest().get().size());
    }


    @Test
    void addRequestTest() {
        EnrollRequestStudentToCourse enrollRequest = EnrollRequestStudentToCourse.builder()
                .idStudent(1L)
                .idCourse(1L)
                .build();

        when(requestRepository.getRequestByCourseIdAndStudentId(1L, 1L)).thenReturn(Optional.empty());
        Request savedRequest = new Request(enrollRequest.getIdStudent(), enrollRequest.getIdCourse());
        requestRepository.saveAndFlush(savedRequest);

        requestService.addRequest(enrollRequest);
        Optional<Request> actualRequestOptional = requestRepository.findById(savedRequest.getId());

        assertFalse(actualRequestOptional.isPresent());
    }



    @Test
    void addRequestException() {
        EnrollRequestStudentToCourse enrollRequest = EnrollRequestStudentToCourse.builder()
                .idStudent(1L)
                .idCourse(1L)
                .build();

        when(requestRepository.getRequestByCourseIdAndStudentId(1L, 1L)).thenReturn(Optional.of(new Request()));

        assertThrows(RequestAlreadyExist.class, () -> requestService.addRequest(enrollRequest));
    }

    @Test
    void removeRequestTest() {
        EnrollRequestStudentToCourse enrollRequest = EnrollRequestStudentToCourse.builder()
                .idStudent(1L)
                .idCourse(1L)
                .build();

        Request existingRequest = new Request();
        when(requestRepository.getRequestByCourseIdAndStudentId(1L, 1L)).thenReturn(Optional.of(existingRequest));

        requestService.removeRequest(enrollRequest);

        assertEquals(null,requestRepository.getRequestByCourseIdAndStudentId(1L, 1L).get().getStatus());
    }

    @Test
    void removeRequestNotFoundTest() {
        EnrollRequestStudentToCourse enrollRequest = EnrollRequestStudentToCourse.builder()
                .idStudent(1L)
                .idCourse(1L)
                .build();

        when(requestRepository.getRequestByCourseIdAndStudentId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(RequestAlreadyExist.class, () -> requestService.removeRequest(enrollRequest));
    }


    @Test
    void acceptRequestTest() {
        RequestDTORequest requestDTO = new RequestDTORequest(1L, 1L, 1L, false, "student1@example.com", "Course 1");

        Student student = new Student();
        student.setId(requestDTO.studentId());

        Course course = new Course();
        course.setId(requestDTO.courseId());

        Request request = new Request();
        request.setStatus(false);

        when(studentRepo.findById(requestDTO.studentId())).thenReturn(Optional.of(student));
        when(courseRepo.findById(requestDTO.courseId())).thenReturn(Optional.of(course));
        when(requestRepository.getRequestByCourseIdAndStudentId(requestDTO.courseId(), requestDTO.studentId())).thenReturn(Optional.of(request));

        requestService.acceptRequest(requestDTO);

        assertTrue(request.getStatus());
    }

    @Test
    void deniedRequestTest() {
        RequestDTORequest requestDTO = new RequestDTORequest(1L, 1L, 1L, true, "student1@example.com", "Course 1");

        Request request = new Request();
        request.setStatus(true);

        when(requestRepository.getRequestByCourseIdAndStudentId(requestDTO.courseId(), requestDTO.studentId())).thenReturn(Optional.of(request));

        requestService.deniedRequest(requestDTO);

        assertFalse(request.getStatus());
    }
    @Test
    void acceptRequestStudentDosentExist() {
        RequestDTORequest requestDTO = new RequestDTORequest(1L, 1L, 1L, false, "student1@example.com", "Course 1");

        when(studentRepo.findById(requestDTO.studentId())).thenReturn(Optional.empty());

        assertThrows(StudentDosentExist.class, () -> requestService.acceptRequest(requestDTO));
    }

    @Test
    void acceptRequestException() {
        RequestDTORequest requestDTO = new RequestDTORequest(1L, 1L, 1L, false, "student1@example.com", "Course 1");

        Student student = new Student();
        student.setId(requestDTO.studentId());

        when(studentRepo.findById(requestDTO.studentId())).thenReturn(Optional.of(student));
        when(courseRepo.findById(requestDTO.courseId())).thenReturn(Optional.empty());

        assertThrows(StatusCourseException.class, () -> requestService.acceptRequest(requestDTO));
    }

    @Test
    void deniedRequestAlreadyExist() {
        RequestDTORequest requestDTO = new RequestDTORequest(1L, 1L, 1L, true, "student1@example.com", "Course 1");

        when(requestRepository.getRequestByCourseIdAndStudentId(requestDTO.courseId(), requestDTO.studentId())).thenReturn(Optional.empty());

        assertThrows(RequestAlreadyExist.class, () -> requestService.deniedRequest(requestDTO));
    }

}
