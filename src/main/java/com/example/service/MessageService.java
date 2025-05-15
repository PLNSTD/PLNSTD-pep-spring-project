package com.example.service;

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
}
