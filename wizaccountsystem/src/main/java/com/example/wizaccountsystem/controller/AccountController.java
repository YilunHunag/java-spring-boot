package com.example.wizaccountsystem.controller;

import org.springframework.web.bind.annotation.*;
import com.example.wizaccountsystem.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService = new AccountService();

    // 建立帳戶
    @PostMapping
    public String createAccount(@RequestParam String accountNumber, @RequestParam String accountHolderName, @RequestParam double initialBalance){
        accountService.addAccount(accountNumber, accountHolderName, initialBalance);
        return "帳戶創建成功！";
    }

    // 查詢餘額
    @GetMapping("/{accountNumber}/balance")
    public double getBalance(@PathVariable String accountNumber) {
        return accountService.getBalance(accountNumber);
    }

    // 存款
    @PostMapping("/{accountNumber}/deposit")
    public String deposit(@PathVariable String accountNumber, @RequestParam double amount) {
        accountService.deposit(accountNumber, amount);
        return "存款成功！";
    }

    // 提款
    @PostMapping("/{accountNumber}/withdraw")
    public String withdraw(@PathVariable String accountNumber, @RequestParam double amount) {
        accountService.withdraw(accountNumber, amount);
        return "提款成功！";
    }
}

