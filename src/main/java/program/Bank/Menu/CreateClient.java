package program.Bank.Menu;

import program.Bank.Client;
import program.Bank.DateBase;
import program.Bank.Enums.ClientStatus;
import java.util.Scanner;
import java.util.UUID;

public class CreateClient implements Command {
    private final Scanner scanner;

    public CreateClient(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Result execute() {
        System.out.println("--- Створення нового клієнта ---");
        try {
            System.out.print("Введіть ПІБ: ");
            String name = scanner.nextLine();

            System.out.print("Телефон: ");
            String phone = scanner.nextLine();

            // Створюємо об'єкт (використовуючи твої класи)
            Client client = new Client();
            // ВАЖЛИВО: ID встановлюється в конструкторі Client,
            // але якщо треба вручну: client.setId(UUID.randomUUID());

            client.setFull_name(name);
            client.setMobile_phone(phone);

            // Заглушки для обов'язкових полів (щоб БД прийняла запис)
            client.setIndividual_tax_number("0000000000");
            client.setSex("M");
            client.setNationality("UA");
            client.setPassport_number("AB000000");
            client.setLegal_address("Unknown");
            client.setPlace_of_birth("Unknown");
            client.setRecord_number("0");
            client.setPlace_of_work_or_study("Unknown");
            client.setDate_of_birth(java.time.LocalDate.of(2000, 1, 1));
            client.setStatus(ClientStatus.ACTIVE);

            // Запис в базу
            DateBase.Upload(client);
            System.out.println("Клієнта успішно створено!");

        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
        return Result.CONTINUE;
    }

    @Override
    public String name() {
        return "add"; // Команда для виклику
    }
}
