package program.Bank.Menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.ConsoleUI;
import program.Bank.Enums.Result;
import java.util.Scanner;

public class Command extends MenuComponent {
    private final Runnable action;
    private final Logger log = LogManager.getLogger(Command.class);
    private final ConsoleUI ui = new ConsoleUI();

    public Command(String name, Runnable action) {
        super(name);
        this.action = action;
    }

    @Override
    public Result execute() {
        ui.print("\n--- " + getName() + " ---");
        try {
            action.run();
        } catch (Exception e) {
            String mes = "Error occurred: " + e.getMessage();
            log.error(mes);
            ui.print(mes);
        }

        return Result.CONTINUE;
    }
}

