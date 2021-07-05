package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountsDAO {

    BigDecimal getAccountBalance(int loggedInUserId);

    int getAccountId(int userId);

    Account ACCOUNT = new Account();

}
