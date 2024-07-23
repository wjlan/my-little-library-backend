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

    public void addNewBook(Book book) {
        Optional<Book> bookOptional = bookRepository.findBookByTitle(book.getTitle());
        bookRepository.findBookByTitle(book.getTitle());
        if (bookOptional.isPresent()) {
            throw new IllegalStateException("Book already exists");
        }

        bookRepository.save(book);
    }
}

