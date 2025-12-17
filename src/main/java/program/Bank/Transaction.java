package program.Bank;

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
        this.operation_info = "no info";
    }
    public Transaction(UUID id){
        this.setId(id);
    }
    public UUID getId() {
        return id;
    }

    public void setId() {
        if (id != null) {
            throw  new IllegalStateException("Transaction ID is already set");
        }
        this.id = UUID.randomUUID();
    }

    public void setId(UUID id) {
        if (id == null) {
            throw  new NumberFormatException("ID must be not null");
        }
        this.id =  id;
    }

    public LocalDate getOpen_date() {
        return open_date;
    }
    public void setOpen_date() {
        this.open_date = LocalDate.now();
    }
    public void setOpen_date(LocalDate open_date) {
        this.open_date = open_date;
    }
    public LocalTime getOpen_time() {
        return open_time;
    }
    public void setOpen_time() {
        this.open_time = LocalTime.now();
    }
    public void setOpen_time(LocalTime open_time) {
        this.open_time = open_time;
    }
    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        if (sum.compareTo(BigDecimal.ZERO) == 0)
            throw new IllegalArgumentException("Sum must not be zero.");
        this.sum = sum;
        if (this.sum.compareTo(BigDecimal.ZERO) > 0){
            this.sign = '+';
        }
        else  {
            this.sign = '-';
        }
    }
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        if (currency == null || currency.trim().isEmpty()) {
            throw new IllegalArgumentException("Currency cannot be empty.");
        }
        if (!currency.matches("^[A-Z]{3}$")) { // наприклад, USD, EUR, UAH
            throw new IllegalArgumentException("Currency must be 3 uppercase letters (e.g., USD, EUR).");
        }
        this.currency = currency;
    }

    public String getOperation_info() {
        return operation_info;
    }
    public void setOperation_info(String operation_info) {
        this.operation_info = operation_info;
    }
    public UUID getAccount_id_from() {
        return account_id_from;
    }

    public void setAccount_id_from(UUID account_id_from) {
        //дописати потім перевірку чи існує такий ід
        this.account_id_from = account_id_from;
    }

    public UUID getAccount_id_to() {
        return account_id_to;
    }

    public void setAccount_id_to(UUID account_id_to) {
        //дописати потім перевірку чи існує такий ід
        this.account_id_to = account_id_to;
    }

    public TransactionStatus getStatus() {
        return status;
    }
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public void Fetch(){
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
                this.operation_info + "\n" +
                this.sign +" "+this.sum + " "+this.currency+"\n" +
                "ID: "+this.id+"\n";
    }

    public void PrintInfo(){
        System.out.println(this.toString());
    }
}
