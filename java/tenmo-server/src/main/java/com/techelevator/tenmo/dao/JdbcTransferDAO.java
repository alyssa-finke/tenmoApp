package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDAO implements TransferDao {

    private JdbcTemplate jdbcTemplate;
    private UserDao userDAO;
    private AccountsDAO accountsDAO;

    //Constructor
    public JdbcTransferDAO(JdbcTemplate jdbcTemplate, AccountsDAO accountsDAO, UserDao userDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountsDAO = accountsDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Account getAccountByUserId(int userId) {
        Account account = null;
        String sql = "SELECT account_id, user_id, balance " +
                "FROM accounts WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            account = mapRowToAccount(results);
        }
        return account;
    }

    //SQL string to credit and update account balances
    private void creditBalance(Account account, int accountToUserId, BigDecimal transferAmount) {
        String sql = "UPDATE accounts " +
                "SET balance = balance + ?" +
                "WHERE user_id = ?;";
        jdbcTemplate.update(sql, transferAmount, account.getUserId());

    }

    //SQL string to debit and update account balances not sure why sender account balance is not updating
    private void debitBalance(int accountFrom, BigDecimal transferAmount) {
        String sql = "UPDATE accounts " +
                "SET balance = balance - ? " +
                "WHERE account_id = ?;";
        jdbcTemplate.update(sql, transferAmount, accountFrom);

    }

    @Override
    public void createNewTransfer(Transfer transfer, String fromUsername) {
        int userId = userDAO.findIdByUsername(fromUsername);
        int accountId = accountsDAO.getAccountId(userId);
        BigDecimal transferAmount = transfer.getTransferAmount();
        BigDecimal accountBalance = accountsDAO.getAccountBalance(userId);
        int accountToUserId = transfer.getUserTo();
        int accountToAccountId = accountsDAO.getAccountId(accountToUserId);
        if (accountBalance.compareTo(transferAmount) == 1) {
            String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                    "VALUES (1, 2, ?, ?, ?);";
            jdbcTemplate.update(sql, accountId, accountToAccountId, transfer.getTransferAmount());
            creditBalance(getAccountByUserId(transfer.getUserTo()), accountToUserId, transferAmount); //
            debitBalance(accountId, transferAmount);

        }
    }

    // #5 see all transfers ive sent or received
  /*  @Override
    public List<Transfer> listMyTransfers(int loggedInUserId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfers " +
                "JOIN accounts ON transfers.account_from = accounts.account_id " +
                "WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, loggedInUserId);
        while (results.next()) {
            Transfer transfer = mapRowToListMyTransfers(results);
            transfers.add(transfer);
        }
        return transfers;
    } */

    @Override //did double join and added username x 2 to map below
    public List<Transfer> listMyTransfers(int loggedInUserId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT uf.username AS userfrom, ut.username AS userto, transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfers " +
                "INNER JOIN accounts AS accf ON account_from = accf.account_id " +
                "INNER JOIN accounts AS acct ON account_to = acct.account_id " +
                "INNER JOIN users AS uf ON accf.user_id = uf.user_id " +
                "INNER JOIN users AS ut ON acct.user_id = ut.user_id " +
                "WHERE accf.user_id = ? OR acct.user_id = ?;"; //question marks correct to do here?
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, loggedInUserId, loggedInUserId); //had to add this extra param because 2 question marks
        while (results.next()) {
            Transfer transfer = mapRowToListMyTransfers(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    // #6 see transfer details for transfer_id
    @Override
    public Transfer viewTransferDetails(int transferId) {
        Transfer transferDetails = null;
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfers " +
                "WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            transferDetails = mapRowToViewTransferDetails(results);
        }
        return transferDetails;
    }



    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }

    private Transfer mapRowToListMyTransfers(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        //added these to the map, using alias from above
        transfer.setFromUsername(rs.getString("userfrom"));
        transfer.setToUsername(rs.getString("userto"));
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferType(rs.getInt("transfer_type_id"));
        transfer.setTransferStatus(rs.getInt("transfer_status_id"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setUserTo(rs.getInt("account_to"));
        transfer.setTransferAmount(rs.getBigDecimal("amount"));
        return transfer;
    }

    private Transfer mapRowToViewTransferDetails(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferType(rs.getInt("transfer_type_id"));
        transfer.setTransferStatus(rs.getInt("transfer_status_id"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setTransferAmount(rs.getBigDecimal("amount"));
        return transfer;
    }




}

/*
can write a bigger sequel joining to all tables
transfers to users
joining out to all the data tables



 */


