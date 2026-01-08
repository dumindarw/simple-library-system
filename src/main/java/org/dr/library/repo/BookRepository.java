package org.dr.library.repo;

import org.dr.library.entity.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {

    Optional<Book> findTopByIsbn(String isbn);

    @Transactional(propagation = Propagation.REQUIRED)
    @Modifying
    @Query("UPDATE Book b SET b.borrower.id = :borrowerId WHERE b.id = :bookId")
    int borrowBook(@Param("bookId") Long bookId, @Param("borrowerId") Long borrowerId);

    @Transactional(propagation = Propagation.REQUIRED)
    @Modifying
    @Query("UPDATE Book b SET b.borrower.id = null WHERE b.id = :bookId AND b.borrower.id= :borrowerId")
    int returnBook(@Param("bookId") Long bookId, @Param("borrowerId") Long borrowerId);
}
