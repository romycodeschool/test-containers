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
import ro.mycode.onlineschoolapi.dto.BookDTO;
import ro.mycode.onlineschoolapi.exception.BookDosentExist;
import ro.mycode.onlineschoolapi.exception.EmptyDataBase;
import ro.mycode.onlineschoolapi.exception.StudentDosentExist;
import ro.mycode.onlineschoolapi.model.Book;
import ro.mycode.onlineschoolapi.model.Student;
import ro.mycode.onlineschoolapi.repository.BookRepository;
import ro.mycode.onlineschoolapi.repository.StudentRepo;
import ro.mycode.onlineschoolapi.services.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = OnlineSchoolApiApplication.class)
class BookServiceTest {


    @Mock
    private BookRepository bookRepository;

    @Mock
    private StudentRepo studentRepo;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void test() {
        bookRepository.deleteAll();
        studentRepo.deleteAll();
    }


    @Test
    void getAllBooks() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            books.add(new Book().builder().stars(5L).title("Harry Potter").price(20).author("Flore Denis").build());
        }

        doReturn(books).when(bookRepository).findAll();

        assertEquals(3, bookService.getAllBooks().size());
    }

    @Test
    void getAllBooksException() {
        doReturn(new ArrayList<>()).when(bookRepository).findAll();

        assertThrows(EmptyDataBase.class, () -> {
            bookService.getAllBooks();
        });
    }

    @Test
    void getAllBooksGraterPriceThan() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            books.add(new Book().builder().stars(5L).title("Harry Potter").price(22).author("Flore Denis").build());
        }

        doReturn(Optional.of(books)).when(bookRepository).lowestPriceBook(20);
        assertEquals(3, bookService.getAllBooksGraterPriceThan(20).get().size());
    }


    @Test
    void getAllBooksGraterPriceThanException() {
        doReturn(Optional.empty()).when(bookRepository).lowestPriceBook(15);
        assertThrows(EmptyDataBase.class, () -> {
            bookService.getAllBooksGraterPriceThan(15);
        });
    }


    @Test
    void getAllBooksLowestPriceThan() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            books.add(new Book().builder().stars(5L).title("Harry Potter").price(22).author("Flore Denis").build());
        }

        doReturn(Optional.of(books)).when(bookRepository).highPriceBook(20);
        assertEquals(5, bookService.getAllBooksLowestPriceThan(20).get().size());
    }

    @Test
    void getAllBooksByStudentEmail() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            books.add(new Book().builder().stars(5L).title("Harry Potter").price(22).author("Flore Denis").build());
        }

        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        s.setBooks(books);
        studentRepo.saveAndFlush(s);
        doReturn(Optional.of(s)).when(studentRepo).findStudentsByEmail("denis@yahoo.com");

        assertEquals(5, bookService.getAllBooksByStudentEmail("denis@yahoo.com").size());
    }

    @Test
    void getAllBooksLowestPriceThanException() {
        doReturn(Optional.empty()).when(bookRepository).highPriceBook(15);
        assertThrows(EmptyDataBase.class, () -> {
            bookService.getAllBooksLowestPriceThan(15);
        });
    }

    @Test
    void getAllBooksByStudentEmailException() {
        doReturn(Optional.empty()).when(studentRepo).findStudentsByEmail("denis@yahoo.com");
        assertThrows(StudentDosentExist.class, () -> {
            bookService.getAllBooksByStudentEmail("denis@yahoo.com");
        });
    }


    @Test
    void getAllBestBooks() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            books.add(new Book().builder().stars(5L).title("Harry Potter").price(22).author("Flore Denis").build());
        }

        doReturn(Optional.of(books)).when(bookRepository).bestBooks();
        assertEquals(5, bookService.getAllBestBooks().get().size());
    }

    @Test
    void getAllBestBooksException() {
        doReturn(Optional.empty()).when(bookRepository).bestBooks();
        assertThrows(EmptyDataBase.class, () -> {
            bookService.getAllBestBooks();
        });
    }

    @Test
    void getAllBooksByStars() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            books.add(new Book().builder().stars(5L).title("Harry Potter").price(22).author("Flore Denis").build());
        }

        doReturn(Optional.of(books)).when(bookRepository).filterStarBook(5L);
        assertEquals(5, bookService.getAllBooksByStars(5L).get().size());
    }

    @Test
    void getAllBooksByStarsException() {
        doReturn(Optional.empty()).when(bookRepository).filterStarBook(5L);
        assertThrows(EmptyDataBase.class, () -> {
            bookService.getAllBooksByStars(5L);
        });
    }

    @Test
    void findBookByIdTest(){
        Book book=new Book().builder().stars(5L).title("Harry Potter").price(22).author("Flore Denis").build();
        doReturn(Optional.of(book)).when(bookRepository).findById(5L);
        assertEquals("Flore Denis",bookService.findById(5L).get().getAuthor());
    }


    @Test
    void findBookByIdException(){
        doReturn(Optional.empty()).when(bookRepository).findById(5L);
        assertThrows(BookDosentExist.class, () -> {
            bookService.findById(5L);
        });
    }


    @Test
    void removeByIdTest(){
        Optional<Book> book=Optional.of(new Book().builder().id(5L).stars(5L).title("Harry Potter").price(22).author("Flore Denis").build() );
        doReturn(book).when(bookRepository).findById(book.get().getId());
        bookService.removeById(book.get().getId());
        assertEquals(Optional.empty(), bookRepository.getBookByStudentAndTitle(5L,"Harry Potter"));
    }

    @Test
    void removeByIdException(){
        doReturn(Optional.empty()).when(bookRepository).findById(5L);
        assertThrows(BookDosentExist.class,()->{
            bookService.removeById(5L);
        });
    }

    @Test
    void updateTest(){
        Optional<Book> book=Optional.of(new Book().builder().id(5L).stars(5L).title("Harry Potter").price(22).author("Flore Denis").build() );
        doReturn(book).when(bookRepository).findById(book.get().getId());
        BookDTO bookDTO=new BookDTO(5L,"Flore Denis",22,5L,"Harry Potter2",5L);
        bookService.updateBook(bookDTO);
        assertEquals("Harry Potter2",bookService.findById(5L).get().getTitle());
    }

    @Test
    void updateException(){
        Optional<Book> book=Optional.of(new Book().builder().id(5L).stars(5L).title("Harry Potter").price(22).author("Flore Denis").build() );
        doReturn(Optional.empty()).when(bookRepository).findById(book.get().getId());
        BookDTO bookDTO=new BookDTO(5L,"Flore Denis",22,5L,"Harry Potter2",5L);

        assertThrows(BookDosentExist.class,()->{
            bookService.updateBook(bookDTO);
        });
    }


}