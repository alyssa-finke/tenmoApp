package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Accounts {

    private int accountId;
    private int userId;
    private BigDecimal balance;

    public Accounts() {
    }
    public Accounts(int accountId, int userId, BigDecimal balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    Transfer transfer = new Transfer();

    //#5 I can't send more TE Bucks than I have in my account.
    public boolean canTransfer(){
        if(balance.compareTo(transfer.getTransferAmount()) == 1); //this is what I found for comparing two BigDecimals
        return true;
    }

}

//add a tranfer method to this that adds true or false if it's allowed