package program.Bank.Menu;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu implements Command {
    private final String name;
    private final String description; // <--- Додали поле для опису
    private final Scanner scanner;
    private final Map<String, Command> commands = new LinkedHashMap<>();

    // --- ОНОВЛЕНИЙ КОНСТРУКТОР ---
    // Тепер він приймає 3 параметри: name, description, scanner
    public Menu(String name, String description, Scanner scanner) {
        this.name = name;
        this.description = description;
        this.scanner = scanner;
    }

    public void add(Command command) {
        commands.put(command.name(), command);
    }

    @Override
    public Result execute() {
        if (commands.isEmpty()) {
            System.out.println("Меню пусте.");
            return Result.CONTINUE;
        }

        Result result;
        do {
            // Тут використовуємо description для гарного заголовка
            System.out.println("\n=== " + description + " ===");
            System.out.println("Доступні команди: " + commands.keySet());
            System.out.print("> ");

            String commandName = scanner.nextLine().trim();
            Command command = commands.get(commandName);

            if (command != null) {
                result = command.execute();
            } else {
                System.out.println("Команда не знайдена. Спробуйте ще раз.");
                result = Result.CONTINUE;
            }
        } while (result == Result.CONTINUE);

        return result == Result.EXIT ? Result.EXIT : Result.CONTINUE;
    }

    @Override
    public String name() {
        return name;
    }
}