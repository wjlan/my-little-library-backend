package com.capstone.mylittlelibrarybackend.bookTest;

import com.capstone.mylittlelibrarybackend.book.Book;
import com.capstone.mylittlelibrarybackend.book.BookRepository;
import com.capstone.mylittlelibrarybackend.book.BookService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;


public class BookTestInt {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetBooks() {
        // Arrange
        List<Book> books = new ArrayList<>();
        books.add(new Book("Title1", "Author1", "Genre1", "2020", "Description1", "English", null));
        books.add(new Book("Title2", "Author2", "Genre2", "2021", "Description2", "English", null));

        when(bookRepository.findAll()).thenReturn(books);

        // Act
        List<Book> result = bookService.getBooks();

        // Assert
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void testSearchBooks_Title() {
        // Arrange
        Book book = new Book("Title1", "Author1", "Genre1", "2020", "Description1", "Language1", null);
        when(bookRepository.searchBooks("Title1", null, null, null)).thenReturn(List.of(book));

        // Act
        List<Book> result = bookService.searchBooks("Title1", "", "", "");

        // Assert
        assertEquals(1, result.size());
        assertEquals("Title1", result.get(0).getTitle());
        verify(bookRepository, times(1)).searchBooks("Title1", null, null, null);
    }

    @Test
    public void testAddNewBook_DuplicateBook() {
        // Arrange
        Book existingBook = new Book("Title1", "Author1", "Genre1", "2020", "Description1", "Language1", null);
        when(bookRepository.searchBooks("Title1", "Author1", "Genre1", "Language1")).thenReturn(List.of(existingBook));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> bookService.addNewBook(existingBook));
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    public void testUpdateBook_Success() {
        // Arrange
        Book book = new Book("Title1", "Author1", "Genre1", "2020", "Description1", "English", null);
        when(bookRepository.existsById(anyLong())).thenReturn(true);

        // Act
        bookService.updateBook(1L, book);

        // Assert
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testUpdateBook_NotFound() {
        // Arrange
        Book book = new Book("Title1", "Author1", "Genre1", "2020", "Description1", "English", null);
        when(bookRepository.existsById(anyLong())).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> bookService.updateBook(1L, book));
        verify(bookRepository, never()).save(any(Book.class));
    }


    @Test
    public void testDeleteBook_Success() {
        // Arrange
        when(bookRepository.existsById(anyLong())).thenReturn(true);
        // Act
        bookService.deleteBook(1L);
        // Assert
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteBook_NotFound() {
        // Arrange
        when(bookRepository.existsById(anyLong())).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> bookService.deleteBook(1L));
        verify(bookRepository, never()).deleteById(anyLong());
    }
}
