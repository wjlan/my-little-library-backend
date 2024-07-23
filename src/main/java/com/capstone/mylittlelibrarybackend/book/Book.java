package com.capstone.mylittlelibrarybackend.book;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence"
    )
    private long id;

    private String title;
    private String author;
    private String genre;
    private Integer publishedYear;
    private String description;
    private String language;

    @Lob
    private byte[] image;

    // Constructors, getters, and setters

    public Book() {}

    public Book(long id, String title, String author, String genre, Integer publishedYear, String description, String language, byte[] image) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publishedYear = publishedYear;
        this.description = description;
        this.language = language;
        this.image = image;
    }

    public Book(String title, String author, String genre, Integer publishedYear, String description, String language, byte[] image) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publishedYear = publishedYear;
        this.description = description;
        this.language = language;
        this.image = image;
    }

    // Getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", publishedYear=" + publishedYear +
                ", description='" + description + '\'' +
                ", language='" + language + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
