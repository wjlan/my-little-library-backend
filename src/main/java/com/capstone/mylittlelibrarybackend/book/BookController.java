package com.capstone.mylittlelibrarybackend.book;

import com.capstone.mylittlelibrarybackend.imageupload.UploadImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/books")
public class BookController {

    private final BookService bookService;
    private final UploadImage uploadImage;

    @Autowired
    public BookController(BookService bookService, UploadImage uploadImage) {
        this.bookService = bookService;
        this.uploadImage = uploadImage;
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping(path = "/{bookId}")
    public Book getBookById(@PathVariable("bookId") Long bookId) {
        return bookService.getBookById(bookId);
    }

    @GetMapping(path = "/search")
    public List<Book> searchBook(@RequestParam("title") String title) {
        return bookService.searchBook(title);
    }

    @PostMapping
    public void addNewBook(@RequestParam("title") String title,
                           @RequestParam("author") String author,
                           @RequestParam("genre") String genre,
                           @RequestParam("publishedYear") String publishedYear,
                           @RequestParam("description") String description,
                           @RequestParam("language") String language,
                           @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        String imagePath = (image != null && !image.isEmpty()) ? String.valueOf(uploadImage.uploadImage(image)) : null;

        Book book = new Book(title, author, genre, publishedYear, description, language, imagePath);
        bookService.addNewBook(book);
    }

    @PutMapping(path = "/{bookId}")
    public void updateBook(@PathVariable("bookId") Long bookId,
                           @RequestParam("title") String title,
                           @RequestParam("author") String author,
                           @RequestParam("genre") String genre,
                           @RequestParam("publishedYear") String publishedYear,
                           @RequestParam("description") String description,
                           @RequestParam("language") String language,
                           @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        Book existingBook = bookService.getBookById(bookId);
        if (image != null && !image.isEmpty()) {
            String imagePath = String.valueOf(uploadImage.uploadImage(image));
            existingBook.setImage(imagePath);
        }

        existingBook.setTitle(title);
        existingBook.setAuthor(author);
        existingBook.setGenre(genre);
        existingBook.setPublishedYear(publishedYear);
        existingBook.setDescription(description);
        existingBook.setLanguage(language);

        bookService.updateBook(bookId, existingBook);
    }

    @DeleteMapping(path = "/{bookId}")
    public void deleteBook(@PathVariable("bookId") Long bookId) {
        bookService.deleteBook(bookId);
    }

}
