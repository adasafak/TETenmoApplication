package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDAO {
    Account getAccount(int accountId);

    BigDecimal getBalance(int accountId);

    boolean updateAccount(Account account);

    boolean deleteAccount(int accountId);

    int getAccountIdByUserId(int userId);

    BigDecimal addBalanceToAnAccount(int accountId, BigDecimal transferMoney);

    BigDecimal subtractBalanceFromAnAccount(int accountId, BigDecimal transferMoney);
}
