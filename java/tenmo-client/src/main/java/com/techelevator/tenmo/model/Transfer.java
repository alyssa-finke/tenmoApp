package com.techelevator.tenmo.model;

<<<<<<< HEAD
import io.cucumber.java.sl.In;

=======
>>>>>>> 6da26938dcda08f65dbee55ba960638299078a21
import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private int transferType;
    private int transferStatus;
    private int accountFrom;
    private int accountTo;
<<<<<<< HEAD
    private BigDecimal transferAmount;

    public Transfer(int transferId, int transferType, int transferStatus, int accountFrom, int accountTo, BigDecimal transferAmount) {
=======
    private int transferAmount;

    public Transfer() {
    }

    public Transfer(int transferId, int transferType, int transferStatus, int accountFrom, int accountTo, int transferAmount) {
>>>>>>> 6da26938dcda08f65dbee55ba960638299078a21
        this.transferId = transferId;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.transferAmount = transferAmount;
    }

    public int getTransferId() {
        return transferId;
    }

<<<<<<< HEAD
    public int getTransferType() {
        return transferType;
    }

    public int getTransferStatus() {
        return transferStatus;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

=======
>>>>>>> 6da26938dcda08f65dbee55ba960638299078a21
    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

<<<<<<< HEAD
=======
    public int getTransferType() {
        return transferType;
    }

>>>>>>> 6da26938dcda08f65dbee55ba960638299078a21
    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

<<<<<<< HEAD
=======
    public int getTransferStatus() {
        return transferStatus;
    }

>>>>>>> 6da26938dcda08f65dbee55ba960638299078a21
    public void setTransferStatus(int transferStatus) {
        this.transferStatus = transferStatus;
    }

<<<<<<< HEAD
=======
    public int getAccountFrom() {
        return accountFrom;
    }

>>>>>>> 6da26938dcda08f65dbee55ba960638299078a21
    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

<<<<<<< HEAD
=======
    public int getAccountTo() {
        return accountTo;
    }

>>>>>>> 6da26938dcda08f65dbee55ba960638299078a21
    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

<<<<<<< HEAD
=======
    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

>>>>>>> 6da26938dcda08f65dbee55ba960638299078a21
    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }
}
