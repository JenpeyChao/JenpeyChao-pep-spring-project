package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer>{
    @Modifying
    @Transactional
    @Query("DELETE FROM Message c WHERE c.messageId = :messageId")
    int deleteMessageByMessageId(int messageId);

    @Query("SELECT m FROM Message m WHERE m.postedBy = :postedBy")
    List<Message> findAllMessageByPostedBy(int postedBy);

    @Query("SELECT m FROM Message m WHERE m.messageId = :messageId")
    Message findMessageByMessageId(int messageId);

    @Transactional
    @Modifying
    @Query("UPDATE Message m SET m.messageText = :messageText WHERE m.messageId = :messageId")
    int patchMessageByMessageId(Integer messageId, String messageText);
}
