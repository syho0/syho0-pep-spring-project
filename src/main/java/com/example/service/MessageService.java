package com.example.service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling business logic related to messages.
 * This class interacts with the MessageRepository and AccountRepository to perform operations such as creating,
 * retrieving, updating, and deleting messages.
 */
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    /**
     * Constructs a new MessageService with the given MessageRepository and AccountRepository.
     * @param messageRepository the repository for accessing message data.
     * @param accountRepository the repository for accessing account data.
     */
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Creates a new message.
     * The message is created successfully if the message text is not blank, is not over 255 characters, and the
     * user posting the message exists.
     * @param message the Message object containing the message details.
     * @return the newly created Message object if creation is successful, otherwise null.
     */
    public Message createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().isBlank() || message.getMessageText().length() > 255
                || !accountRepository.existsById(message.getPostedBy())) {
            return null;
        }
        return messageRepository.save(message);
    }

    /**
     * Retrieves all messages.
     * @return a list of all messages.
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Retrieves a message by its ID.
     * @param id the ID of the message to retrieve.
     * @return an Optional containing the message if found, or an empty Optional if not.
     */
    public Optional<Message> getMessageById(int id) {
        return messageRepository.findById(id);
    }

    /**
     * Deletes a message by its ID.
     * @param id the ID of the message to delete.
     */
    public void deleteMessage(int id) {
        messageRepository.deleteById(id);
    }

    /**
     * Updates the text of a message.
     * The update is successful if the message exists and the new message text is not blank and is not over 255
     * characters.
     * @param id the ID of the message to update.
     * @param message the Message object containing the new message text.
     * @return the updated Message object if the update is successful, otherwise null.
     */
    public Message updateMessage(int id, Message message) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            Message existingMessage = optionalMessage.get();
            if (message.getMessageText() != null && !message.getMessageText().isBlank() && message.getMessageText().length() <= 255) {
                existingMessage.setMessageText(message.getMessageText());
                return messageRepository.save(existingMessage);
            }
        }
        return null;
    }

    /**
     * Retrieves all messages posted by a specific user.
     * @param userId the ID of the user.
     * @return a list of all messages posted by the specified user.
     */
    public List<Message> getMessagesByUser(int userId) {
        return messageRepository.findByPostedBy(userId);
    }
}
