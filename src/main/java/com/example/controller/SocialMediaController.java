package com.example.controller;

import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.DuplicateAccountException;
import com.example.exception.UnauthorizedLoginException;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController (AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        try {
            Account savedAccount = this.accountService.registerAccount(account);
            return ResponseEntity.status(HttpStatus.OK).body(savedAccount);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (DuplicateAccountException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) {
        try {
            Account accountFound = accountService.loginAccount(account);
            return ResponseEntity.status(HttpStatus.OK).body(accountFound);
        } catch (UnauthorizedLoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message) {
        try {
            Message savedMessage = messageService.saveMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(savedMessage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }
}
