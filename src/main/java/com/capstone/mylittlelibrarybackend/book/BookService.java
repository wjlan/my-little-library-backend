package com.capstone.mylittlelibrarybackend.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with ID " + bookId));
    }

    public void addNewBook(Book book) {
        List<Book> existingBooks = bookRepository.findBooksByTitleContaining(book.getTitle());

        for (Book existingBook : existingBooks) {
            if (existingBook.getAuthor().equals(book.getAuthor()) &&
                    existingBook.getGenre().equals(book.getGenre()) &&
                    existingBook.getPublishedYear().equals(book.getPublishedYear()) &&
                    existingBook.getDescription().equals(book.getDescription()) &&
                    existingBook.getLanguage().equals(book.getLanguage())) {

                throw new IllegalStateException("Duplicate book already exists");
            }
        }

        bookRepository.save(book);
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

    public List<Book> searchBook(String title) {
        return bookRepository.findBooksByTitleContaining(title);
    }
}

