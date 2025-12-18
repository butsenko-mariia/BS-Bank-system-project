package program;

import program.Bank.*;
import program.Bank.Menu.*;


import java.util.Scanner;
import java.util.UUID;

public class App {

    public static void main(String[] args) {
        System.setOut(new java.io.PrintStream(System.out, true, java.nio.charset.StandardCharsets.UTF_8));

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



        Scanner scanner = new Scanner(System.in);

        // 1. Створюємо базові команди навігації
        Command back = new Return();
        Command exit = new Exit();

        // 2. Створюємо меню "Клієнти"
        Menu clientMenu = new Menu("clients", scanner);
        clientMenu.add(new CreateClient(scanner)); // Додаємо нашу команду
        clientMenu.add(new Help("Меню для роботи з клієнтами"));
        clientMenu.add(back);

        // 3. Створюємо ГОЛОВНЕ МЕНЮ
        Menu mainMenu = new Menu("main", scanner);

        // Додаємо вкладене меню (Composite в дії!)
        mainMenu.add(clientMenu);

        // Додаємо команди головного меню
        mainMenu.add(new Help("Головне меню системи"));
        mainMenu.add(exit);

        // 4. Запуск
        mainMenu.execute();

        scanner.close();
       }
}