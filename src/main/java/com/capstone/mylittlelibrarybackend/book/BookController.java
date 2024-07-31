package com.capstone.mylittlelibrarybackend.book;

import com.capstone.mylittlelibrarybackend.user.User;
import com.capstone.mylittlelibrarybackend.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/books")
public class BookController {

    private final BookService bookService;
    private final UserRepository userRepository;

    @Autowired
    public BookController(BookService bookService, UserRepository userRepository) {
        this.bookService = bookService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Book> getBooks(@CurrentUser UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        return bookService.getBooks(user.getId());
    }

    @GetMapping(path = "/{bookId}")
    public Book getBookById(@PathVariable("bookId") Long bookId) {
        return bookService.getBookById(bookId);
    }

    @GetMapping(path = "/search")
    public List<Book> searchBook(@CurrentUser UserPrincipal userPrincipal, @RequestParam("title") String title) {
        return bookService.searchBook(userPrincipal.getId(), title);
    }
    @PostMapping
    public void addNewBook(@CurrentUser UserPrincipal userPrincipal, @RequestBody Book book) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        bookService.addNewBook(user.getId(), book);
    }

    @PutMapping(path = "/{bookId}")
    public void updateBook(@PathVariable("bookId") Long bookId, @RequestBody Book book) {
        bookService.updateBook(bookId, book);
    }

    @DeleteMapping(path = "/{bookId}")
    public void deleteBook(@PathVariable("bookId") Long bookId) {
        bookService.deleteBook(bookId);
    }
}
