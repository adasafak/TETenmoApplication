package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private int transferId;
    private int senderUserId;
    private int receiverUserId;
    private BigDecimal transferMoney;
    private String status;

    public Transfer() {
    }

    public Transfer(int transferId, int senderUserId, int receiverUserId, BigDecimal transferMoney, String approved) {
        this.transferId = transferId;
        this.senderUserId = senderUserId;
        this.receiverUserId = receiverUserId;
        this.transferMoney = transferMoney;
        this.status = approved;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(int senderUserId) {
        this.senderUserId = senderUserId;
    }

    public int getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(int receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public BigDecimal getTransferMoney() {
        return transferMoney;
    }

    public void setTransferMoney(BigDecimal transferMoney) {
        this.transferMoney = transferMoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transferId=" + transferId +
                ", senderAccountId=" + senderUserId +
                ", receiverAccountId=" + receiverUserId +
                ", transferMoney=" + transferMoney +
                ", sendingStatus=" + status +
                '}';
    }
}
