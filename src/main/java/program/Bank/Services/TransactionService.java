package program.Bank.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.DataBase;

public class TransactionService {
    private final Logger log = LogManager.getLogger(TransactionService.class);
    private final DataBase dataBase;

    public TransactionService(DataBase dataBase) {
        this.dataBase = dataBase;
    }
}
