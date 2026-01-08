package org.dr.library.controller;

import org.dr.library.entity.Borrower;
import org.dr.library.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BorrowerController {

    @Autowired
    BorrowerService borrowerService;

    @PostMapping("/borrowers")
    Borrower newBorrower(@RequestBody Borrower newBorrower) {
        return borrowerService.registerNewBorrower(newBorrower);
    }

}
