package program.Bank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private LocalDate open_date;
    private LocalTime open_time;
    private BigDecimal sum;
    private String currency;
    private String operation_type;
    private UUID account_id_from;
    private UUID account_id_to;
    private TransactionStatus status;

    public Transaction() {
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
        if (sum.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Sum must be positive");
        this.sum = sum;
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
    public String getOperation_type() {
        return operation_type;
    }
    public void setOperation_type(String operation_type) {
        this.operation_type = operation_type;
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
        boolean ifMatchSatus = false;
        for (var transactionStatus: TransactionStatus.values()){
            if (status == transactionStatus) {
                this.status = status;
                ifMatchSatus = true;
            }
        }
        if (!ifMatchSatus) {
            throw new IllegalArgumentException("TransactionStatus must match TransactionStatus.");
        }
    }
}
