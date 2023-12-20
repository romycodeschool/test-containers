package ro.mycode.onlineschoolapi.rest;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ro.mycode.onlineschoolapi.dto.LoginResponse;
import ro.mycode.onlineschoolapi.model.Book;
import ro.mycode.onlineschoolapi.model.Course;
import ro.mycode.onlineschoolapi.model.Student;
import ro.mycode.onlineschoolapi.services.CourseService;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/courses")
public class CourseRest {


    private CourseService courseService;

    public CourseRest(CourseService courseService) {
        this.courseService = courseService;
    }


    @GetMapping("/all")
    @PreAuthorize("hasAuthority('course:read')")
    public ResponseEntity<List<Course>> courseList(){
        List<Course> coursesList=courseService.getAllCourse();
        return new ResponseEntity<>(coursesList, HttpStatus.OK);
    }

    @GetMapping("/my/courses")
    @PreAuthorize("hasAuthority('course:read')")
    public ResponseEntity<List<Course>> courseListByStudent(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username="";
        List<Course> courseList=new ArrayList<>();
        if (authentication != null && authentication.isAuthenticated()) {

             username =(String)authentication.getPrincipal();
             courseList=courseService.getAllCourseByStudent(username);

        }
        return new ResponseEntity<>(courseList, HttpStatus.OK);
    }
}
