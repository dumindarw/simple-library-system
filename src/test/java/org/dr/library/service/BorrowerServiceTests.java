package org.dr.library.service;

import org.dr.library.entity.Borrower;
import org.dr.library.repo.BorrowerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BorrowerServiceTests {

    @Mock
    BorrowerRepository borrowerRepository;

    @InjectMocks
    BorrowerService borrowerService;

    @Test
    void registerNewBorrower_success() {
        Borrower input = mock(Borrower.class);
        Borrower saved = mock(Borrower.class);
        when(borrowerRepository.save(input)).thenReturn(saved);

        Borrower result = borrowerService.registerNewBorrower(input);

        assertSame(saved, result);
        verify(borrowerRepository).save(input);
    }
}
