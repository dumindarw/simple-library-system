package org.dr.library.controller;

import lombok.extern.slf4j.Slf4j;
import org.dr.library.dto.BookDto;
import org.dr.library.dto.ResponseDto;
import org.dr.library.exceptions.BookException;
import org.dr.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class BooksController {


    @Autowired
    BookService bookService;

    @PostMapping("/books")
    ResponseEntity<?> newBook(@RequestBody BookDto book) {

        try{
            return new ResponseEntity<BookDto>(bookService.registerNewBook(book), HttpStatus.OK);
        }catch (BookException existException){
            return new ResponseEntity<ResponseDto>(new ResponseDto(existException.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/books")
    ResponseEntity<List<BookDto>> books() {
        return new ResponseEntity<List<BookDto>>(bookService.findAllBooks(), HttpStatus.OK);
    }

    @PatchMapping("/books/{bookId}/borrowers/{borrowerId}")
    ResponseEntity<?> borrowBook(@PathVariable long bookId, @PathVariable long borrowerId) {

        try {
            int status= bookService.borrowBook(bookId, borrowerId);
            if(status == 1) {
                return new ResponseEntity<>(new ResponseDto("Book successfully borrowed"), HttpStatus.OK);
            }else{
                return new ResponseEntity<ResponseDto>(new ResponseDto("Borrowing was not successful"), HttpStatus.BAD_REQUEST);
            }
        } catch (BookException e) {
            return new ResponseEntity<ResponseDto>(new ResponseDto(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping("/books/{bookId}/borrowers/{borrowerId}/return")
    ResponseEntity<?> returnBook(@PathVariable long bookId, @PathVariable long borrowerId) {

        try {
            int status= bookService.returnBook(bookId, borrowerId);
            if(status == 1) {
                return new ResponseEntity<>(new ResponseDto("Book successfully returned"), HttpStatus.OK);
            }else{
                return new ResponseEntity<ResponseDto>(new ResponseDto("Return was not successful"), HttpStatus.BAD_REQUEST);
            }
        } catch (BookException e) {
            return new ResponseEntity<ResponseDto>(new ResponseDto(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }
}
