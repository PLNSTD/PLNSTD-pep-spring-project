package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.DuplicateAccountException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account) {
        if (account.getUsername().isEmpty()) throw new IllegalArgumentException("Username is required");
        if (account.getPassword().length() < 4) throw new IllegalArgumentException("Password is too short");
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) throw new DuplicateAccountException("Username already exists");
        
        return accountRepository.save(account);
    }
}
