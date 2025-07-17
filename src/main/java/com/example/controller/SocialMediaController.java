package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for handling all incoming HTTP requests to the Social Media API.
 * This class defines the API endpoints and maps them to the appropriate service methods.
 */
@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    /**
     * Constructs a new SocialMediaController with the given AccountService and MessageService.
     * @param accountService the service for handling account-related business logic.
     * @param messageService the service for handling message-related business logic.
     */
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * Endpoint for user registration. Corresponds to User Story 1.
     * @param account The account details from the request body.
     * @return A response entity containing the created account and a 200 OK status,
     *         a 409 Conflict status if the username is taken, or a 400 Bad Request status for other validation errors.
     */
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        Account registeredAccount = accountService.register(account);
        if (registeredAccount != null) {
            return new ResponseEntity<>(registeredAccount, HttpStatus.OK);
        } else if (accountService.login(account) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint for user login. Corresponds to User Story 2.
     * @param account The login credentials from the request body.
     * @return A response entity containing the authenticated account and a 200 OK status,
     *         or a 401 Unauthorized status if the login fails.
     */
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account foundAccount = accountService.login(account);
        if (foundAccount != null && foundAccount.getPassword().equals(account.getPassword())) {
            return new ResponseEntity<>(foundAccount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Endpoint for creating a new message. Corresponds to User Story 3.
     * @param message The message details from the request body.
     * @return A response entity containing the created message and a 200 OK status,
     *         or a 400 Bad Request status if the message is invalid.
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        if (createdMessage != null) {
            return new ResponseEntity<>(createdMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint for retrieving all messages. Corresponds to User Story 4.
     * @return A response entity containing a list of all messages and a 200 OK status.
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return new ResponseEntity<>(messageService.getAllMessages(), HttpStatus.OK);
    }

    /**
     * Endpoint for retrieving a message by its ID. Corresponds to User Story 5.
     * @param messageId The ID of the message to retrieve.
     * @return A response entity containing the message if found, or an empty body, both with a 200 OK status.
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        Optional<Message> message = messageService.getMessageById(messageId);
        return message.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.OK));
    }

    /**
     * Endpoint for deleting a message by its ID. Corresponds to User Story 6.
     * @param messageId The ID of the message to delete.
     * @return A response entity with the number of rows affected (1 if successful) or an empty body,
     *         both with a 200 OK status.
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int messageId) {
        Optional<Message> message = messageService.getMessageById(messageId);
        if (message.isPresent()) {
            messageService.deleteMessage(messageId);
            return new ResponseEntity<>(1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    /**
     * Endpoint for updating a message. Corresponds to User Story 7.
     * @param messageId The ID of the message to update.
     * @param message The new message text in the request body.
     * @return A response entity with the number of rows affected (1 if successful) and a 200 OK status,
     *         or a 400 Bad Request status if the update fails.
     */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Message message) {
        Message updatedMessage = messageService.updateMessage(messageId, message);
        if (updatedMessage != null) {
            return new ResponseEntity<>(1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint for retrieving all messages from a specific user. Corresponds to User Story 8.
     * @param accountId The ID of the user whose messages are to be retrieved.
     * @return A response entity containing a list of messages from the user and a 200 OK status.
     */
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable int accountId) {
        return new ResponseEntity<>(messageService.getMessagesByUser(accountId), HttpStatus.OK);
    }
}
