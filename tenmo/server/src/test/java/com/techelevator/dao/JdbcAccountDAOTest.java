package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDAO;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class JdbcAccountDAOTest extends BaseDaoTests {
    private static final Account ACCOUNT_1 = new Account(2001,1001, new BigDecimal(1000));
    private static final Account ACCOUNT_2 = new Account(2002,1002, new BigDecimal(1000));
    private JdbcAccountDAO sut;
    private JdbcUserDao dut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDAO(jdbcTemplate);
        dut = new JdbcUserDao(jdbcTemplate);
    }

    @Test
    public void createNewAccount() {
        boolean accountCreated = dut.create("TEST_USER","test_password");
        Assert.assertTrue(accountCreated);
        User user = dut.findByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", user.getUsername());
    }
    @Test
    public void getAccount(){
        Account account = sut.getAccount(2001);
        assertAccountsMatch(ACCOUNT_1, account);

        account = sut.getAccount(2002);
        assertAccountsMatch(ACCOUNT_2,account);

        account = sut.getAccount(99);
        Assert.assertNull(account);
    }

    @Test
    public void getAccountIdByUserId(){
        int expectedAccountId = sut.getAccountIdByUserId(1001);
        int actualAccountId = ACCOUNT_1.getUserId();

        Assert.assertEquals(expectedAccountId,actualAccountId);

        int expectedAccountId2 = sut.getAccountIdByUserId(1002);
        int actualAccountId2 = ACCOUNT_2.getUserId();

        Assert.assertEquals(expectedAccountId2,actualAccountId2);


    }

    @Test
    public void getBalance(){
        BigDecimal expectedBalance = sut.getBalance(2001);
        BigDecimal actualBalance = ACCOUNT_1.getBalance();

        Assert.assertEquals(expectedBalance, actualBalance);
        BigDecimal expectedBalance2 = sut.getBalance(2002);
        BigDecimal actualBalance2 = ACCOUNT_2.getBalance();

        Assert.assertEquals(expectedBalance, actualBalance);


    }
    private void assertAccountsMatch(Account expected, Account actual) {
        Assert.assertEquals(expected.getAccountId(), actual.getAccountId());
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getBalance(),actual.getBalance());
    }

}