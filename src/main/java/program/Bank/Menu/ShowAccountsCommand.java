package program.Bank.Menu;


import program.Bank.Client;

public class ShowAccountsCommand implements Command {
    private final Client client;

    public ShowAccountsCommand(Client client) {
        this.client = client;
    }

    @Override
    public Result execute() {
        System.out.println("\n--- ВАШІ РАХУНКИ ---");
        // Тут поки заглушка, бо методу в Client для списку рахунків ще немає
        System.out.println("1. UAH Основний - " + client.getBalance() + " грн");
        System.out.println("2. USD Ощадний - 0.00 $");
        System.out.println("--------------------");
        return Result.CONTINUE;
    }

    @Override
    public String name() {
        return "accounts"; // Пункт 1
    }
}