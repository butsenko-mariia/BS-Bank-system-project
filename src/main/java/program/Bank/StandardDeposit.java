package program.Bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.Enums.AccountStatus;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class StandardDeposit extends Deposit{
    private static final Logger log = LogManager.getLogger(StandardDeposit.class);

    public StandardDeposit(){
        this.setId();
        this.setOpen_date();
        this.setCurrency("UAH");
        this.setStatus(AccountStatus.ACTIVE);
        this.setProfit(BigDecimal.ZERO);
        log.info("Created new StandardDeposit with generated ID: {}.", this.getId());
    }
    public StandardDeposit(UUID id){
        this.setId(id);
        log.debug("Initialized StandardDeposit with existing ID: {}.", id);
    }

    public void InterestCalculation(LocalDate date){
        log.debug("Starting standard profit calculation for Deposit {}.", this.getId());

        long days = ChronoUnit.DAYS.between(this.getOpen_date(), date);
        if (days < 0)
        {
            log.warn("Date calculation resulted in negative days ({}) for Deposit {}. Resetting to 0.", days, this.getId());
            days = 0;
        }
        log.debug("Calculation parameters: Days = {}, Sum = {}, Rate = {}.",
                days, this.getOriginal_sum(), this.getInterest_rate());
        BigDecimal profit = this.getOriginal_sum().multiply(this.getInterest_rate()).multiply(BigDecimal.valueOf(days))
                .divide(BigDecimal.valueOf(365), 2, RoundingMode.HALF_UP);

        this.setProfit(profit);
        log.debug("Calculated profit for StandardDeposit {}: {}.", this.getId(), profit);
    }
}