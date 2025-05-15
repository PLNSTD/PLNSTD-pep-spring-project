package com.example.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private AccountService accountService;

    public MessageService (MessageRepository messageRepository, AccountService accountService) {
        this.messageRepository = messageRepository;
        this.accountService = accountService;
    }

    public Message saveMessage(Message message) {
        if (message.getMessageText().length() == 0) throw new IllegalArgumentException("Message is empty");
        if (message.getMessageText().length() > 255) throw new IllegalArgumentException("Message is too long");
        if (accountService.findById(message.getPostedBy()).isEmpty()) throw new IllegalArgumentException("User not exists");

        return messageRepository.save(message);
    }   

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageByID(Integer Id) {
        return messageRepository.findById(Id).orElse(null);
    }

    public void deleteMessageById(Integer Id) {
        if (Id == null) throw new IllegalArgumentException("Id required");
        if (!messageRepository.existsById(Id)) throw new EntityNotFoundException("Message with ID " + Id + " not found");
        messageRepository.deleteById(Id);
    }

    public void updateMessageById(Integer Id, Message newMessage) {
        String newTextMessage = newMessage.getMessageText();
        if (newTextMessage.length() == 0 || newTextMessage.isEmpty() || newTextMessage == null) throw new IllegalArgumentException("Text can not be blank");
        if (newTextMessage.length() > 255) throw new IllegalArgumentException("Text too long");
        Message messageFound = messageRepository.findById(Id).orElseThrow(() -> new EntityNotFoundException("Message with ID " + Id + " not found"));

        messageFound.setMessageText(newTextMessage);

        messageRepository.save(messageFound);
    }

    public List<Message> getMessagesByUserID(Integer Id) {
        accountService.findById(Id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        
        return messageRepository.findByPostedBy(Id);
    }
}
