package org.dr.library.service;

import lombok.extern.slf4j.Slf4j;
import org.dr.library.dto.BookDto;
import org.dr.library.entity.Book;
import org.dr.library.exceptions.BookException;
import org.dr.library.mappers.BookMapper;
import org.dr.library.repo.BookRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDto registerNewBook(BookDto newBook) throws BookException {

        Book oldbook = bookRepository.findTopByIsbn(newBook.isbn()).orElse(null);

        if(oldbook!=null && (!oldbook.getName().equalsIgnoreCase(newBook.name()) || !oldbook.getAuthor().equalsIgnoreCase(newBook.author()))) {
            throw new BookException("Author and Title for the given ISBN is not correct");
        }else{
            return BookMapper.INSTANCE.bookToBookDto(bookRepository.save(BookMapper.INSTANCE.bookDtoToBook(newBook)));
        }

    }

    public int borrowBook(long bookId, long borrowerId) throws BookException {
        try {
            Optional<Book> book = bookRepository.findById(bookId);
            if(book.isPresent() && book.get().getBorrower() == null) {
                return bookRepository.borrowBook(bookId, borrowerId);
            }else{
                throw new BookException("Book already borrowed");
            }

        }catch (DataAccessException e){
            log.error("Error while borrowing book {}", e.getMessage());
            throw new BookException("Error while borrowing book");
        }
    }

    public int returnBook(long bookId, long borrowerId) throws BookException {
        try {
            return bookRepository.returnBook(bookId, borrowerId);
        }catch (DataAccessException e){
            log.error("Error while returning book {}", e.getMessage());
            throw new BookException("Error while returning book");
        }
    }

    public List<BookDto> findAllBooks() {

        return BookMapper.INSTANCE.bookListToBookDtoList(StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .collect(Collectors.toList()));

    }
}
