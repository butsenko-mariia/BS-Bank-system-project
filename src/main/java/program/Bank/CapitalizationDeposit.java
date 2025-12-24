package program.Bank;

import program.Bank.Enums.AccountStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class CapitalizationDeposit extends Deposit{
    private static final Logger log = LogManager.getLogger(CapitalizationDeposit.class);

    public CapitalizationDeposit(){
        this.setId();
        this.setOpen_date();
        this.setCurrency("UAH");
        this.setStatus(AccountStatus.ACTIVE);
        this.setProfit(BigDecimal.ZERO);
        log.info("Created new CapitalizationDeposit with generated ID: {}.", this.getId());
    }
    public CapitalizationDeposit(UUID id){
        this.setId(id);
        log.debug("Initialized CapitalizationDeposit with existing ID: {}.", id);
    }
    public void InterestCalculation(LocalDate date){
        log.debug("Starting capitalization interest calculation for Deposit {}.", this.getId());

        long months = ChronoUnit.MONTHS.between(this.getOpen_date(), date);
        if (months <= 0) {
            log.debug("Passed months count is {} (<= 0). Profit set to ZERO for Deposit {}.", months, this.getId());
            this.setProfit(BigDecimal.ZERO);
            return;
        }

        BigDecimal current_balance = this.getOriginal_sum();
        BigDecimal monthlyRate = this.getInterest_rate().divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
        log.debug("Calculation parameters: Months = {}, Annual Rate = {}, Monthly Rate = {}.",
                months, this.getInterest_rate(), monthlyRate);

        for (int i = 0; i < months; i++) {
            BigDecimal monthProfit = current_balance.multiply(monthlyRate);
            current_balance = current_balance.add(monthProfit);
        }

        this.setProfit(current_balance.subtract(this.getOriginal_sum()).setScale(2, RoundingMode.HALF_UP));
        log.debug("Calculated capitalization profit for Deposit {}: {}.", this.getId(), this.getProfit());
    }
}