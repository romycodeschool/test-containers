package ro.mycode.onlineschoolapi.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ro.mycode.onlineschoolapi.dto.BookDTO;
import ro.mycode.onlineschoolapi.dto.CreateBookRequest;
import ro.mycode.onlineschoolapi.model.Book;
import ro.mycode.onlineschoolapi.model.Course;
import ro.mycode.onlineschoolapi.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/books")
public class BookRest {

    private BookService bookService;

    public BookRest(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('book:read')")
    public ResponseEntity<List<Book>> bookList(){
        List<Book> bookList=bookService.getAllBooks();
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @GetMapping("/greaterPrice/{price}")
    @PreAuthorize("hasAuthority('book:read')")
    public ResponseEntity<List<Book>> bookListGraterPrice(@PathVariable double price){
        Optional<List<Book>> bookList=bookService.getAllBooksGraterPriceThan(price);
        return new ResponseEntity<>(bookList.get(),HttpStatus.OK);
    }

    @GetMapping("/lowerPrice/{price}")
    @PreAuthorize("hasAuthority('book:read')")
    public ResponseEntity<List<Book>> bookListLowerPrice(@PathVariable double price){
        Optional<List<Book>> bookList=bookService.getAllBooksLowestPriceThan(price);
        return new ResponseEntity<>(bookList.get(),HttpStatus.OK);
    }

    @GetMapping("/bestbooks")
    @PreAuthorize("hasAuthority('book:read')")
    public ResponseEntity<List<Book>> bookListBestBooks(){
        Optional<List<Book>> bookList=bookService.getAllBestBooks();
        return new ResponseEntity<>(bookList.get(),HttpStatus.OK);
    }

    @GetMapping("/bestbooks/{stars}")
    @PreAuthorize("hasAuthority('book:read')")
    public ResponseEntity<List<Book>> bookListAllBooksByStars(@PathVariable Long stars){
        Optional<List<Book>> bookList=bookService.getAllBooksByStars(stars);
        return new ResponseEntity<>(bookList.get(),HttpStatus.OK);
    }


    @GetMapping("/mybooks")
    @PreAuthorize("hasAuthority('book:read')")
    public ResponseEntity<List<Book>> bookListByStudent(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username="";
        List<Book> bookList=new ArrayList<>();
        if (authentication != null && authentication.isAuthenticated()) {
            username =(String)authentication.getPrincipal();
            bookList=bookService.getAllBooksByStudentEmail(username);
        }
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('book:write')")
    public ResponseEntity<BookDTO> updateCar(@RequestBody BookDTO book) {
        this.bookService.updateBook(book);
        return new ResponseEntity<>(book,HttpStatus.OK);
    }

    @DeleteMapping("/removebyid/{id}")
    @PreAuthorize("hasAuthority('book:write')")
    public ResponseEntity<Book> deleteBook(@PathVariable(value="id")Long id) {
       Optional<Book> x = bookService.findById(id);
        this.bookService.removeById(id);
        return new ResponseEntity<>(x.get(),HttpStatus.OK);
    }

}
