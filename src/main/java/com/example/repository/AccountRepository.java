package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A Spring Data JPA repository for the Account entity.
 * This interface provides methods for performing CRUD operations on the "account" table,
 * as well as custom queries.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    /**
     * Finds an account by its username.
     * @param username the username to search for.
     * @return the Account object with the matching username, or null if not found.
     */
    Account findByUsername(String username);
}
