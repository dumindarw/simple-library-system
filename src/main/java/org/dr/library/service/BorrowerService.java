package org.dr.library.service;

import org.dr.library.entity.Borrower;
import org.dr.library.repo.BorrowerRepository;
import org.springframework.stereotype.Service;

@Service
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;

    public BorrowerService(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    public Borrower registerNewBorrower(Borrower newBorrower) {
        return borrowerRepository.save(newBorrower);
    }
}
