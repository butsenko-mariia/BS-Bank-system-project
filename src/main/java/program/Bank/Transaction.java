package program.Bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.Enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Transaction {
    private static final Logger log = LogManager.getLogger(Transaction.class);

    private UUID id;
    private LocalDate open_date;
    private LocalTime open_time;
    private BigDecimal sum;
    private String currency;
    private String operation_info;
    private char sign;
    private UUID account_id_from;
    private UUID account_id_to;
    private TransactionStatus status;

    public Transaction() {
        this.setId();
        this.setOpen_date();
        this.setOpen_time();
        this.currency = "UAH";
        this.operation_info = "Default transaction info";
        log.debug("Created new empty Transaction with ID: {}.", this.id);
    }

    public Transaction(UUID id){
        this.setId(id);
        log.debug("Initialized Transaction with existing ID: {}.", id);
    }

    public UUID getId() {
        return id;
    }

    public void setId() {
        if (id != null) {
            String msg = "Error: Attempt to overwrite existing Transaction ID.";
            log.error(msg);
            throw new IllegalStateException(msg);
        }
        this.id = UUID.randomUUID();
    }

    public void setId(UUID id) {
        if (id == null) {
            String msg = "Error: Attempt to set null Transaction ID.";
            log.error(msg);
            throw new NumberFormatException(msg);
        }
        this.id = id;
    }

    public LocalDate getOpen_date() {
        return open_date;
    }

    public void setOpen_date() {
        this.open_date = LocalDate.now();
    }

    public void setOpen_date(LocalDate open_date) {
        if (open_date == null) {
            String msg = "Transaction Date cannot be null.";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (open_date.isAfter(LocalDate.now())) {
            String msg = "Transaction Date cannot be in the future: " + open_date;
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        this.open_date = open_date;
    }

    public LocalTime getOpen_time() {
        return open_time;
    }

    public void setOpen_time() {
        this.open_time = LocalTime.now();
    }

    public void setOpen_time(LocalTime open_time) {
        if (open_time == null) {
            String msg = "Transaction Time cannot be null.";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        this.open_time = open_time;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        if (sum == null) {
            String msg = "Sum cannot be null.";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (sum.compareTo(BigDecimal.ZERO) == 0) {
            String msg = "Transaction sum cannot be zero.";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        this.sum = sum;

        if (this.sum.compareTo(BigDecimal.ZERO) > 0){
            this.sign = '+';
        } else  {
            this.sign = '-';
        }
        log.debug("Transaction sum set: {} (Sign: {}).", sum, this.sign);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        if (currency == null || currency.trim().isEmpty()) {
            String msg = "Currency cannot be empty.";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (!currency.matches("^[A-Z]{3}$")) {
            String msg = "Invalid currency format '" + currency + "'. Must be 3 uppercase letters.";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        this.currency = currency;
    }

    public String getOperation_info() {
        return operation_info;
    }

    public void setOperation_info(String operation_info) {
        if (operation_info == null || operation_info.trim().isEmpty()) {
            String msg = "Operation info cannot be empty.";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (operation_info.trim().length() < 3) {
            String msg = "Operation info is too short. Please provide details.";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        this.operation_info = operation_info;
    }

    public UUID getAccount_id_from() {
        return account_id_from;
    }

    public void setAccount_id_from(UUID account_id_from) {
        if (account_id_from == null) {
            String msg = "Sender Account ID cannot be null.";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (this.account_id_to != null && this.account_id_to.equals(account_id_from)) {
            String msg = "Sender Account cannot be the same as Receiver Account.";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }

        this.account_id_from = account_id_from;
        log.debug("Sender account ID set: {}.", account_id_from);
    }

    public UUID getAccount_id_to() {
        return account_id_to;
    }

    public void setAccount_id_to(UUID account_id_to) {
        if (account_id_to == null) {
            String msg = "Receiver Account ID cannot be null.";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (this.account_id_from != null && this.account_id_from.equals(account_id_to)) {
            String msg = "Receiver Account cannot be the same as Sender Account.";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }

        this.account_id_to = account_id_to;
        log.debug("Receiver account ID set: {}.", account_id_to);
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        if (status == null) {
            String msg = "Transaction status cannot be null.";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        this.status = status;
        log.debug("Transaction status updated: {}.", status);
    }

    public void Fetch(){
        log.debug("Fetching transaction data from DB for ID: {}.", this.id);
        String query = "SELECT * FROM transaction WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();

             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setObject(1, this.id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    if (rs.getDate("open_date") != null)
                        this.setOpen_date(rs.getDate("open_date").toLocalDate());

                    java.sql.Time sqlTime = rs.getTime("open_time");
                    if (sqlTime != null) {
                        this.setOpen_time(sqlTime.toLocalTime());
                    }

                    this.setSum(rs.getBigDecimal("sum"));
                    this.setCurrency(rs.getString("currency"));
                    this.setOperation_info(rs.getString("operation_info"));

                    String signStr = rs.getString("sign");
                    if (signStr != null && !signStr.isEmpty()) {
                        this.sign = signStr.charAt(0); // Присвоюємо першу літеру
                    }

                    this.setAccount_id_from((UUID) rs.getObject("account_id_from"));
                    this.setAccount_id_to((UUID) rs.getObject("account_id_to"));

                    String statusStr = rs.getString("status");
                    if (statusStr != null) {
                        this.setStatus(TransactionStatus.valueOf(statusStr));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Помилка при завантаженні транзакції: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return this.open_date +" "+this.open_time+"\n" +
                "Info: " + this.operation_info + "\n" +
                "Amount: " + this.sign + this.sum + " "+this.currency+"\n" +
                "Status: " + this.status + "\n" +
                "ID: "+this.id+"\n";
    }

    public void PrintInfo(){
        String info = this.toString();
        System.out.println(info);
        log.info("Print transaction info:\n{}", info);
    }
}