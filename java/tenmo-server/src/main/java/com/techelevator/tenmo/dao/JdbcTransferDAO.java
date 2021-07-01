package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDAO implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    //Constructor
    public JdbcTransferDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    //Should we include accountFrom and accountTo in parameters?
    //Do we need account_from and account_to in the sql string?
    public BigDecimal transferTransaction(int transferAmount) {
        String sql = "SELECT account_from, account_to, amount " +
                "FROM transfers " +
                "JOIN accounts ON transfers.account_from = accounts.account_id " +
                "WHERE account_to = ?;";
        BigDecimal amountTransferred = jdbcTemplate.queryForObject(sql, BigDecimal.class, transferAmount);
        return amountTransferred;

    }


//do i have the money to tranfer? if so, subtract from mine, add to yours, and insert a row into transfer
//run sql insert into database
//create 1 method to insert transfer
//1 method to update account balances
//then call for both

//client should just create a request


}

