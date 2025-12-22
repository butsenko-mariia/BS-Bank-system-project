package program.Bank.Menu;


import program.Bank.Client;
import program.Bank.Enums.Result;

import java.util.Scanner;

public class ClientMenuRunner {

    public static void run(Client client, Scanner scanner) {

        //Menu userMenu = new Menu("user_menu", "ОСОБИСТИЙ КАБІНЕТ", scanner);
        //
        //        // 1. Інформація про рахунки (поки заглушка з минулого разу)
        //        userMenu.add(new ShowAccountsCommand(client));
        //
        //        // 2. Картки
        //        Menu cardsMenu = new Menu("cards", "УПРАВЛІННЯ КАРТКАМИ", scanner);
        //        cardsMenu.add(new Return());
        //        userMenu.add(cardsMenu);
        //
        //        // 3. Депозити
        //        Menu depositMenu = new Menu("deposits", "УПРАВЛІННЯ ДЕПОЗИТАМИ", scanner);
        //        depositMenu.add(new Return());
        //        userMenu.add(depositMenu);
        //
        //        // 4. Кредити
        //        Menu loanMenu = new Menu("loans", "УПРАВЛІННЯ КРЕДИТАМИ", scanner);
        //        loanMenu.add(new Return());
        //        userMenu.add(loanMenu);
        //
        //        // 5. Історія операцій (НОВЕ!)
        //        userMenu.add(new ShowHistoryCommand(client));
        //
        //        // 6. Дані клієнта + Редагування (НОВЕ!)
        //        userMenu.add(new ClientInfoMenu(client, scanner));
        //
        //        // 7. Вихід з аккаунту (Правий блок на схемі)
        //        userMenu.add(new Command() {
        //            @Override
        //            public Result execute() {
        //                System.out.println("Вихід з аккаунту... До побачення!");
        //                return Result.RETURN; // Повертає нас у меню входу
        //            }
        //            @Override
        //            public String name() { return "logout"; }
        //        });
        //
        //        // 0. Назад
        //        userMenu.add(new Return());
        //
        //        userMenu.execute();
    }
}