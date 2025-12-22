package program.Bank.Menu;


import program.Bank.Client;
import program.Bank.Enums.Result;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class EditClientCommand {
    //private final Client client;
    //    private final Scanner scanner;
    //
    //    public EditClientCommand(Client client, Scanner scanner) {
    //        this.client = client;
    //        this.scanner = scanner;
    //    }
    //
    //    @Override
    //    public Result execute() {
    //        System.out.println("\n--- РЕДАГУВАННЯ ДАНИХ ---");
    //        System.out.println("(Натисніть Enter, щоб залишити старе значення без змін)");
    //
    //        try {
    //            // 1. ПІБ
    //            System.out.println("Поточне ім'я: " + client.getFull_name());
    //            System.out.print("Нове ім'я: ");
    //            String name = scanner.nextLine().trim();
    //            if (!name.isEmpty()) client.setFull_name(name);
    //
    //            // 2. Дата народження (Складний момент, треба парсити)
    //            System.out.println("Дата народження: " + client.getDate_of_birth());
    //            System.out.print("Нова дата (РРРР-ММ-ДД): ");
    //            String dob = scanner.nextLine().trim();
    //            if (!dob.isEmpty()) {
    //                try {
    //                    client.setDate_of_birth(LocalDate.parse(dob));
    //                } catch (DateTimeParseException e) {
    //                    System.out.println("!!! Невірний формат дати. Зміну пропущено.");
    //                }
    //            }
    //
    //            // 3. Стать
    //            System.out.println("Стать: " + client.getSex());
    //            System.out.print("Нова стать (M/W): ");
    //            String sex = scanner.nextLine().trim();
    //            if (!sex.isEmpty()) client.setSex(sex);
    //
    //            // 4. Громадянство
    //            System.out.println("Громадянство: " + client.getNationality());
    //            System.out.print("Нове громадянство: ");
    //            String nation = scanner.nextLine().trim();
    //            if (!nation.isEmpty()) client.setNationality(nation);
    //
    //            // 5. Телефон
    //            System.out.println("Телефон: " + client.getMobile_phone());
    //            System.out.print("Новий телефон: ");
    //            String phone = scanner.nextLine().trim();
    //            if (!phone.isEmpty()) client.setMobile_phone(phone);
    //
    //            // 6. ІПН
    //            System.out.println("ІПН: " + client.getIndividual_tax_number());
    //            System.out.print("Новий ІПН: ");
    //            String ipn = scanner.nextLine().trim();
    //            if (!ipn.isEmpty()) client.setIndividual_tax_number(ipn);
    //
    //            // 7. Паспорт
    //            System.out.println("Паспорт: " + client.getPassport_number());
    //            System.out.print("Новий паспорт: ");
    //            String passport = scanner.nextLine().trim();
    //            if (!passport.isEmpty()) client.setPassport_number(passport);
    //
    //            // 8. Адреса
    //            System.out.println("Адреса: " + client.getLegal_address());
    //            System.out.print("Нова адреса: ");
    //            String address = scanner.nextLine().trim();
    //            if (!address.isEmpty()) client.setLegal_address(address);
    //
    //            // 9. Місце народження
    //            System.out.println("Місце народження: " + client.getPlace_of_birth());
    //            System.out.print("Нове місце народження: ");
    //            String birthPlace = scanner.nextLine().trim();
    //            if (!birthPlace.isEmpty()) client.setPlace_of_birth(birthPlace);
    //
    //            // 10. Місце роботи
    //            System.out.println("Місце роботи/навчання: " + client.getPlace_of_work_or_study());
    //            System.out.print("Нове місце роботи: ");
    //            String work = scanner.nextLine().trim();
    //            if (!work.isEmpty()) client.setPlace_of_work_or_study(work);
    //
    //            // 11. Номер запису
    //            System.out.println("Номер запису: " + client.getRecord_number());
    //            System.out.print("Новий номер запису: ");
    //            String record = scanner.nextLine().trim();
    //            if (!record.isEmpty()) client.setRecord_number(record);
    //
    //            // --- ЗБЕРЕЖЕННЯ В БД ---
    //            // Оскільки ми змінили об'єкт у пам'яті, треба оновити його і в базі.
    //            // Для цього потрібен метод Update у DateBase (я додав його нижче).
    //
    //            //DateBase.Update(client); // Розкоментуй це, коли додаси метод у DateBase!
    //
    //            System.out.println("\nДані успішно оновлено!");
    //
    //        } catch (IllegalArgumentException e) {
    //            System.out.println("!!! Помилка валідації: " + e.getMessage());
    //        } catch (Exception e) {
    //            System.out.println("!!! Помилка: " + e.getMessage());
    //        }
    //
    //        System.out.println("Натисніть Enter...");
    //        scanner.nextLine();
    //
    //        return Result.CONTINUE;
    //    }
    //
    //    @Override
    //    public String name() {
    //        return "edit";
    //    }
}