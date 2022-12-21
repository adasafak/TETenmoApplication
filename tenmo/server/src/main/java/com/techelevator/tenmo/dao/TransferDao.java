package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {
//    Transfer create1Transfer(int senderAccountId, int receiverAccountId, BigDecimal transferMoney,boolean approved);
    Transfer getTransferByUserId( int userId);
    Transfer getTransferByTransferId(int transferId);
    Transfer updateTransfer(int transferId);
    Transfer deleteTransfer(int transferId);
    List<Transfer> getTransfers(int senderUserId, int receiverUserId);

    Transfer createTransfer(Transfer transfer);
}
