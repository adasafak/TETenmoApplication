package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDAO implements TransferDao {

    JdbcTemplate jdbcTemplate;
    @Autowired
    AccountDAO accountDAO;
    @Autowired
    UserDao userDao;

    public JdbcTransferDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    //    @Override
//    public Transfer create1Transfer(int senderAccountId, int receiverAccountId, BigDecimal transferMoney, boolean sendingStatus) {
//        String sql = "INSERT INTO transfer(sender_account_id, receiver_account_id,transfer_money, sending_status) VALUES (?,?,?,?) RETURNING transfer_id";
//        Integer transferId = jdbcTemplate.queryForObject(sql,Integer.class, senderAccountId, receiverAccountId,  transferMoney, sendingStatus);
//        Transfer transfer = getTransferByTransferId(transferId);
//        return transfer;
//    }

    @Override
    public Transfer getTransferByUserId(int userId) {
        return null;
    }

    @Override
    public Transfer getTransferByTransferId(int transferId) {
        Transfer transfer = null;
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    @Override
    public Transfer updateTransfer(int transferId) {
        return null;
    }

    @Override
    public Transfer deleteTransfer(int transferId) {
        return null;
    }

    @Override
    public List<Transfer> getTransfers(int senderUserId, int receiverUserId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * FROM transfer WHERE sender_user_id =? AND receiver_user_id =? ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, senderUserId, receiverUserId);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    @Override
    public Transfer createTransfer(Transfer newTransfer) {
        Transfer transfer = null;
        int senderAccountId = accountDAO.getAccountIdByUserId(newTransfer.getSenderUserId());
        int receiverAccountId = accountDAO.getAccountIdByUserId(newTransfer.getReceiverUserId());


        if (newTransfer.getSenderUserId() != newTransfer.getReceiverUserId()) {
        if(newTransfer.getTransferMoney().compareTo(BigDecimal.ZERO) <= 0){
            return transfer;
        } else if (newTransfer.getTransferMoney().compareTo(accountDAO.getBalance(senderAccountId)) <= 0){
            accountDAO.subtractBalanceFromAnAccount(senderAccountId, newTransfer.getTransferMoney());
            accountDAO.addBalanceToAnAccount(receiverAccountId, newTransfer.getTransferMoney());
            String sql = "INSERT INTO transfer(sender_user_id, receiver_user_id,transfer_money, sending_status) VALUES (?,?,?,?) RETURNING transfer_id";
            Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class, newTransfer.getSenderUserId(), newTransfer.getReceiverUserId(), newTransfer.getTransferMoney(), newTransfer.getStatus());
            transfer = getTransferByTransferId(transferId);
                 }
           return transfer;
             }
          return null;
}
    private Transfer mapRowToTransfer(SqlRowSet results) {
        int id = results.getInt("transfer_id");
        int senderUserId = results.getInt("sender_user_id");
        int receiverUserId = results.getInt("receiver_user_id");
        BigDecimal transferMoney = results.getBigDecimal("transfer_money");
        String status = results.getString("sending_status");
        return new Transfer (id,senderUserId,receiverUserId,transferMoney,status);

    }
}
