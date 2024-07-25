package com.capstone.mylittlelibrarybackend.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.title = ?1")
    Optional<Book> findBookByTitle(String tittle);

    @Query("SELECT b FROM Book b WHERE b.title LIKE %?1%")
    List<Book> findBooksByTitleContaining(String title);
}
