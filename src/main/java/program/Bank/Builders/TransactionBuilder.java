package program.Bank.Builders;

import program.Bank.Transaction;
import program.Bank.Enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class TransactionBuilder {
    Transaction transaction = new Transaction();

    public TransactionBuilder(){
        this.createNew();
    }
    private void createNew(){
        transaction.setId();
        transaction.setOpen_date();
        transaction.setOpen_time();
        transaction.setSum(BigDecimal.ZERO);
        transaction.setCurrency("GRN");
        transaction.setStatus(TransactionStatus.COMPLETED);
    }
    public static TransactionBuilder create(){
        return new TransactionBuilder();
    }
    public TransactionBuilder open_date(LocalDate open_date){
        transaction.setOpen_date(open_date);
        return this;
    }
    public TransactionBuilder open_time(LocalTime open_time){
        transaction.setOpen_time(open_time);
        return this;
    }
    public  TransactionBuilder sum(BigDecimal sum){
        transaction.setSum(sum);
        return this;
    }
    public TransactionBuilder currency(String currency){
        transaction.setCurrency(currency);
        return this;
    }
    public  TransactionBuilder operation_type(String operation_type){
        transaction.setOperation_type(operation_type);
        return this;
    }
    public TransactionBuilder account_id_from(UUID account_id_from){
        transaction.setAccount_id_from(account_id_from);
        return this;
    }
    public TransactionBuilder account_id_to(UUID account_id_to){
        transaction.setAccount_id_to(account_id_to);
        return this;
    }
    public TransactionBuilder status(TransactionStatus status){
        transaction.setStatus(status);
        return this;
    }
    public Transaction build(){
        return transaction;
    }
    public void reset(){
        transaction = new Transaction();
        this.createNew();
    }
}
