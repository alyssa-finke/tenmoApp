package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountsDAO implements AccountsDAO {

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
    }
