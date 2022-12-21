package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private UserDao userDao;


    @GetMapping("/login/{id}")
    public Account seeAccountBalance(@PathVariable int id){
    Account account = accountDAO.getAccount(id);
    if(account == null){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found!!!");
    } else {
        return account;
     }
  }

    @GetMapping("/balance")
    public BigDecimal getBalance( Principal principal){
        int userId = userDao.findIdByUsername(principal.getName());
        int accountId= accountDAO.getAccountIdByUserId(userId);
                return accountDAO.getBalance(accountId);
    }
  }
