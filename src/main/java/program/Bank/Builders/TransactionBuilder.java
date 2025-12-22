package program.Bank.Builders;

import program.Bank.Transaction;
import program.Bank.Enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class TransactionBuilder {
    Transaction transaction;

    public TransactionBuilder(){
        transaction = new Transaction();
    }
    public TransactionBuilder(UUID id){
        transaction = new Transaction(id);
    }
    public static TransactionBuilder create(){
        return new TransactionBuilder();
    }
    public TransactionBuilder id(UUID id){
        transaction.setId(id);
        return this;
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
    public TransactionBuilder operation_info(String operation_info){
        transaction.setOperation_info(operation_info);
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
        if (transaction == null || transaction.getId() == null ||transaction.getAccount_id_from() == null ||
                transaction.getAccount_id_to() == null || transaction.getSum() == null ||
                transaction.getOpen_date() == null || transaction.getOpen_time() == null || transaction.getCurrency() == null){
            throw  new IllegalStateException("Some fields are null.");
        }
        return transaction;
    }
    public void reset(){
        transaction = new Transaction();
    }
}
