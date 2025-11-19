package program.Bank;

import java.math.BigDecimal;
import java.util.UUID;

public class Transaction {
    private String id;
    private String date_and_time;
    private BigDecimal sum;
    private String currency;
    private String operation_type;
    private String account_id_from;
    private String account_id_to;
    private TransactionStatus status;
    public Transaction() {
        this.id = UUID.randomUUID().toString();
    }

    public Transaction(String date_and_time, BigDecimal sum,
                       String currency, String operation_type, String account_from,
                       String account_to, TransactionStatus status) {

        this.setTransaction_id();
        this.date_and_time = date_and_time;
        this.sum = sum;
        this.currency = currency;
        this.operation_type = operation_type;
        this.account_id_from = account_from;
        this.account_id_to = account_to;
        this.status = status;
    }
    public String getId() {
        return id;
    }
    public void setTransaction_id() {
        this.id = UUID.randomUUID().toString();
    }
    public String getDate_and_time() {
        return date_and_time;
    }
    public void setDate_and_time(String date_and_time) {
        if (date_and_time == null || !date_and_time.matches("^\\d{2}\\.\\d{2}\\.\\d{4}, \\d{2}\\.\\d{2}\\.\\d{2}$"))
            throw new IllegalArgumentException("Date and time must be in format: 'dd.mm.yyyy, hh:mm:ss");
        this.date_and_time = date_and_time;
    }
    public BigDecimal getSum() {
        return sum;
    }
    public void setSum(BigDecimal sum) {
        if (sum.compareTo(BigDecimal.ZERO) == -1)
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
    public String getAccount_id_from() {
        return account_id_from;
    }
    public void setAccount_id_from(String account_id_from) {
        //дописати потім перевірку чи існує такий ід
        this.account_id_from = account_id_from;
    }
    public String getAccount_id_to() {
        return account_id_to;
    }
    public void setAccount_id_to(String account_id_to) {
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
