package org.dr.library.service;

import org.dr.library.dto.BookDto;
import org.dr.library.entity.Book;
import org.dr.library.entity.Borrower;
import org.dr.library.exceptions.BookExistException;
import org.dr.library.mappers.BookMapper;
import org.dr.library.repo.BookRepository;
import org.dr.library.repo.BorrowerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDto registerNewBook(BookDto newBook) throws BookExistException {

        Book oldbook = bookRepository.findTopByIsbn(newBook.isbn()).orElse(null);

        if(oldbook!=null && (!oldbook.getName().equalsIgnoreCase(newBook.name()) || !oldbook.getAuthor().equalsIgnoreCase(newBook.author()))) {
            throw new BookExistException("Author and Title for the given ISBN is not correct");
        }else{
            return BookMapper.INSTANCE.bookToBookDto(bookRepository.save(BookMapper.INSTANCE.bookDtoToBook(newBook)));
        }

    }

    public void borrowBook(long bookId, long borrowerId){
         bookRepository.updateBook(bookId, borrowerId);
    }

    public List<BookDto> findAllBooks() {

        return BookMapper.INSTANCE.bookListToBookDtoList(StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .collect(Collectors.toList()));

    }
}
