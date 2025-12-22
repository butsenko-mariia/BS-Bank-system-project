package program.Bank.Builders;

import program.Bank.Enums.AccountStatus;
import program.Bank.Deposit;
import program.Bank.StandardDeposit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class StandardDepositBuilder {
    private StandardDeposit deposit;


    public StandardDepositBuilder(){
        deposit = new StandardDeposit();
    }
    public StandardDepositBuilder(UUID id){
        deposit = new StandardDeposit(id);
    }
    public static StandardDepositBuilder create(){
        return new StandardDepositBuilder();
    }
    public StandardDepositBuilder client_id(UUID client_id){
        deposit.setClient_id(client_id);
        return this;
    }
    public StandardDepositBuilder original_sum(BigDecimal original_sum){
        deposit.setOriginal_sum(original_sum);
        return this;
    }
    public StandardDepositBuilder open_date(LocalDate open_date){
        deposit.setOpen_date(open_date);
        return this;
    }
    public StandardDepositBuilder close_date(LocalDate close_date){
        deposit.setClose_date(close_date);
        return this;
    }
    public StandardDepositBuilder interest_rate(BigDecimal interest_rate){
        deposit.setInterest_rate(interest_rate);
        return this;
    }
    public StandardDepositBuilder currency(String currency){
        deposit.setCurrency(currency);
        return this;
    }
    public StandardDepositBuilder status(AccountStatus status){
        deposit.setStatus(status);
        return this;
    }
    public Deposit build(){
        if (deposit == null || deposit.getId() == null || deposit.getClient_id() == null ||
                deposit.getOriginal_sum() == null || deposit.getOpen_date() == null ||
                deposit.getInterest_rate() == null || deposit.getClose_date() == null || deposit.getCurrency() == null){
            throw  new IllegalStateException("Some fields are null.");
        }
        return deposit;
    }
    public void reset(){
        deposit = new StandardDeposit();
    }
}
