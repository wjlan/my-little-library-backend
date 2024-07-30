package com.capstone.mylittlelibrarybackend.bookTest;

import static org.assertj.core.api.Assertions.assertThat;
import com.capstone.mylittlelibrarybackend.book.Book;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

public class BookTestUnit {

    private Book book;

    @Test
    public void testBookConstructorAndGetters() {
        // Arrange
        String title = "Test Title";
        String author = "Test Author";
        String genre = "Test Genre";
        String publishedYear = "2024";
        String description = "Test Description";
        String language = "English";
        byte[] image = {1, 2, 3, 4, 5};

        // Act
        Book book = new Book(title, author, genre, publishedYear, description, language, image);

        // Assert
        assertThat(book.getTitle()).isEqualTo(title);
        assertThat(book.getAuthor()).isEqualTo(author);
        assertThat(book.getGenre()).isEqualTo(genre);
        assertThat(book.getPublishedYear()).isEqualTo(publishedYear);
        assertThat(book.getDescription()).isEqualTo(description);
        assertThat(book.getLanguage()).isEqualTo(language);
        assertThat(book.getImage()).isEqualTo(image);
    }

    @Test
    public void testSettersAndGetters() {
        // Arrange
        Book book = new Book();
        String title = "Another Title";
        String author = "Another Author";
        String genre = "Another Genre";
        String publishedYear = "2023";
        String description = "Another Description";
        String language = "French";
        byte[] image = {10, 20, 30};

        // Act
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPublishedYear(publishedYear);
        book.setDescription(description);
        book.setLanguage(language);
        book.setImage(image);

        // Assert
        assertThat(book.getTitle()).isEqualTo(title);
        assertThat(book.getAuthor()).isEqualTo(author);
        assertThat(book.getGenre()).isEqualTo(genre);
        assertThat(book.getPublishedYear()).isEqualTo(publishedYear);
        assertThat(book.getDescription()).isEqualTo(description);
        assertThat(book.getLanguage()).isEqualTo(language);
        assertThat(book.getImage()).isEqualTo(image);
    }

    public void testToString() {
        // Arrange
        String title = "Test Title";
        String author = "Test Author";
        String genre = "Test Genre";
        String publishedYear = "2024";
        String description = "Test Description";
        String language = "English";
        byte[] image = {1, 2, 3, 4, 5};

        Book book = new Book(title, author, genre, publishedYear, description, language, image);

        // Act
        String toStringResult = book.toString();

        // Assert
        assertThat(toStringResult).contains("Book{");
        assertThat(toStringResult).contains("id=null"); // id is null before persisting
        assertThat(toStringResult).contains("title='" + title + "'");
        assertThat(toStringResult).contains("author='" + author + "'");
        assertThat(toStringResult).contains("genre='" + genre + "'");
        assertThat(toStringResult).contains("publishedYear='" + publishedYear + "'");
        assertThat(toStringResult).contains("description='" + description + "'");
        assertThat(toStringResult).contains("language='" + language + "'");
        assertThat(toStringResult).contains("image=" + Arrays.toString(image));
    }

}
