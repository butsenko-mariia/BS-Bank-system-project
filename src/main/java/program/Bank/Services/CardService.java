package program.Bank.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.DataBase;

public class CardService {
    private final Logger log = LogManager.getLogger(CardService.class);
    private final DataBase dataBase;

    public CardService(DataBase dataBase) {
        this.dataBase = dataBase;
    }
}
