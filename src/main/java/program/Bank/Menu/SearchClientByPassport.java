package program.Bank.Menu;


import program.Bank.Client;
import program.Bank.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.UUID;

public class SearchClientByPassport implements Command {
    private final Scanner scanner;

    public SearchClientByPassport(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Result execute() {
        System.out.println("\n--- Пошук за номером паспорта ---");
        System.out.print("Введіть номер паспорта: ");
        String passport = scanner.nextLine().trim();

        // 1. Шукаємо ID в базі
        UUID foundId = findIdByPassport(passport);

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
            String pin = scanner.nextLine();

            Client client = new Client();
            client.setId(foundId);
            DataBase.Fetch(client);

            showClientDashboard(client);
        }

        return Result.CONTINUE;
    }

    @Override
    public String name() {
        return "passport_search";
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
    private UUID findIdByPassport(String passport) {
        String query = "SELECT id FROM client WHERE passport_number = ?";
        try (Connection conn = DataBase.Connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, passport);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return (UUID) rs.getObject("id");
        } catch (Exception e) { System.out.println("Помилка БД: " + e.getMessage()); }
        return null;
    }
}