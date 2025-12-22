package program.Bank.Menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.Enums.Result;
import java.util.Scanner;

//This is a leaf of composite pattern
public class Command extends MenuComponent {
    private final Runnable action;
    private final Logger log = LogManager.getLogger(Command.class);

    public Command(String name, Runnable action) {
        super(name);
        this.action = action;
    }

    @Override
    public Result execute() {
        System.out.println("\n--- " + getName() + " ---");
        try {
            action.run();
        } catch (Exception e) {
            String mes = "Error occurred: " + e.getMessage();
            log.error(mes);
            System.out.println(mes);
        }

        System.out.println("\nНатисніть будь-яку клавішу, щоб продовжити...");
        new Scanner(System.in).nextLine();

        return Result.CONTINUE;
    }
}

