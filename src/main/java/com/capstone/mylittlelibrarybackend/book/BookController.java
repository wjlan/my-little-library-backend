package com.capstone.mylittlelibrarybackend.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping(path = "/search")
    public List<Book> searchBook(@RequestParam("title") String title) {
        return bookService.searchBook(title);
    }

    @PostMapping
    public void addNewBook(@RequestBody Book book) {
        bookService.addNewBook(book);
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
