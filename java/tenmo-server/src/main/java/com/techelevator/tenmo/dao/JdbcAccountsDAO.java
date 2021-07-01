package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.Principal;

@Component
public class JdbcAccountsDAO implements AccountsDAO {

    private JdbcTemplate jdbcTemplate;
   Accounts accounts = new Accounts();

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

    //#4 The senders account balance is decreased by the amount of the transfer.
    @Override //can't figure out what the setBalance wants below
    //Do i need to add Principal principal in parameters? If so, add to params in AccountsDAO, too.
    public void subtractFromAccount(BigDecimal transferAmount, Principal principal) {
        if(accounts.canTransfer()){
            String loggedInUserName = principal.getName();
            accounts.setBalance(getAccountBalance() = accounts.getBalance().subtract(transferAmount);
        }
    }

//Should be able to make similar method for adding: Add in AccountsDAO then here
//Should i be doing this here or in JdbcTransferDAO
    //Sql strings transfer from and transfer to
}
