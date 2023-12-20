package ro.mycode.onlineschoolapi.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ro.mycode.onlineschoolapi.dto.*;
import ro.mycode.onlineschoolapi.jwt.JWTTokenProvider;
import ro.mycode.onlineschoolapi.model.Book;
import ro.mycode.onlineschoolapi.model.Course;
import ro.mycode.onlineschoolapi.model.Student;
import ro.mycode.onlineschoolapi.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

import static ro.mycode.onlineschoolapi.constante.Utils.JWT_TOKEN_HEADER;

@RestController
@CrossOrigin
@RequestMapping("api/v1/students")
@AllArgsConstructor
public class StudentRest {

    private StudentService studentService;
    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;





    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody StudentDTO user) {
        authenticate(user.email(),user.password());
        Student loginUser = studentService.findStudentByEmail(user.email());
        Student userPrincipal = new Student(loginUser.getFirstName(), loginUser.getSecondName(),loginUser.getEmail(), loginUser.getAge(),loginUser.getPassword(),loginUser.getUserRole());
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        Long userId= this.studentService.findStudentByEmail(user.email()).getId();
        LoginResponse loginResponse= new LoginResponse(userId,user.email(),jwtHeader.getFirst(JWT_TOKEN_HEADER), loginUser.getFirstName(), loginUser.getSecondName(),loginUser.getUserRole());
        return new ResponseEntity<>(loginResponse,jwtHeader,OK);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> addStudent(@RequestBody StudentDTO user){
        this.studentService.addStudent(user);
        Student studentPrincipal=studentService.findStudentByEmail(user.email());
        HttpHeaders jwtHeader=getJwtHeader(studentPrincipal);
        Long userId=this.studentService.findStudentByEmail(user.email()).getId();
        RegisterResponse registerResponse=new RegisterResponse(userId, user.firstName(), user.secondName(), user.email(),user.age(),jwtHeader.getFirst(JWT_TOKEN_HEADER));
        authenticate(user.email(), user.password());
        return new ResponseEntity<>(registerResponse,jwtHeader,OK);
    }

    @GetMapping("/")
    public ResponseEntity<StudentListResponse> studentList() {
        StudentListResponse studentListResponse = StudentListResponse.builder().studentList(studentService.getAllStudents()).message("All students").build();
        return new ResponseEntity<>(studentListResponse, HttpStatus.OK);
    }

    @PostMapping("/addBook")
    @PreAuthorize("hasAuthority('book:write')")
    public ResponseEntity<CreateBookRequest> addBookToAStudent(@RequestBody CreateBookRequest createBookRequest) {
        studentService.addBook(createBookRequest);
        return new ResponseEntity<>(createBookRequest, HttpStatus.CREATED);
    }


    @PostMapping("/enrollCourseToAStudent")
    @PreAuthorize("hasAuthority('course:write')")
    public ResponseEntity<CourseRequest> addCourseToAStudent(@RequestBody CourseRequest courseRequest) {
        studentService.addEnrolment(courseRequest);
        return new ResponseEntity<>(courseRequest, HttpStatus.CREATED);
    }

    @DeleteMapping("/cancelCourse")
    @PreAuthorize("hasAuthority('course:write')")
    public ResponseEntity<CourseRequest> cancelCourseToAStudent(@RequestBody CourseRequest courseRequest) {
        studentService.removeEnrolment(courseRequest);
        return new ResponseEntity<>(courseRequest, HttpStatus.OK);
    }

    @GetMapping("/bestCourse")
    public ResponseEntity<Course> bestCourse() {
        Course course = studentService.bestCourse();
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<StudentRequest> findStudentById(@PathVariable(value="id")Long id) {
        Optional<StudentRequest> x = studentService.getStudentById(id);
        return new ResponseEntity<>(x.get(),HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private HttpHeaders getJwtHeader(Student user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJWTToken(user));
        return headers;
    }

}







