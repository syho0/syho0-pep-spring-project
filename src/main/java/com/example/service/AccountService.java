package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for handling business logic related to accounts.
 * This class interacts with the AccountRepository to perform operations such as user registration and login.
 */
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    /**
     * Constructs a new AccountService with the given AccountRepository.
     * @param accountRepository the repository for accessing account data.
     */
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Registers a new user account.
     * The registration is successful if the username is not blank, the password is at least 4 characters long,
     * and the username is not already taken.
     *
     * @param account the Account object containing the registration details.
     * @return the newly created Account object if registration is successful, otherwise null.
     */
    public Account register(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank() || account.getPassword() == null || account.getPassword().length() < 4) {
            return null;
        }
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            return null;
        }
        return accountRepository.save(account);
    }

    /**
     * Authenticates a user based on their username and password.
     * @param account the Account object containing the login credentials.
     * @return the authenticated Account object if the credentials are valid, otherwise null.
     */
    public Account login(Account account) {
        return accountRepository.findByUsername(account.getUsername());
    }
}
