package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A Spring Data JPA repository for the Message entity.
 * This interface provides methods for performing CRUD operations on the "message" table,
 * as well as custom queries.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    /**
     * Finds all messages posted by a specific user.
     * @param postedBy the ID of the user.
     * @return a list of all messages posted by the user.
     */
    List<Message> findByPostedBy(int postedBy);
}
