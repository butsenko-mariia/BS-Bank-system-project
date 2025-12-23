package program.Bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.Enums.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Transaction {
    private static final Logger log = LogManager.getLogger(Transaction.class);

    private UUID id;
    private LocalDate open_date;
    private LocalTime open_time;
    private BigDecimal sum;
    private String currency;
    private String operation_info;
    private String sign;
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
            this.sign = "+";
        } else  {
            this.sign = "-";
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
        // ВИДАЛЯЄМО перевірку на null!
        // Гроші можуть прийти "нізвідки" (наприклад, поповнення готівкою)
        this.account_id_from = account_id_from;
    }

    public UUID getAccount_id_to() {
        return account_id_to;
    }

    public void setAccount_id_to(UUID account_id_to) {
        // ВИДАЛЯЄМО перевірку на null!
        // Гроші можуть піти "в нікуди" (магазин, банкомат)
        this.account_id_to = account_id_to;
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

    public void setSign(String sign) {
        if (sign == null || sign.trim().isEmpty() || (!sign.equals("+") && !sign.equals("-"))){
            log.error("Invalid sign string.");
            throw new IllegalArgumentException();
        }
        this.sign = sign;
    }

    public String getSign() {
        return sign;
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