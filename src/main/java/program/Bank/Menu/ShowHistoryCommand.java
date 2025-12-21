package program.Bank.Menu;

import program.Bank.Client;
import program.Bank.DateBase;
import program.Bank.Transaction;
import java.util.List;

public class ShowHistoryCommand implements Command {
    private final Client client;

    public ShowHistoryCommand(Client client) {
        this.client = client;
    }

    @Override
    public Result execute() {
        System.out.println("\n--- ІСТОРІЯ ТРАНЗАКЦІЙ ---");

        // 1. Отримуємо реальний список з бази
        List<Transaction> history = DateBase.FetchAllTransactions(client.getId());

        if (history.isEmpty()) {
            System.out.println("(Історія транзакцій порожня)");
        } else {
            // 2. Виводимо кожну транзакцію
            for (Transaction t : history) {
                System.out.println("--------------------------");
                System.out.println(t.getOpen_date() + " " + t.getOpen_time());
                System.out.println(t.getOperation_info());

                // Форматуємо суму (наприклад: + 100.00 UAH)
                String sign = t.getSign().equals("+") ? "+" : "-";
                System.out.println(sign + " " + t.getSum() + " " + t.getCurrency());

                System.out.println("Status: " + t.getStatus());
                System.out.println("ID: #" + t.getId().toString().substring(0, 8) + "...");
            }
            System.out.println("--------------------------");
        }

        System.out.println("\nНатисніть Enter, щоб повернутися...");
        try { System.in.read(); } catch (Exception e) {}

        return Result.CONTINUE;
    }

    @Override
    public String name() {
        return "history";
    }
}