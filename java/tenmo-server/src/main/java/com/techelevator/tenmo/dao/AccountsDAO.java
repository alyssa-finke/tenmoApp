package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;

import java.math.BigDecimal;
import java.security.Principal;

public interface AccountsDAO {

    BigDecimal getAccountBalance(int loggedInUserId);

    //initialized accounts here so that I can use canTransfer from Accounts class.
    Accounts accounts = new Accounts();

    //Created this and using @Override in JDBC
    //Changed this to return void. Should it be returning void or big decimal?
    //public void subtractFromAccount(BigDecimal transferAmount);

    //findAccountsByUsername
    //return an account based on the username passed in
}

//getaccount that returns an account
//updating accounts here
