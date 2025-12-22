package program.Bank.Menu;


import program.Bank.Client;
import program.Bank.DateBase;
import program.Bank.Enums.ClientStatus;
import program.Bank.Enums.Result;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class CreateClientCommand implements Command {
    private final Scanner scanner;

    public CreateClientCommand(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Result execute() {
        System.out.println("\n--- СТВОРЕННЯ НОВОГО АККАУНТУ ---");
        System.out.println("(Введіть '0' на етапі введення ПІБ, щоб повернутися назад)");

        try {
            // 1. Збір даних
            System.out.print("ПІБ: ");
            String fullName = scanner.nextLine().trim();
            if (fullName.equals("0")) return Result.CONTINUE; // Реалізація кнопки "0. Назад"

            System.out.print("Дата народження (РРРР-ММ-ДД, наприклад 2000-01-30): ");
            String dobStr = scanner.nextLine().trim();
            LocalDate dob = parseDate(dobStr);

            System.out.print("Стать (M/W): ");
            String sex = scanner.nextLine().trim().toUpperCase();

            System.out.print("Громадянство: ");
            String nationality = scanner.nextLine().trim();

            System.out.print("Мобільний телефон (+380...): ");
            String phone = scanner.nextLine().trim();

            System.out.print("ІПН: ");
            String taxNumber = scanner.nextLine().trim();

            System.out.print("Номер паспорту: ");
            String passport = scanner.nextLine().trim();

            System.out.print("Юридична адреса: ");
            String address = scanner.nextLine().trim();

            System.out.print("Місце народження: ");
            String birthPlace = scanner.nextLine().trim();

            System.out.print("Номер запису: ");
            String recordNum = scanner.nextLine().trim();

            System.out.print("Місце роботи/навчання: ");
            String workPlace = scanner.nextLine().trim();

            System.out.print("Створити Пін-код (4 цифри): ");
            String pin = scanner.nextLine().trim();
            // Важливо: У твоєму класі Client поки немає поля pinCode,
            // тому ми його запитуємо, але поки що нікуди не зберігаємо.

            // 2. Створення об'єкта
            Client client = new Client();
            // ID генерується автоматично в конструкторі Client

            // Заповнення полів
            client.setFull_name(fullName);
            client.setDate_of_birth(dob);
            client.setSex(sex);
            client.setNationality(nationality);
            client.setMobile_phone(phone);
            client.setIndividual_tax_number(taxNumber);
            client.setPassport_number(passport);
            client.setLegal_address(address);
            client.setPlace_of_birth(birthPlace);
            client.setRecord_number(recordNum);
            client.setPlace_of_work_or_study(workPlace);
            client.setStatus(ClientStatus.ACTIVE);

            // 3. Запис у Базу Даних
            DateBase.Upload(client);

            // 4. Вивід звіту (як на схемі)
            printSuccessReport(client, pin);

        } catch (IllegalArgumentException e) {
            System.out.println("!!! Помилка вводу даних: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("!!! Системна помилка: " + e.getMessage());
            e.printStackTrace();
        }

        return Result.CONTINUE;
    }

    @Override
    public String name() {
        return "create"; // Команда для виклику в меню
    }

    // --- Допоміжні методи ---

    private LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            System.out.println("Невірний формат дати. Встановлено дату за замовчуванням (2000-01-01).");
            return LocalDate.of(2000, 1, 1);
        }
    }

    private void printSuccessReport(Client client, String pin) {
        System.out.println("\n      Аккаунт успішно створено!");
        System.out.println("----------------------------------");
        System.out.println("ID: #" + client.getId());
        System.out.println("ПІБ: " + client.getFull_name());
        System.out.println("Дата народження: " + client.getDate_of_birth());
        System.out.println("Стать: " + (client.getSex().equals("M") ? "чоловік" : "жінка"));
        System.out.println("Громадянство: " + client.getNationality());
        System.out.println("Мобільний телефон: " + client.getMobile_phone());
        System.out.println("ІПН: " + client.getIndividual_tax_number());
        System.out.println("Номер паспорту: " + client.getPassport_number());
        System.out.println("Юридична адреса: " + client.getLegal_address());
        System.out.println("Місце народження: " + client.getPlace_of_birth());
        System.out.println("Номер запису: " + client.getRecord_number());
        System.out.println("Місце роботи/навчання: " + client.getPlace_of_work_or_study());
        System.out.println("Пін-код: " + pin);
        System.out.println("Статус: " + client.getStatus());
        System.out.println("----------------------------------");
        System.out.println("Натисніть Enter для продовження...");

        try { System.in.read(); } catch (Exception e) {}
    }
}