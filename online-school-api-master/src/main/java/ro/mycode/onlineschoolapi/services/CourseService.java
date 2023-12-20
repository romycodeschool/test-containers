package ro.mycode.onlineschoolapi.services;

import org.springframework.stereotype.Service;
import ro.mycode.onlineschoolapi.dto.CourseRequest;
import ro.mycode.onlineschoolapi.dto.EnrollRequestStudentToCourse;
import ro.mycode.onlineschoolapi.dto.LoginResponse;
import ro.mycode.onlineschoolapi.exception.EmptyDataBase;
import ro.mycode.onlineschoolapi.exception.RequestAlreadyExist;
import ro.mycode.onlineschoolapi.exception.StudentDosentExist;
import ro.mycode.onlineschoolapi.model.Book;
import ro.mycode.onlineschoolapi.model.Course;
import ro.mycode.onlineschoolapi.model.Request;
import ro.mycode.onlineschoolapi.model.Student;
import ro.mycode.onlineschoolapi.repository.CourseRepo;
import ro.mycode.onlineschoolapi.repository.StudentRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private CourseRepo courseRepo;
    private StudentRepo studentRepo;

    public CourseService(CourseRepo courseRepo, StudentRepo studentRepo) {
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;
    }

    public List<Course> getAllCourse() {
        List<Course> courseRepoAll = courseRepo.findAll();

        if (courseRepoAll.size() == 0) {
            throw new EmptyDataBase("Baza de date este goala !");
        }
        return courseRepoAll;
    }

    public List<Course> getAllCourseByStudent(String email) {
        Optional<Student> s = studentRepo.findStudentsByEmail(email);
        if (!s.isEmpty() && s.get().getEnrolledCourses()!=null) {
            return s.get().getEnrolledCourses();
        } else {
            throw new StudentDosentExist("Studentul nu exista !");
        }
    }
}
