package org.dr.library.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.dr.library.entity.Book;
import org.dr.library.entity.Borrower;
import org.dr.library.repo.BookRepository;
import org.dr.library.repo.BorrowerRepository;
import org.dr.library.service.BorrowerService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BorrowerRepository borrowerRepository;

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }



    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;

    }



    @Test
    void shouldAbleToGetAllBooks() {

        bookRepository.save(new Book(null,"isbn-111-222", "Quesadilla", "Duminda R",null));
        bookRepository.save(new Book(null,"isbn-111-222-333", "Monica", "Duminda R",null));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/books")
                .then()
                .statusCode(200)
                .body(".", hasSize(2));
    }

    @Test
    void shouldAbleToBorrowOneBook() {

        borrowerRepository.save(new Borrower(null, "John Doe", "deem@joe.lk"));

        given()
                .contentType(ContentType.JSON)
                .when()
                .patch("/books/1/borrowers/1")
                .then()
                .statusCode(200)
                .body("message", equalTo("Book successfully borrowed"));
    }

    @Test
    void shouldAbleToReturnBook() {

        given()
                .contentType(ContentType.JSON)
                .when()
                .patch("/books/1/borrowers/1/return")
                .then()
                .statusCode(200)
                .body("message", equalTo("Book successfully returned"));
    }

}
