package program.Bank.Builders;

import program.Bank.Enums.AccountStatus;
import program.Bank.Deposit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class DepositBuilder {
    private Deposit deposit = new Deposit();
    public DepositBuilder(){
        this.createNew();
    }
    private void createNew(){
        deposit.setId();
        deposit.setOpen_date();
        deposit.setOriginal_sum(BigDecimal.ZERO);
        deposit.setProfit(BigDecimal.ZERO);
        deposit.setCurrency("GRN");
        deposit.setStatus(AccountStatus.OPEN);
    }
    public static DepositBuilder create(){
        return new DepositBuilder();
    }
    public DepositBuilder client_id(UUID client_id){
        deposit.setClient_id(client_id);
        return this;
    }
    public DepositBuilder original_sum(BigDecimal original_sum){
        deposit.setOriginal_sum(original_sum);
        return this;
    }
    public DepositBuilder current_balance(BigDecimal current_balance){
        deposit.setProfit(current_balance);
        return this;
    }
    public DepositBuilder open_date(LocalDate open_date){
        deposit.setOpen_date(open_date);
        return this;
    }
    public DepositBuilder close_date(LocalDate close_date){
        deposit.setClose_date(close_date);
        return this;
    }
    public DepositBuilder interest_rate(double interest_rate){
        deposit.setInterest_rate(interest_rate);
        return this;
    }
    public DepositBuilder currency(String currency){
        deposit.setCurrency(currency);
        return this;
    }
    public DepositBuilder status(AccountStatus status){
        deposit.setStatus(status);
        return this;
    }
    public Deposit build(){
        return deposit;
    }
    public void reset(){
        deposit = new Deposit();
        this.createNew();
    }
}
