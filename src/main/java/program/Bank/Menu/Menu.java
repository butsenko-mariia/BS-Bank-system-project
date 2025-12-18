package program.Bank.Menu;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu implements Command {
    private final String name;
    private final Scanner scanner;
    // Використовуємо LinkedHashMap, щоб зберігати порядок команд при виводі
    private final Map<String, Command> commands = new LinkedHashMap<>();

    // КОНСТРУКТОР
    public Menu(String name, Scanner scanner) {
        this.name = name;
        this.scanner = scanner;
    }

    // Метод додавання команд (або підменю) в це меню [cite: 76]
    public void add(Command command) {
        commands.put(command.name(), command);
    }

    @Override
    public Result execute() {
        if (commands.isEmpty()) {
            System.out.println("Menu is empty. Returning");
            return Result.CONTINUE;
        }

        Result result;
        do {
            prompt(); // Вивід списку команд
            String commandName = scanner.nextLine().trim();

            Command command = commands.get(commandName);

            if (command != null) {
                result = command.execute(); // Виконання команди
            } else {
                System.out.println("Command not found. Try again");
                result = Result.CONTINUE;
            }

            // Працюємо, поки команда повертає CONTINUE [cite: 69]
        } while (result == Result.CONTINUE);

        // Якщо прийшов EXIT - передаємо далі, якщо RETURN - зупиняємо це меню
        return result == Result.EXIT ? Result.EXIT : Result.CONTINUE;
    }

    @Override
    public String name() {
        return name;
    }

    private void prompt() {
        System.out.println("\n=== " + name.toUpperCase() + " ===");
        System.out.println("Available commands: " + commands.keySet());
        System.out.print("> ");
    }
}