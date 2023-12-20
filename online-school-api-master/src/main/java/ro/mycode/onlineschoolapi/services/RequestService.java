package ro.mycode.onlineschoolapi.services;

import org.springframework.stereotype.Service;
import ro.mycode.onlineschoolapi.dto.EnrollRequestStudentToCourse;
import ro.mycode.onlineschoolapi.dto.RequestDTO;
import ro.mycode.onlineschoolapi.dto.RequestDTORequest;
import ro.mycode.onlineschoolapi.dto.StudentDTO;
import ro.mycode.onlineschoolapi.exception.RequestAlreadyExist;
import ro.mycode.onlineschoolapi.exception.StatusCourseException;
import ro.mycode.onlineschoolapi.exception.StudentDosentExist;
import ro.mycode.onlineschoolapi.model.Course;
import ro.mycode.onlineschoolapi.model.Request;
import ro.mycode.onlineschoolapi.model.Student;
import ro.mycode.onlineschoolapi.repository.CourseRepo;
import ro.mycode.onlineschoolapi.repository.RequestRepository;
import ro.mycode.onlineschoolapi.repository.StudentRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    private RequestRepository requestRepository;
    private StudentRepo studentRepo;

    private CourseRepo courseRepo;


    public RequestService(RequestRepository requestRepository, StudentRepo studentRepo, CourseRepo courseRepo) {
        this.requestRepository = requestRepository;
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
    }




    public Optional<List<RequestDTORequest>> getAllRequest(){
        Optional<List<Request>> listOfRequest = Optional.of(requestRepository.findAll());
        List<RequestDTORequest> list= new ArrayList<>();
        for(Request x: listOfRequest.get()){
            Student t= studentRepo.findById(x.getStudentId()).get();
            Course c = courseRepo.findById(x.getCourseId()).get();
            list.add(new RequestDTORequest(x.getId(),x.getStudentId(),x.getCourseId(),x.getStatus(),t.getEmail(),c.getName()));
        }

        return Optional.of(list);
    }

    public void addRequest(EnrollRequestStudentToCourse enrollRequestStudentToCourse) {
        Optional<Request> request = requestRepository.getRequestByCourseIdAndStudentId(enrollRequestStudentToCourse.getIdCourse(), enrollRequestStudentToCourse.getIdStudent());
        if (request.isEmpty()) {
            Request requestSave = new Request(enrollRequestStudentToCourse.getIdStudent(), enrollRequestStudentToCourse.getIdCourse());
            requestRepository.saveAndFlush(requestSave);
        } else {
            throw new RequestAlreadyExist("Aveti deja un request pentru acest curs!");
        }
    }

    public void removeRequest(EnrollRequestStudentToCourse enrollRequestStudentToCourse) {
        Optional<Request> request = requestRepository.getRequestByCourseIdAndStudentId(enrollRequestStudentToCourse.getIdCourse(), enrollRequestStudentToCourse.getIdStudent());
        if (!request.isEmpty()) {
            requestRepository.removeByCourseIdAndStudentId(enrollRequestStudentToCourse.getIdCourse(), enrollRequestStudentToCourse.getIdStudent());
        } else {
            throw new RequestAlreadyExist("Nu exista acest request!");
        }
    }

    public void acceptRequest(RequestDTORequest requestDTO) {

        Optional<Student> student = studentRepo.findById(requestDTO.studentId());
        Optional<Request> request=requestRepository.getRequestByCourseIdAndStudentId(requestDTO.courseId(), requestDTO.studentId());
        if (!student.isEmpty()) {
            Optional<Course> course = courseRepo.findById(requestDTO.courseId());
            if (!course.isEmpty()) {
                student.get().addCourse(course.get());
                request.get().setStatus(true);
                requestRepository.saveAndFlush(request.get());
            } else {
                throw new StatusCourseException("Cursul nu exista !");
            }
        } else {
            throw new StudentDosentExist("Studentul nu exista !");
        }
    }

    public void deniedRequest(RequestDTORequest requestDTORequest) {
        Optional<Request> request = requestRepository.getRequestByCourseIdAndStudentId(requestDTORequest.courseId(), requestDTORequest.studentId());
        if (!request.isEmpty()) {
            request.get().setStatus(false);
            requestRepository.saveAndFlush(request.get());
        } else {
            throw new RequestAlreadyExist("Nu exista acest request!");
        }
    }

}
