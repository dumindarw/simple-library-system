package org.dr.library.controller;

import org.dr.library.dto.BookDto;
import org.dr.library.dto.ErrorDto;
import org.dr.library.entity.Borrower;
import org.dr.library.exceptions.BookExistException;
import org.dr.library.mappers.BookMapper;
import org.dr.library.service.BookService;
import org.dr.library.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BooksController {


    @Autowired
    BookService bookService;

    @PostMapping("/books")
    ResponseEntity<?> newBook(@RequestBody BookDto book) {

        try{
            return new ResponseEntity<BookDto>(bookService.registerNewBook(book), HttpStatus.OK);
        }catch (BookExistException existException){
            return new ResponseEntity<ErrorDto>(new ErrorDto(existException.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/books")
    ResponseEntity<List<BookDto>> books() {

        return new ResponseEntity<List<BookDto>>(bookService.findAllBooks(), HttpStatus.OK);
    }

    @PutMapping("/books/{bookId}/borrowers/{borrowerId}")
    ResponseEntity<?> borrowBook(@PathVariable long bookId, @PathVariable long borrowerId) {

        bookService.borrowBook(bookId, borrowerId);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
