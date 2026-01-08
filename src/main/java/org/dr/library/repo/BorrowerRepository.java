package org.dr.library.repo;

import org.dr.library.entity.Borrower;
import org.springframework.data.repository.CrudRepository;

public interface BorrowerRepository extends CrudRepository<Borrower, Long> {
}
