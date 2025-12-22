package program.Bank.Menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.Enums.Result;
import java.util.*;

//This is a container of composite pattern
public class Menu extends MenuComponent {
    private final List<MenuComponent> commands = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private final Logger log = LogManager.getLogger(Menu.class);

    public Menu(String name) {
        super(name);
    }

    @Override
    public Result execute() {
        if (commands.isEmpty()) {
            String mes = "Menu is empty. Returning";
            log.warn(mes);
            System.out.println(mes);
            return Result.RETURN;
        }

        Result result = Result.CONTINUE;

        do {
            prompt();

            int commandName;

            try {
                commandName = Integer.parseInt(scanner.nextLine());
            }
            catch (NumberFormatException e) {
                String mes = "Invalid input. Enter a number.";
                log.warn(mes);
                System.out.println(mes);
                continue;
            }

            if (commandName == 0) {
                String mes = "User commanded to return. Returning...";
                log.info(mes);
                return Result.RETURN;
            }
            else if (commandName > 0 && commandName <= commands.size()){
                MenuComponent command = commands.get(commandName - 1);
                Result childResult = command.execute();

                if (childResult == Result.EXIT) {
                    return Result.EXIT;
                }
            }
            else{
                String mes = "Command not found. Try again";
                log.warn(mes);
                System.out.println(mes);
            }

        } while (result == Result.CONTINUE);

        return result;
    }

    private void prompt(){
        System.out.println("\n=== " + name.toUpperCase() + " ===");
        StringBuilder commandNames = new StringBuilder();
        for (int i =  0; i < commands.size(); i++){
            commandNames.append(i+1).append(". ").append(commands.get(i).getName()).append("\n");
        }
        System.out.println("Enter one of the available commands: "+commandNames+"\n");
    }

    public void add(MenuComponent command) {
        this.commands.add(command);
        String mes = command.getName() + " has been added";
        log.debug(mes);
    }

    public void remove(MenuComponent command) {
        this.commands.remove(command);
        String mes = command.getName() + " has been removed";
        log.debug(mes);
    }

    public void remove(int index) {
        this.commands.remove(index);
        String mes = "Command with index["+ index +"] has been removed";
        log.debug(mes);
    }

}