package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDAO;
import com.techelevator.tenmo.dao.JdbcTransferDAO;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcTransferDAOTest extends BaseDaoTests{
    private static final Transfer TRANSFER_1 = new Transfer(3001,1001,1002,new BigDecimal(50), "Approved");
    private static final Transfer TRANSFER_2 = new Transfer(3002,1002,1001,new BigDecimal(150), "Approved");
    private JdbcTransferDAO sut;
    private Transfer testTransfer;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDAO(jdbcTemplate);
        testTransfer = new Transfer(4000,9999,9991,new BigDecimal(500),"Approved");
    }
    @Test
    public void getTransferByTransferId() {
        Transfer transfer = sut.getTransferByTransferId(3001);
        assertTransferMatch(TRANSFER_1, transfer);

        transfer = sut.getTransferByTransferId(3002);
        assertTransferMatch(TRANSFER_2, transfer);

        transfer = sut.getTransferByTransferId(5000);
        Assert.assertNull(transfer);


    }
    @Test
    public void getTransfers() {
        List<Transfer> transferList = sut.getTransfers( 1001,1002);
        Assert.assertEquals(2,transferList.size());
        assertTransferMatch(TRANSFER_1, transferList.get(0));
        assertTransferMatch(TRANSFER_2, transferList.get(1));

    }
    @Test
    public void createTransfer() {
        Transfer createdTransfer = sut.createTransfer(testTransfer);
        Integer newId = createdTransfer.getTransferId();

        Assert.assertTrue(newId > 0);

        testTransfer.setTransferId(newId);

        assertTransferMatch(testTransfer,createdTransfer);


    }
    private void assertTransferMatch(Transfer expected,Transfer actual) {
        Assert.assertEquals(expected.getTransferId(), actual.getTransferId());
        Assert.assertEquals(expected.getSenderUserId(), actual.getSenderUserId());
        Assert.assertEquals(expected.getReceiverUserId(),actual.getReceiverUserId());
        Assert.assertEquals(expected.getTransferMoney(), actual.getTransferMoney());
        Assert.assertEquals(expected.getStatus(), actual.getStatus());
    }

}