package org.dr.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
/*@Table( uniqueConstraints = {
                @UniqueConstraint(name = "unique_book",
                        columnNames = {"book_isbn", "book_name","book_author"})
        })*/
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_isbn")
    private String isbn;

    @Column(name = "book_name")
    private String name;

    @Column(name = "book_author")
    private String author;

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private Borrower borrower;
}
