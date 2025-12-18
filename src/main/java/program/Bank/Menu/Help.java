package program.Bank.Menu;


public class Help implements Command {
    private final String description;

    public Help(String description) {
        this.description = description;
    }

    @Override
    public Result execute() {
        System.out.println("Довідка: " + description);
        return Result.CONTINUE;
    }

    @Override
    public String name() {
        return "help";
    }
}
