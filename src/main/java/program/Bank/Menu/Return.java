package program.Bank.Menu;


public class Return implements Command {
    @Override
    public Result execute() {
        return Result.RETURN; // Сигнал повернутися назад
    }

    @Override
    public String name() {
        return "back"; // Юзер має написати "back"
    }
}