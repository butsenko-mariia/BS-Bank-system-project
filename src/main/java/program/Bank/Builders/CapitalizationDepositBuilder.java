package program.Bank.Builders;

import program.Bank.CapitalizationDeposit;
import program.Bank.Enums.AccountStatus;
import program.Bank.Deposit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class CapitalizationDepositBuilder {
    private CapitalizationDeposit deposit;
    public CapitalizationDepositBuilder(){
        deposit = new CapitalizationDeposit();
    }
    public static CapitalizationDepositBuilder create(){
        return new CapitalizationDepositBuilder();
    }
    public CapitalizationDepositBuilder client_id(UUID client_id){
        deposit.setClient_id(client_id);
        return this;
    }
    public CapitalizationDepositBuilder original_sum(BigDecimal original_sum){
        deposit.setOriginal_sum(original_sum);
        return this;
    }
    public CapitalizationDepositBuilder open_date(LocalDate open_date){
        deposit.setOpen_date(open_date);
        return this;
    }
    public CapitalizationDepositBuilder close_date(LocalDate close_date){
        deposit.setClose_date(close_date);
        return this;
    }
    public CapitalizationDepositBuilder interest_rate(BigDecimal interest_rate){
        deposit.setInterest_rate(interest_rate);
        return this;
    }
    public CapitalizationDepositBuilder currency(String currency){
        deposit.setCurrency(currency);
        return this;
    }
    public CapitalizationDepositBuilder status(AccountStatus status){
        deposit.setStatus(status);
        return this;
    }
    public CapitalizationDepositBuilder fetch(){
        deposit.Fetch();
        return this;
    }
    public Deposit build(){
        return deposit;
    }
    public void reset(){
        deposit = new CapitalizationDeposit();
    }
}
