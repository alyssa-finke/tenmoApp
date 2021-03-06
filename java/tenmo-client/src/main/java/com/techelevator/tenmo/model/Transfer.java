package com.techelevator.tenmo.model;

import io.cucumber.java.sl.In;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private int transferType;
    private int transferStatus;
    private int accountFrom;
    private int userTo;
    private BigDecimal transferAmount;
    private String fromUsername;
    private String toUsername;

    public Transfer(int transferId, int transferType, int transferStatus, int accountFrom, int accountTo, BigDecimal transferAmount, String fromUsername, String toUsername) {
        this.transferId = transferId;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.accountFrom = accountFrom;
        this.userTo = userTo;
        this.transferAmount = transferAmount;
        this.fromUsername = fromUsername;
        this.toUsername = toUsername;
    }

    public Transfer() {
    }

    public Transfer(int userTo, BigDecimal transferAmount) {
        this.userTo = userTo;
        this.transferAmount = transferAmount;
    }

    public Transfer(int accountFrom, int accountTo, BigDecimal transferAmount) {
    }

    public int getTransferId() {
        return transferId;
    }

    public int getTransferType() {
        return transferType;
    }

    public int getTransferStatus() {
        return transferStatus;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public int getUserTo() {
        return userTo;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public void setTransferStatus(int transferStatus) {
        this.transferStatus = transferStatus;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public void setUserTo(int accountTo) {
        this.userTo = accountTo;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public String getToUsername() {
        return toUsername;
    }
}

