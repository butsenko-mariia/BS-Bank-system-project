package program.Bank.Menu;


import program.Bank.Client;
import program.Bank.DateBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.UUID;

public class SearchClientByName implements Command {
    private final Scanner scanner;

    public SearchClientByName(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Result execute() {
        System.out.println("\n--- Пошук за ПІБ ---");
        System.out.print("Введіть повне ім'я: ");
        String fullName = scanner.nextLine().trim();

        // 1. Шукаємо ID в базі
        UUID foundId = findIdByName(fullName);

        if (foundId == null) {
            // ВАРІАНТ 1: НЕ ЗНАЙДЕНО
            System.out.println("\n-----------------------------");
            System.out.println("Ваш аккаунт не знайдено або його не існує.");
            System.out.println("Поверніться на минулу сторінку, щоб створити аккаунт.");
            System.out.println("-----------------------------");
            System.out.println("Натисніть Enter...");
            scanner.nextLine();
        } else {
            // ВАРІАНТ 2: УСПІХ
            System.out.print("Аккаунт знайдено. Введіть Пін-код: ");
            String pin = scanner.nextLine(); // Імітація перевірки

            // Завантажуємо дані клієнта
            Client client = new Client();
            client.setId(foundId);
            DateBase.Fetch(client);

            // Показуємо красиве меню
            showClientDashboard(client);
        }

        return Result.CONTINUE;
    }

    @Override
    public String name() {
        return "name_search";
    }

    // --- Меню клієнта (Dashboard) ---
    private void showClientDashboard(Client client) {
        // 1. Показуємо красиву "шапку", як на картинці
        System.out.println("\n      ВХІД УСПІШНИЙ");
        System.out.println("---------------------------");
        System.out.println("Вітаємо, " + client.getFull_name() + "!");
        System.out.println("Статус: " + client.getStatus());
        System.out.println("Баланс: " + client.getBalance() + " грн");
        System.out.println("---------------------------");

        // 2. ПЕРЕДАЄМО УПРАВЛІННЯ В МЕНЮ КЛІЄНТА
        ClientMenuRunner.run(client, scanner);
    }

    // --- SQL запит ---
    private UUID findIdByName(String name) {
        String query = "SELECT id FROM client WHERE full_name = ?";
        try (Connection conn = DateBase.Connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return (UUID) rs.getObject("id");
        } catch (Exception e) { System.out.println("Помилка БД: " + e.getMessage()); }
        return null;
    }
}