package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public Account createAccount(Account account){
        return this.accountRepository.save(account);
    }
    public Account findAccountByUsernamePassword(String username,String password){
        return this.accountRepository.findAccountByUsernameAndPassword(username, password);
    }

    public Account findAccountByAccountId(Integer accountId){
        return this.accountRepository.findAccountByAccountId(accountId);
    } 
    public Account findAccountByUsername(String username){
        return this.accountRepository.findAccountByUsername(username);
    }
    public Account login(Account account) {
        
        return this.accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
}
