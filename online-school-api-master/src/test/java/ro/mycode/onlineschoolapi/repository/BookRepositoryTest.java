package ro.mycode.onlineschoolapi.repository;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.mycode.onlineschoolapi.OnlineSchoolApiApplication;
import ro.mycode.onlineschoolapi.model.Book;
import ro.mycode.onlineschoolapi.model.Student;
import ro.mycode.onlineschoolapi.security.UserRole;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OnlineSchoolApiApplication.class)
class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    StudentRepo studentRepo;


    @BeforeEach
    public void deleteDataBase() {
        studentRepo.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    @Transactional
    void orderBooksAscendentByPrice() {

        Faker faker = new Faker();
        List<Book> books = new ArrayList<>();
        Student x =new Student().builder().age(18).email("denis@yahoo.com").firstName("Flore").secondName("Denis").userRole(UserRole.STUDENT).password(new BCryptPasswordEncoder().encode("parola")).build();

        studentRepo.saveAndFlush(x);


        for (int i = 0; i < 3; i++) {
            books.add(new Book().builder().author(faker.book().author()).price(20 - i).stars(faker.number().numberBetween(1L, 5L)).title(faker.book().title()).build());
        }
        bookRepository.saveAllAndFlush(books);


        Optional<Student> find = studentRepo.findStudentsByEmail("denis@yahoo.com");
        List<Book> booksAll = bookRepository.findAll();
        find.get().addBook(booksAll.get(0));
        find.get().addBook(booksAll.get(1));
        find.get().addBook(booksAll.get(2));
        assertEquals(18, bookRepository.orderBooksAscendentByPrice().get().get(0).getPrice());

    }

    @Test
    void orderBooksDescendentByPrice() {
        Faker faker = new Faker();
        List<Book> books = new ArrayList<>();
        Student x =new Student().builder().age(18).email("denis@yahoo.com").firstName("Flore").secondName("Denis").userRole(UserRole.STUDENT).password(new BCryptPasswordEncoder().encode("parola")).build();

        studentRepo.saveAndFlush(x);


        for (int i = 0; i < 3; i++) {
            books.add(new Book().builder().author(faker.book().author()).price(20 - i).stars(faker.number().numberBetween(1L, 5L)).title(faker.book().title()).build());
        }
        bookRepository.saveAllAndFlush(books);


        Optional<Student> find = studentRepo.findStudentsByEmail("denis@yahoo.com");
        List<Book> booksAll = bookRepository.findAll();
        find.get().addBook(booksAll.get(0));
        find.get().addBook(booksAll.get(1));
        find.get().addBook(booksAll.get(2));
        assertEquals(20, bookRepository.orderBooksDescendentByPrice().get().get(0).getPrice());
    }

    @Test
    void lowestPriceBook() {
        Faker faker = new Faker();
        List<Book> books = new ArrayList<>();
        Student x =new Student().builder().age(18).email("denis@yahoo.com").firstName("Flore").secondName("Denis").userRole(UserRole.STUDENT).password(new BCryptPasswordEncoder().encode("parola")).build();

        studentRepo.saveAndFlush(x);


        for (int i = 0; i < 3; i++) {
            books.add(new Book().builder().author(faker.book().author()).price(20 - i).stars(faker.number().numberBetween(1L, 5L)).title(faker.book().title()).build());
        }
        bookRepository.saveAllAndFlush(books);


        Optional<Student> find = studentRepo.findStudentsByEmail("denis@yahoo.com");
        List<Book> booksAll = bookRepository.findAll();
        find.get().addBook(booksAll.get(0));
        find.get().addBook(booksAll.get(1));
        find.get().addBook(booksAll.get(2));
        assertEquals(2, bookRepository.lowestPriceBook(18).get().size());

    }

    @Test
    void highPriceBook() {
        Faker faker = new Faker();
        List<Book> books = new ArrayList<>();
        Student x =new Student().builder().age(18).email("denis@yahoo.com").firstName("Flore").secondName("Denis").userRole(UserRole.STUDENT).password(new BCryptPasswordEncoder().encode("parola")).build();

        studentRepo.saveAndFlush(x);


        for (int i = 0; i < 3; i++) {
            books.add(new Book().builder().author(faker.book().author()).price(20 - i).stars(faker.number().numberBetween(1L, 5L)).title(faker.book().title()).build());
        }
        bookRepository.saveAllAndFlush(books);


        Optional<Student> find = studentRepo.findStudentsByEmail("denis@yahoo.com");
        List<Book> booksAll = bookRepository.findAll();
        find.get().addBook(booksAll.get(0));
        find.get().addBook(booksAll.get(1));
        find.get().addBook(booksAll.get(2));
        assertEquals(3, bookRepository.highPriceBook(21).get().size());
    }

    @Test
    void bestBooks() {

        Faker faker = new Faker();
        List<Book> books = new ArrayList<>();
        Student x =new Student().builder().age(18).email("denis@yahoo.com").firstName("Flore").secondName("Denis").userRole(UserRole.STUDENT).password(new BCryptPasswordEncoder().encode("parola")).build();

        studentRepo.saveAndFlush(x);


        for (int i = 0; i < 3; i++) {
            books.add(new Book().builder().author(faker.book().author()).price(20 - i).stars(5L).title(faker.book().title()).build());
        }
        bookRepository.saveAllAndFlush(books);


        Optional<Student> find = studentRepo.findStudentsByEmail("denis@yahoo.com");
        List<Book> booksAll = bookRepository.findAll();
        find.get().addBook(booksAll.get(0));
        find.get().addBook(booksAll.get(1));
        find.get().addBook(booksAll.get(2));
        assertEquals(5L, bookRepository.bestBooks().get().get(0).getStars());

    }

    @Test
    void filterStarBook() {
        Faker faker = new Faker();
        List<Book> books = new ArrayList<>();
        Student x =new Student().builder().age(18).email("denis@yahoo.com").firstName("Flore").secondName("Denis").userRole(UserRole.STUDENT).password(new BCryptPasswordEncoder().encode("parola")).build();

        studentRepo.saveAndFlush(x);


        for (int i = 0; i < 3; i++) {
            books.add(new Book().builder().author(faker.book().author()).price(20 - i).stars(5L).title(faker.book().title()).build());
        }
        bookRepository.saveAllAndFlush(books);


        Optional<Student> find = studentRepo.findStudentsByEmail("denis@yahoo.com");
        List<Book> booksAll = bookRepository.findAll();
        find.get().addBook(booksAll.get(0));
        find.get().addBook(booksAll.get(1));
        find.get().addBook(booksAll.get(2));
        assertEquals(3, bookRepository.filterStarBook(5L).get().size());
    }

//    @Test
//    void getBookByStudentAndTitle() {
//
//        Faker faker = new Faker();
//        List<Book> books = new ArrayList<>();
//        Student x =new Student().builder().age(18).email("denis@yahoo.com").firstName("Flore").secondName("Denis").userRole(UserRole.STUDENT).password(new BCryptPasswordEncoder().encode("parola")).build();
//
//        studentRepo.saveAndFlush(x);
//
//
//        for (int i = 0; i < 3; i++) {
//            books.add(new Book().builder().author(faker.book().author()).price(20 - i).stars(5L).title(faker.book().title()).build());
//        }
//        bookRepository.saveAllAndFlush(books);
//
//
//        Optional<Student> find = studentRepo.findStudentsByEmail("denis@yahoo.com");
//        List<Book> booksAll = bookRepository.findAll();
//        find.get().addBook(booksAll.get(0));
//        find.get().addBook(booksAll.get(1));
//        find.get().addBook(booksAll.get(2));
//        studentRepo.saveAndFlush(find.get());
//        assertEquals(booksAll.get(0), bookRepository.getBookByStudentAndTitle(find.get().getId(), booksAll.get(0).getTitle()).get());
//    }

    @Test
     void removeBookByStudentAndTitle() {

        Faker faker = new Faker();
        List<Book> books = new ArrayList<>();
        Student x =new Student().builder().age(18).email("denis@yahoo.com").firstName("Flore").secondName("Denis").userRole(UserRole.STUDENT).password(new BCryptPasswordEncoder().encode("parola")).build();

        studentRepo.saveAndFlush(x);


        for (int i = 0; i < 3; i++) {
            books.add(new Book().builder().author(faker.book().author()).price(20 - i).stars(5L).title(faker.book().title()).build());
        }
        bookRepository.saveAllAndFlush(books);


        Optional<Student> find = studentRepo.findStudentsByEmail("denis@yahoo.com");
        List<Book> booksAll = bookRepository.findAll();
        find.get().addBook(booksAll.get(0));
        find.get().addBook(booksAll.get(1));
        find.get().addBook(booksAll.get(2));
        studentRepo.saveAndFlush(find.get());
        bookRepository.removeBookByStudentAndTitle(find.get().getId(), booksAll.get(0).getTitle());
        assertEquals(Optional.empty(), bookRepository.getBookByStudentAndTitle(find.get().getId(), booksAll.get(0).getTitle()));

    }
}