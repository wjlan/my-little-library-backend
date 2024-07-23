package com.capstone.mylittlelibrarybackend.controller;

import com.capstone.mylittlelibrarybackend.dao.Book;
import com.capstone.mylittlelibrarybackend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable long id) {
        return bookService.getBookById(id);

    }
}
