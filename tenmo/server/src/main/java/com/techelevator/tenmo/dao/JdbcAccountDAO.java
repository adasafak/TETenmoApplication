package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDAO  implements AccountDAO{

    JdbcTemplate jdbcTemplate;
    UserDao userDao;

    public JdbcAccountDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Account getAccount(int accountId) {
        Account account  = null;
        String sql = "SELECT * FROM account WHERE account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        if(results.next()){
            account= mapRowToAccount(results);
        }
        return account;
    }
    @Override
    public int getAccountIdByUserId(int userId) {
        int accountId = 0;
        String sql = " SELECT account_id FROM account WHERE user_id = ?";
       accountId = jdbcTemplate.queryForObject(sql, Integer.class,userId);
        return accountId;
    }

    @Override
    public BigDecimal addBalanceToAnAccount(int accountId, BigDecimal transferMoney) {

        String sql1 = " UPDATE account SET balance = balance + ? WHERE account_id = ?";
        jdbcTemplate.update(sql1, transferMoney, accountId);
        return null;
    }

    @Override
    public BigDecimal subtractBalanceFromAnAccount(int accountId, BigDecimal transferMoney) {
        String sql2 = " UPDATE account SET balance = balance - ? WHERE account_id = ?";
        jdbcTemplate.update(sql2, transferMoney, accountId);
        return null;
    }



    @Override
    public BigDecimal getBalance(int accountId) {
        BigDecimal balance = BigDecimal.ZERO;
        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,accountId);
        if(results.next()){
            balance= mapRowToAccount(results).getBalance();
        }
        return balance;
    }

    private Account mapRowToAccount(SqlRowSet results) {
        int id = results.getInt("account_id");
        int userId = results.getInt("user_id");
        BigDecimal balance = results.getBigDecimal("balance");
        return new Account(id,userId,balance);

    }

    @Override
    public boolean updateAccount(Account account) {
        return false;
    }

    @Override
    public boolean deleteAccount(int accountId) {
        return false;
    }


}
