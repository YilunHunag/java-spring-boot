package com.example.wizaccountsystem.service;

import com.example.wizaccountsystem.model.Account;
import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private Map<String, Account> accountMap = new HashMap<>(); // 儲存帳戶的集合

    // 新增帳戶
    public void addAccount(String accountNumber, String accountHolderName, double initialBalance) {
        Account account = new Account(accountNumber, accountHolderName, initialBalance);
        accountMap.put(accountNumber, account);
    }

    // 查詢帳戶餘額
    public double getBalance(String accountNumber) {
        Account account = accountMap.get(accountNumber);
        if (account != null) {
            return account.getBalance();
        }
        return -1;  // 若找不到帳戶，返回-1
    }

    // 存款
    public void deposit(String accountNumber, double amount) {
        Account account = accountMap.get(accountNumber);
        if (account != null) {
            double newBalance = account.getBalance() + amount;
            account.setBalance(newBalance);
        }
    }

    // 提款
    public void withdraw(String accountNumber, double amount) {
        Account account = accountMap.get(accountNumber);
        if (account != null && account.getBalance() >= amount) {
            double newBalance = account.getBalance() - amount;
            account.setBalance(newBalance);
        }
    }
}

