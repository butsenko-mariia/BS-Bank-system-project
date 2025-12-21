package program;

import program.Bank.*;
import program.Bank.Menu.*;


import java.util.Scanner;
import java.util.UUID;

public class App {

    public static void main(String[] args) {

        System.setOut(new java.io.PrintStream(System.out, true, java.nio.charset.StandardCharsets.UTF_8));
/*
        System.out.println("--- START CLIENT ---");
        String testClientIdString = "11111111-1111-1111-1111-111111111111";
        UUID id = UUID.fromString(testClientIdString);
        Client client = new Client(id);
        DateBase.Fetch(client);
        client.PrintClientFullInfo();
        System.out.println("\n--- END CLIENT ---");

        System.out.println("\n--- START DEPOSIT ---");
        UUID depositId = UUID.fromString("d0000001-1111-1111-1111-111111111111");
        Deposit deposit = new StandardDeposit(depositId);
        DateBase.Fetch(deposit);
        deposit.PrintFullInfo();
        System.out.println("\n--- END DEPOSIT ---");

        System.out.println("\n--- START LOAN ---");
        UUID loanId = UUID.fromString("10000007-7777-7777-7777-777777777777");
        Loan loan = new Loan(loanId);
        DateBase.Fetch(loan);
        loan.PrintFullInfo();
        System.out.println("\n--- END LOAN ---");

        System.out.println("\n--- START CARD ---");
        UUID cardId = UUID.fromString("c0000004-4444-4444-4444-444444444444");
        Card card = new Card(cardId);
        DateBase.Fetch(card);
        card.PrintFullInfo();
        System.out.println("\n--- END CARD ---");

*/


        // 1. Створюємо інструменти
        Scanner scanner = new Scanner(System.in);
        Command exit = new Exit();
        Command back = new Return();

        // 2. Створюємо меню "ВХІД В АККАУНТ" (Другий рівень)
        // Сюди додаємо наші команди пошуку, які ми створили раніше
        Menu loginMenu = new Menu("login", "ВХІД В АККАУНТ", scanner);
        loginMenu.add(new SearchClientByName(scanner));     // Пошук за ПІБ
        loginMenu.add(new SearchClientByPhone(scanner));    // Пошук за телефоном
        loginMenu.add(new SearchClientByPassport(scanner)); // Пошук за паспортом
        loginMenu.add(back);                                // Кнопка "Назад"

        // 3. Створюємо ГОЛОВНЕ МЕНЮ (Перший рівень)
        Menu mainMenu = new Menu("main", "БАНКІВСЬКА СИСТЕМА", scanner);

        // Додаємо пункти згідно з твоєю схемою:
        mainMenu.add(new CreateClientCommand(scanner)); // 1. Створити аккаунт
        mainMenu.add(loginMenu);                        // 2. Увійти в аккаунт (наше підменю)
        mainMenu.add(exit);                             // 3. Вийти

        // 4. Запускаємо
        mainMenu.execute();

        scanner.close();
       }
}