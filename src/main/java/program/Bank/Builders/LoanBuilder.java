package program.Bank.Builders;

import program.Bank.DateBase;
import program.Bank.Enums.AccountStatus;
import program.Bank.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class LoanBuilder {
    Loan loan;

    public LoanBuilder(){
        loan = new Loan();
    }
    public LoanBuilder(UUID id){
        loan = new Loan(id);
    }
    public static LoanBuilder create(){
        return new LoanBuilder();
    }
    public LoanBuilder client_id(UUID client_id){
        loan.setClient_id(client_id);
        return this;
    }
    public LoanBuilder original_sum(BigDecimal original_sum){
        loan.setOriginal_sum(original_sum);
        return this;
    }
    public LoanBuilder current_balance(BigDecimal current_balance){
        loan.setCurrent_balance(current_balance);
        return this;
    }
    public LoanBuilder open_date(LocalDate open_date){
        loan.setOpen_date(open_date);
        return this;
    }
    public LoanBuilder close_date(LocalDate close_date){
        loan.setClose_date(close_date);
        return this;
    }
    public LoanBuilder term_month(int term_month){
        loan.setTerm_month(term_month);
        return this;
    }
    public LoanBuilder payment_day(int payment_day){
        loan.setPayment_day(payment_day);
        return this;
    }
    public LoanBuilder monthly_payment(BigDecimal monthly_payment){
        loan.setMonthly_payment(monthly_payment);
        return this;
    }
    public LoanBuilder interest_rate(BigDecimal interest_rate){
        loan.setInterest_rate(interest_rate);
        return this;
    }
    public LoanBuilder currency(String currency){
        loan.setCurrency(currency);
        return this;
    }
    public  LoanBuilder status(AccountStatus status){
        loan.setStatus(status);
        return this;
    }
    public Loan builder(){
        if (loan == null || loan.getId() == null || loan.getClient_id() == null || loan.getOriginal_sum() == null
                || loan.getInterest_rate() == null|| loan.getOpen_date() == null|| loan.getClose_date() == null
                || loan.getCurrency() == null) {
            throw  new IllegalStateException("Some fields are null.");
        }
        return loan;
    }
    public void reset(){
        loan = new Loan();
    }
}
