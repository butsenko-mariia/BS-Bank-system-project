package program.Bank.Menu;


public class Exit implements Command {
    @Override
    public Result execute() {
        System.out.println("Завершення роботи...");
        return Result.EXIT; // Сигнал повного виходу
    }

    @Override
    public String name() {
        return "exit";
    }
}
