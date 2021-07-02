package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountsDAO implements AccountsDAO { //only logic to talk to database

    private JdbcTemplate jdbcTemplate;
  // Accounts accounts = new Accounts();

    public JdbcAccountsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override // join user to accounts by userId by username
    public BigDecimal getAccountBalance(int loggedInUserId) {
        String sql = "SELECT balance " +
                "FROM accounts " +
                "WHERE user_id = ?;";

            BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, loggedInUserId);
            return balance;

    }

    @Override
    public int getAccountId(int userId) {
        String sql = "SELECT account_id " +
                "FROM accounts " +
                "WHERE user_id = ?;";
        int accountId = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return accountId;
    }

    //This logic should be somewhere else bc this should only be sql. Put in transfer controller.
    //#4 The senders account balance is decreased by the amount of the transfer.


//Should be able to make similar method for adding: Add in AccountsDAO then here
//Should i be doing this here or in JdbcTransferDAO
    //Sql strings transfer from and transfer to
}
