package com.capstone.mylittlelibrarybackend.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with ID " + bookId));
    }

    public void addNewBook(Book book) {
        // Check for duplicates
        List<Book> existingBooks = bookRepository.searchBooks(
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getLanguage()
        );

        if (!existingBooks.isEmpty()) {
            throw new IllegalStateException("A book with the same title, author, genre, and language already exists.");
        }

        // Save the new book if no duplicates are found
        try {
            bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Error occurred while adding the book: " + e.getMessage(), e);
        }
    }

    public void updateBook(Long bookId, Book book) {
        if (bookRepository.existsById(bookId)) {
            book.setId(bookId);
            bookRepository.save(book);
        } else {
            throw new RuntimeException("Book not found with ID " + bookId);
        }
    }

    public void deleteBook(Long bookId) {
        boolean exists = bookRepository.existsById(bookId);
        if (!exists) {
            throw new IllegalStateException("Book not found with ID " + bookId);
        }
        bookRepository.deleteById(bookId);
    }

    public List<Book> searchBooks(String title, String author, String genre, String language) {
        return bookRepository.searchBooks(
                title.isEmpty() ? null : title,
                author.isEmpty() ? null : author,
                genre.isEmpty() ? null : genre,
                language.isEmpty() ? null : language
        );
    }
}
