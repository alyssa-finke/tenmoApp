package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountsDAO implements AccountsDAO {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    //SQL statement to get account balance
    @Override
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


}
