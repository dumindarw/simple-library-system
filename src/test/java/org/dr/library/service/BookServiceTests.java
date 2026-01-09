package org.dr.library.service;

import org.dr.library.dto.BookDto;
import org.dr.library.entity.Book;
import org.dr.library.entity.Borrower;
import org.dr.library.exceptions.BookException;
import org.dr.library.repo.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService;


    @Test
    void registerNewBook_success_whenNoExistingBook() throws Exception {
        BookDto input = mock(BookDto.class);
        when(input.isbn()).thenReturn("isbn-1");
        when(input.name()).thenReturn("Title");
        when(input.author()).thenReturn("Author");

        Book saved = mock(Book.class);
        when(saved.getIsbn()).thenReturn("isbn-1");
        when(saved.getName()).thenReturn("Title");
        when(saved.getAuthor()).thenReturn("Author");

        when(bookRepository.findTopByIsbn("isbn-1")).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(saved);

        BookDto result = bookService.registerNewBook(input);

        assertNotNull(result);
        assertEquals("isbn-1", result.isbn());
        assertEquals("Title", result.name());
        assertEquals("Author", result.author());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void borrowBook_throws_whenAlreadyBorrowed() {
        long bookId = 3L;
        long borrowerId = 4L;

        Book book = mock(Book.class);
        when(book.getBorrower()).thenReturn(new Borrower(4L,"borrower1","br@lib.lk"));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        BookException ex = assertThrows(BookException.class, () -> bookService.borrowBook(bookId, borrowerId));
        assertEquals("Book already borrowed", ex.getMessage());
        verify(bookRepository, never()).borrowBook(anyLong(), anyLong());
    }

    @Test
    void returnBook_wrapsDataAccessException() {
        long bookId = 9L;
        long borrowerId = 10L;

        when(bookRepository.returnBook(bookId, borrowerId)).thenThrow(new DataAccessResourceFailureException("db"));

        BookException ex = assertThrows(BookException.class, () -> bookService.returnBook(bookId, borrowerId));
        assertEquals("Error while returning book", ex.getMessage());
    }

    @Test
    void returnBook_returnsCount_onSuccess() throws Exception {
        long bookId = 7L;
        long borrowerId = 8L;

        when(bookRepository.returnBook(bookId, borrowerId)).thenReturn(1);
        int result = bookService.returnBook(bookId, borrowerId);
        assertEquals(1, result);
        verify(bookRepository).returnBook(bookId, borrowerId);
    }
}
