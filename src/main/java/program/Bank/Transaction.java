package program.Bank;

import java.util.UUID;

public class Transaction {
    private String transaction_id;
    private String date_and_time;
    private double sum;
    private String currency;
    private String operation_type;
    private String account_from;
    private String account_to;
    private TransactionStatus status;

    public Transaction(String date_and_time, double sum,
                       String currency, String operation_type, String account_from,
                       String account_to, TransactionStatus status) {

        this.transaction_id = UUID.randomUUID().toString();
        this.date_and_time = date_and_time;
        this.sum = sum;
        this.currency = currency;
        this.operation_type = operation_type;
        this.account_from = account_from;
        this.account_to = account_to;
        this.status = status;
    }
}
