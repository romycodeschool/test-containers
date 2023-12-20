package ro.mycode.onlineschoolapi.services;

import ro.mycode.onlineschoolapi.dto.*;
import ro.mycode.onlineschoolapi.exception.*;
import ro.mycode.onlineschoolapi.model.Book;
import ro.mycode.onlineschoolapi.model.Course;
import ro.mycode.onlineschoolapi.model.Student;
import ro.mycode.onlineschoolapi.repository.BookRepository;
import ro.mycode.onlineschoolapi.repository.CourseRepo;
import ro.mycode.onlineschoolapi.repository.StudentRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import ro.mycode.onlineschoolapi.security.UserRole;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class StudentService {

    private StudentRepo studentRepo;
    private CourseRepo courseRepo;

    private BookRepository bookRepository;


    public StudentService(StudentRepo studentRepo, CourseRepo courseRepo, BookRepository bookRepository) {
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.bookRepository = bookRepository;
    }

    public List<Student> getAllStudents() {
        List<Student> students = studentRepo.findAll();
        if (students.size() == 0) {
            throw new EmptyDataBase("DataBase Empty!");
        }
        return students;
    }

    public Optional<StudentRequest> getStudentById(Long id) {
        Optional<Student> student = studentRepo.findById(id);
        if (!student.isEmpty()) {
            StudentRequest studentRequest = new StudentRequest(student.get().getId(), student.get().getFirstName(), student.get().getSecondName(),
                    student.get().getEmail(), student.get().getAge(), student.get().getPassword(), student.get().getUserRole());
            return Optional.of(studentRequest);
        } else {
            throw new StudentDosentExist("Studentul nu exista !");
        }
    }

    @Transactional
    @Modifying
    public void addStudent(StudentDTO s) {
        Optional<Student> student = studentRepo.findStudentsByEmail(s.email());
        if (student.isEmpty()) {
            Student stud = new Student(s.firstName(), s.secondName(), s.email(), s.age(), s.password());
            if(s.role().compareTo("ADMIN")==0){
                stud.setUserRole(UserRole.ADMIN);
            }else if (s.role().compareTo("STUDENT")==0){
                stud.setUserRole(UserRole.STUDENT);
            }
            studentRepo.saveAndFlush(stud);
        } else {
            throw new StudentAlreadyExist("Studentul exista deja!");
        }
    }

    @Transactional
    @Modifying
    public void removeStudent(String email) {
        Optional<Student> student = studentRepo.findStudentsByEmail(email);
        if (!student.isEmpty()) {
            studentRepo.removeByEmail(email);
        } else {
            throw new StudentDosentExist("Studentul nu exista!");
        }
    }

    @Transactional
    @Modifying
    public void addBook(CreateBookRequest createBookRequestDTO) throws BookDosentExist, StudentDosentExist {

        Optional<Student> student = studentRepo.findById(createBookRequestDTO.getIdStudent());


        if (student.isEmpty()) {
            throw new StudentDosentExist("Student doesn't exist! ");
        }


        Book book = Book.builder().
                title(createBookRequestDTO.getTitle()).
                author(createBookRequestDTO.getAuthor())
                .price(createBookRequestDTO.getPrice()).stars(createBookRequestDTO.getStars()).build();

        if (!bookRepository.getBookByStudentAndTitle(student.get().getId(), book.getTitle()).isEmpty()) {
            throw new BookDosentExist("Book doesn't exist!");
        }

        student.get().addBook(book);
        studentRepo.saveAndFlush(student.get());
    }

    @Transactional
    @Modifying
    public void addEnrolment(CourseRequest courseRequest) throws StatusCourseException, StudentDosentExist {
        Optional<Course> course = courseRepo.getCourseByDepartamentAndName(courseRequest.department(), courseRequest.name());
        if (course.isEmpty()) {
            Course courseItem=new Course(courseRequest.name(), courseRequest.department());
            courseRepo.saveAndFlush(courseItem);
        }

        Optional<Student> student = studentRepo.findById(courseRequest.studentId());
        if (student.isEmpty()) {
            throw new StudentDosentExist("Studentul nu exista ! ");
        }

        Optional<Course> courseFind = courseRepo.getCourseByDepartamentAndName(courseRequest.department(), courseRequest.name());

        List<Course> courses = student.get().getEnrolledCourses();
        for (Course t : courses)
            if (t.equals(courseFind.get())) {
                throw new StatusCourseException("Enrolmentul exista deja ! ");
            }


        student.get().addCourse(courseFind.get());
        studentRepo.saveAndFlush(student.get());
    }

    @Transactional
    @Modifying
    public void removeEnrolment(CourseRequest courseRequest) throws StatusCourseException, StudentDosentExist {
        Optional<Student> student = studentRepo.findById(courseRequest.studentId());
        if (student.isEmpty()) {
            throw new StudentDosentExist("Studentul nu exista ! ");
        }

        Optional<Course> course = courseRepo.getCourseByDepartamentAndName(courseRequest.department(), courseRequest.name());
        if (course.isEmpty()) {
            throw new StatusCourseException("Cursul nu exista !");
        }


        if (!student.get().vfExistCourse(course.get())) {
            throw new StatusCourseException("Enrolmentul nu exista ! ");
        } else {
            student.get().removeCourse(course.get());
            studentRepo.saveAndFlush(student.get());
        }
    }


    @Transactional
    @Modifying
    public Course bestCourse() throws StatusCourseException {
        Optional<Course> curs = courseRepo.findById(studentRepo.bestCourseId().get());
        return curs.get();
    }

    public Student findStudentByEmail(String email) {
        Optional<Student> stundet=studentRepo.findStudentsByEmail(email);
        if(!stundet.isEmpty()){
            return stundet.get();
        }else {
            throw new StudentDosentExist("Studentul nu exista !");
        }
    }

}