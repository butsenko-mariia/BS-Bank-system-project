package program.Bank.Menu;

public interface Command {
    Result execute(); // Метод, що виконує дію
    String name();    // Ім'я команди (те, що пише юзер)
}