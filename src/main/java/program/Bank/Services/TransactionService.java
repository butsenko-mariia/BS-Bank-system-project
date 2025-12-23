package program.Bank.Services;

import program.Bank.Builders.TransactionBuilder;
import program.Bank.DataBase;
import program.Bank.Transaction;
import program.Bank.Enums.TransactionStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionService {
    private final Logger log = LogManager.getLogger(TransactionService.class);
    private final DataBase dataBase;

    public TransactionService(DataBase dataBase) {
        this.dataBase = dataBase;
    }


    public void createTransaction(UUID fromAccountId, UUID toAccountId, BigDecimal amount, String currency, String operationInfo) {
        log.info("Creating transaction log. Amount: {} {}", amount, currency);

        try {
            Transaction transaction = TransactionBuilder.create()

                    .account_id_from(fromAccountId)
                    .account_id_to(toAccountId)
                    .sum(amount)
                    .currency(currency)
                    .operation_info(operationInfo)
                    .open_date(LocalDate.now())
                    .open_time(LocalTime.now())
                    .status(TransactionStatus.COMPLETED)
                    .build();

            dataBase.Upload(transaction);

            log.info("Transaction recorded successfully with ID: {}", transaction.getId());

        } catch (Exception e) {
            log.error("Failed to record transaction: {}", e.getMessage());
        }
    }


    public List<Transaction> getTransactionHistory(UUID accountId) {
        log.info("Fetching transaction history for account: {}", accountId);

        List<Transaction> history = new ArrayList<>();

        String query = "SELECT id FROM transaction " +
                "WHERE account_id_from = ? OR account_id_to = ? " +
                "ORDER BY open_date DESC, open_time DESC";

        try (Connection connection = dataBase.Connection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, accountId);
            statement.setObject(2, accountId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                UUID transId = (UUID) rs.getObject("id");

                Transaction t = new Transaction(transId);
                dataBase.Fetch(t);


                if (t.getAccount_id_from() != null && t.getAccount_id_from().equals(accountId)) {
                    t.setSign("-");
                } else {
                    t.setSign("+");
                }

                history.add(t);
            }
            log.info("Found {} transactions for account {}", history.size(), accountId);

        } catch (SQLException e) {
            log.error("SQL Error while loading history: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
        }

        return history;
    }


    public Transaction getTransactionById(UUID id) {
        log.debug("Looking for transaction by ID: {}", id);
        Transaction transaction = new Transaction(id);
        dataBase.Fetch(transaction);

        if (transaction.getOpen_date() == null) {
            log.warn("Transaction not found in DB: {}", id);
            return null;
        }
        return transaction;
    }
}