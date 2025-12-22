package program.Bank.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.DataBase;

public class LoanService {
    private final Logger log = LogManager.getLogger(LoanService.class);
    private final DataBase dataBase;

    public LoanService(DataBase dataBase) {
        this.dataBase = dataBase;
    }
}
