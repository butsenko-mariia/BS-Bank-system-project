package program.Bank.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.DataBase;

public class DepositeService {
    private final Logger log = LogManager.getLogger(DepositeService.class);
    private final DataBase dataBase;

    public DepositeService(DataBase dataBase) {
        this.dataBase = dataBase;
    }
}
