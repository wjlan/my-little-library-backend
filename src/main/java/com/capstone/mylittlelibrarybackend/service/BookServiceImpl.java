package com.capstone.mylittlelibrarybackend.service;

import com.capstone.mylittlelibrarybackend.dao.BookRepository;
import com.capstone.mylittlelibrarybackend.dao.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book getBookById(long id) {
        return bookRepository.findById(id).orElseThrow(RuntimeException::new);
    }

}
