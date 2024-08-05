package com.capstone.mylittlelibrarybackend.book;

import com.capstone.mylittlelibrarybackend.imageupload.UploadImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "author", defaultValue = "") String author,
            @RequestParam(value = "genre", defaultValue = "") String genre,
            @RequestParam(value = "language", defaultValue = "") String language
    ) {
        List<Book> books = bookService.searchBooks(title, author, genre, language);
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<String> addNewBook(@RequestParam("title") String title,
                                             @RequestParam("author") String author,
                                             @RequestParam("genre") String genre,
                                             @RequestParam("publishedYear") String publishedYear,
                                             @RequestParam("description") String description,
                                             @RequestParam("language") String language,
                                             @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            String imagePath = null;
            if (image != null && !image.isEmpty()) {
                imagePath = uploadImage.uploadImage(image);
            }

            Book book = new Book(title, author, genre, publishedYear, description, language, imagePath);
            bookService.addNewBook(book);

            return ResponseEntity.ok("Book added successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add book: " + e.getMessage());
        }
    }


    @PutMapping(path = "/{bookId}")
    public ResponseEntity<String> updateBook(@PathVariable("bookId") Long bookId,
                                             @RequestParam("title") String title,
                                             @RequestParam("author") String author,
                                             @RequestParam("genre") String genre,
                                             @RequestParam("publishedYear") String publishedYear,
                                             @RequestParam("description") String description,
                                             @RequestParam("language") String language,
                                             @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        try {
            Book existingBook = bookService.getBookById(bookId);
            if (image != null && !image.isEmpty()) {
                String imagePath = uploadImage.uploadImage(image);
                existingBook.setImage(imagePath);
            }

            existingBook.setTitle(title);
            existingBook.setAuthor(author);
            existingBook.setGenre(genre);
            existingBook.setPublishedYear(publishedYear);
            existingBook.setDescription(description);
            existingBook.setLanguage(language);

            bookService.updateBook(bookId, existingBook);

            return ResponseEntity.ok("Book successfully updated");
        } catch (IOException e) {
            // Handle exceptions related to image upload
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image: " + e.getMessage());
        } catch (Exception e) {
            // Handle other potential exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update book: " + e.getMessage());
        }
    }

    @DeleteMapping(path = "/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable("bookId") Long bookId) {
        try {
            bookService.deleteBook(bookId);
            return ResponseEntity.ok("Book successfully deleted");
        } catch (Exception e) {
            // Handle exceptions related to book deletion
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete book: " + e.getMessage());
        }
    }
}
