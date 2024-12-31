package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
@Service
public class MessageService {
    
    @Autowired
    MessageRepository messageRepository;

    public Message createMessage(Message message){
        return this.messageRepository.save(message);
    }

    public int deleteMessageBymessageId(int messageId) {
        Optional<Message> res = this.messageRepository.findById(messageId);
        System.out.println(res);
        if(res.isPresent()){
            this.messageRepository.deleteMessageByMessageId(messageId);
            return 1;
        }
        return 0;
    }

    public List<Message> findAllMessageByPostedBy(int postedBy) {
        List<Message> res = this.messageRepository.findAllMessageByPostedBy(postedBy);
        for(Message message:res){
            System.out.println(message);
        }

        return res;
        
    }

    public List<Message> getAllMessage() {
        return this.messageRepository.findAll();
    }

    public Message findMessageByMessageId(int messageId) {

        return this.messageRepository.findMessageByMessageId(messageId);
    }

    public int patchMessageByMessageId(Integer messageId, String messageText) {
        return this.messageRepository.patchMessageByMessageId(messageId,messageText);
    }
}
