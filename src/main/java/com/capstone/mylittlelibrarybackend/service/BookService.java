package com.capstone.mylittlelibrarybackend.service;

import com.capstone.mylittlelibrarybackend.dao.Book;

public interface BookService {
    Book getBookById(long id);
}
