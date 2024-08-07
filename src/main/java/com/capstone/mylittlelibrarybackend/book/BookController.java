package com.capstone.mylittlelibrarybackend.book;

import com.capstone.mylittlelibrarybackend.exception.ResourceNotFoundException;
import com.capstone.mylittlelibrarybackend.imageupload.UploadImage;
import com.capstone.mylittlelibrarybackend.user.User;
import com.capstone.mylittlelibrarybackend.security.UserPrincipal;
import com.capstone.mylittlelibrarybackend.user.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public BookController(BookService bookService, UploadImage uploadImage, UserRepository userRepository) {
        this.bookService = bookService;
        this.uploadImage = uploadImage;
        this.userRepository = userRepository;
    }

    // Get books list
    @GetMapping
    public List<Book> getBooks(UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        return bookService.getBooks(user.getId());
    }

    @GetMapping(path = "/{bookId}")
    public Book getBookById(@PathVariable("bookId") Long bookId) {
        return bookService.getBookById(bookId);
    }

    // Search and filter
    @GetMapping(path = "/search")
    public ResponseEntity<List<Book>> searchBooks(
            UserPrincipal userPrincipal,
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "author", defaultValue = "") String author,
            @RequestParam(value = "genre", defaultValue = "") String genre,
            @RequestParam(value = "language", defaultValue = "") String language
    ) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        List<Book> books = bookService.searchBooks(title, author, genre, language, user.getId());
        return ResponseEntity.ok(books);
    }

    // Create new book
    @PostMapping
    public ResponseEntity<String> addNewBook(UserPrincipal userPrincipal,
                                             @RequestParam("title") String title,
                                             @RequestParam("author") String author,
                                             @RequestParam("genre") String genre,
                                             @RequestParam("publishedYear") String publishedYear,
                                             @RequestParam("description") String description,
                                             @RequestParam("language") String language,
                                             @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            User user = userRepository.findById(userPrincipal.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

            String imagePath = null;
            if (image != null && !image.isEmpty()) {
                imagePath = uploadImage.uploadImage(image);
            }

            Book book = new Book(title, author, genre, publishedYear, description, language, imagePath, user);
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

    // Update
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
