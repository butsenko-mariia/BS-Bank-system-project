package program.Bank.Menu;

public interface Command {
    Result execute();
    String name();
}