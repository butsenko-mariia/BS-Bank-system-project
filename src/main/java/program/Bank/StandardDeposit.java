package program.Bank;
import program.Bank.Enums.AccountStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class StandardDeposit extends Deposit{

    public StandardDeposit(){
        this.setId();
        this.setOpen_date();
        this.setCurrency("GRN");
        this.setStatus(AccountStatus.ACTIVE);
    }
    public StandardDeposit(UUID id){
        this.setId(id);
    }

    public void InterestCalculation(LocalDate date){
        long days = ChronoUnit.DAYS.between(this.getOpen_date(), date);
        if (days < 0) days = 0;
        BigDecimal profit = this.getOriginal_sum().multiply(this.getInterest_rate()).multiply(BigDecimal.valueOf(days))
                .divide(BigDecimal.valueOf(365), 2, BigDecimal.ROUND_HALF_UP);

        this.setProfit(profit);
    }
}