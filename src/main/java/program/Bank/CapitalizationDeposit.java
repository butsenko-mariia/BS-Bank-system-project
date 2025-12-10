package program.Bank;
import program.Bank.Enums.AccountStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
public class CapitalizationDeposit extends Deposit{
    public CapitalizationDeposit(){
        this.setId();
        this.setOpen_date();
        this.setCurrency("GRN");
        this.setStatus(AccountStatus.ACTIVE);
    }
    public CapitalizationDeposit(UUID id){
        this.setId(id);
    }
    public void InterestCalculation(LocalDate date){
        long months = ChronoUnit.MONTHS.between(this.getOpen_date(), date);
        if (months <= 0) {
            this.setProfit(BigDecimal.ZERO);
            return;
        }

        BigDecimal current_balance = this.getOriginal_sum();
        BigDecimal monthlyRate = this.getInterest_rate().divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        for (int i = 0; i < months; i++) {
            BigDecimal monthProfit = current_balance.multiply(monthlyRate);
            current_balance = current_balance.add(monthProfit);
        }

        this.setProfit(current_balance.subtract(this.getOriginal_sum()).setScale(2, RoundingMode.HALF_UP));

// for (int i = 0; i < months; i++){
// profit = current_balance.multiply(this.getInterest_rate()).multiply(BigDecimal.valueOf((30.5/365)));
// current_balance = current_balance.add(profit);
// }
// this.setProfit(current_balance.subtract(this.getOriginal_sum()));
    }
}