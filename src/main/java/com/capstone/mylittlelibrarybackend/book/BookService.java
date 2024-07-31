package com.capstone.mylittlelibrarybackend.book;

import com.capstone.mylittlelibrarybackend.user.User;
import com.capstone.mylittlelibrarybackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<Book> getBooks(Long userId){
        return bookRepository.findBooksByUserId(userId);
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with ID " + bookId));
    }

    public void addNewBook(Long userId, Book book) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID " + userId));

        Optional<Book> bookOptional = bookRepository.findBookByUserIdAndTitle(userId, book.getTitle());
        if (bookOptional.isPresent()) {
            throw new IllegalStateException("Book with the same title already exists for this user");
        }

        book.setUser(user);
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

