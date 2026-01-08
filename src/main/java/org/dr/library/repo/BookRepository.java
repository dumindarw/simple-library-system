package org.dr.library.repo;

import org.dr.library.entity.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {

    Optional<Book> findTopByIsbn(String isbn);

    @Modifying
    @Transactional // or typically in the service layer
    @Query("UPDATE Book b SET b.borrower.id = :borrowerId WHERE b.id = :bookId")
    void updateBook(Long borrowerId, Long bookId);
}
