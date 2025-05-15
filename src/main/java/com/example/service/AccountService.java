package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.DuplicateAccountException;
import com.example.exception.UnauthorizedLoginException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<String> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public Optional<Account> findById(Integer id) {
        return accountRepository.findById(id);
    }

    public Account registerAccount(Account account) {
        if (account.getUsername().isEmpty()) throw new IllegalArgumentException("Username is required");
        if (account.getPassword().length() < 4) throw new IllegalArgumentException("Password is too short");
        if (this.findByUsername(account.getUsername()).isPresent()) throw new DuplicateAccountException("Username already exists");
        
        return accountRepository.save(account);
    }

    public Account loginAccount(Account account) {
        Optional<Account> foundAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (foundAccount.isPresent()) {
            return foundAccount.get();
        }
        throw new UnauthorizedLoginException("Credentials error");
    }
}
