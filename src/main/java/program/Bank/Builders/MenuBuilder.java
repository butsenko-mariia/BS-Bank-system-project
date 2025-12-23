package program.Bank.Builders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.*;
import program.Bank.Enums.*;
import program.Bank.Menu.*;
import program.Bank.Services.*;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;

public class MenuBuilder {
    private final ClientService clientService;
    private final CardService cardService;
    private final DepositeService depositeService;
    private final LoanService loanService;
    private final TransactionService transactionService;
    private final ConsoleUI ui;
    private Client currentClient;
    private final Logger log = LogManager.getLogger(MenuBuilder.class);


    public MenuBuilder(){
        this.clientService = new ClientService(DataBase.getInstance());
        this.cardService = new CardService(DataBase.getInstance());
        this.depositeService = new DepositeService(DataBase.getInstance());
        this.loanService = new LoanService(DataBase.getInstance());
        this.transactionService = new TransactionService(DataBase.getInstance());
        this.ui = new ConsoleUI();
    }

    public Command ExitCommand(){
        return new Command("Exit", () -> {
            System.exit(0);
        });
    }

    public Menu MainMenu(){
        Menu mainMenu = new Menu("BANK SYSTEM");
        System.out.println("Enter Client`s account");
        mainMenu.add(ExitCommand());
        mainMenu.add(new Command("Register new Client", () -> {
            RegisterNewClient();
            Menu clientMenu = ClientMenu(this.currentClient);
            clientMenu.execute();
        }));
        mainMenu.add(new Command("Log into Client's cabinet", () -> {
            Menu loginMenu = LoginMenu();
            loginMenu.execute();
        }));

        return mainMenu;
    }

    public void RegisterNewClient() {
        ui.print("=== REGISTER CLIENT ===");

        Client client = null;

        try {
            String full_name = ui.ask("Enter Full name:");
            String date_of_birth = ui.ask("Enter Date of Birth:");
            String sex = ui.ask("Enter Sex");
            String nationality = ui.ask("Enter Nationality:");
            String mobile_phone = ui.ask("Enter Mobile phone:");
            String individual_tax_number = ui.ask("Enter Individual tax number:");
            String passport_number = ui.ask("Enter Passport number:");
            String legal_address = ui.ask("Enter Legal address:");
            String place_of_birth = ui.ask("Enter Place of Birth:");
            String record_number = ui.ask("Enter Record number:");
            String place_of_work_or_study = ui.ask("Enter Place of Work or Study:");

            client = clientService.RegisterClient(full_name, date_of_birth, sex, nationality, mobile_phone, individual_tax_number,
                    passport_number, legal_address, place_of_birth, record_number, place_of_work_or_study);

        }
        catch (Exception e) {
            String mes = "Error occurred: " + e.getMessage();
            ui.print(mes);
            log.error(mes);
        }

        if(client != null) {
            ui.print("=== Client was created successfully! ===");
            clientService.FullInfo(client);
        }

        this.currentClient = client;
    }

    public Menu LoginMenu(){
        Menu loginMenu = new Menu("=== LOG IN CLIENT's CABINET ===");

        loginMenu.add(new Command("Return", () -> {}));
        loginMenu.add(new Command(("Enter Client's account by full name"), () -> {
            Login("name", "Enter Client`s full name");
        }));
        loginMenu.add(new Command(("Enter Client's account by passport number"), () -> {
            Login("passport", "Enter Client`s passport number");
        }));
        loginMenu.add(new Command(("Enter Client's account by phone number"), () -> {
            Login("phone", "Enter Client`s phone number");
        }));
        loginMenu.add(ExitCommand());

        return loginMenu;
    }

    public void Login(String type, String promptMsg) {
        String value = ui.ask(promptMsg);

        Client client = clientService.SearchClient(type, value);

        if (client == null) {
            String mes = "Ваш аккаунт не знайдено або його не існує. Спробуйте ще раз або створіть новий акаунт";
            log.error(mes);
            ui.print(mes);
        }
        else {
            this.currentClient = client;
            ui.print("Welcome, " + client.getFull_name() + "!");

            Menu clientMenu = ClientMenu(client);
            clientMenu.execute();
        }
    }

    public Menu ClientMenu(Client client){
        Menu clientMenu = new Menu("=== CLIENT PERSONAL CABINET ===");

        clientMenu.add(new Command("Information about client's accounts", () -> {
            // Тут виклик AccountService.printInfo(client)
        }));

        clientMenu.add(new Command("Cards", () -> {
            Menu cardMenu = CardMenu();
            cardMenu.execute();
        }));

        clientMenu.add(new Command("Deposit", () -> {
            // Функціонал депозитів
        }));

        clientMenu.add(new Command("Credits", () -> {
            //Функціонал кредитів
        }));

        clientMenu.add(new Command("Transactions' history", () -> {
            ui.print("\n=== TRANSACTION HISTORY ===");

            // 1. Отримуємо картки клієнта (використовуючи метод, який ми щойно додали)
            java.util.List<Card> cards = cardService.getClientCards(client.getId());

            if (cards.isEmpty()) {
                ui.print("У вас немає активних карток.");
                return;
            }

            // 2. Проходимо по кожній картці
            for (Card card : cards) {
                ui.print("\n-----------------------------------------------------");
                ui.print(" CARD: " + card.getCard_number() + " (" + card.getCurrency() + ")");
                ui.print("-----------------------------------------------------");

                // 3. Запитуємо історію у TransactionService
                java.util.List<Transaction> history = transactionService.getTransactionHistory(card.getId());

                if (history.isEmpty()) {
                    ui.print("  No transactions found.");
                } else {
                    // 4. Малюємо табличку
                    System.out.printf("%-12s | %-22s | %-15s%n", "DATE", "INFO", "AMOUNT");
                    System.out.println("-------------|------------------------|----------------");

                    for (Transaction t : history) {
                        String date = t.getOpen_date().toString();
                        String info = t.getOperation_info();
                        // Обрізаємо довгий текст, щоб таблиця була рівною
                        if (info.length() > 20) info = info.substring(0, 20) + "..";

                        String amount = t.getSign() + t.getSum() + " " + t.getCurrency();

                        System.out.printf("%-12s | %-22s | %15s%n", date, info, amount);
                    }
                }
            }
            ui.print("-----------------------------------------------------");
        }));

        clientMenu.add(new Command("Client's data", () -> {
            clientService.ShortInfoShow(client);
            clientService.FullInfo(client);
        }));

        clientMenu.add(ExitCommand());

        return clientMenu;
    }

    public Menu CardMenu(){
        Menu cardMenu = new Menu("=== CARDS CABINET ===");
        ui.print("Your current cards:");
        cardService.PrintAllClientsCards(currentClient);

        cardMenu.add(new Command("Return", () -> {}));
        cardMenu.add(new Command("Open new card", () -> {
            Card card = OpenNewCard();
            Menu certainCardMenu = CertainCardMenu(card);
            certainCardMenu.execute();
        }));
        cardMenu.add(new Command("Work with an existing card", () -> {
            String cardNumber = ui.ask("Enter card number");
            Card card = cardService.GetCardByNumber(cardNumber);
            Menu certainCardMenu = CertainCardMenu(card);
            certainCardMenu.execute();
        }));

        cardMenu.add(ExitCommand());

        return  cardMenu;
    }

    public Card OpenNewCard(){
        ui.print("=== OPEN NEW CARD ===");

        Card card = null;

        try {

            UUID client_id = this.currentClient.getId();
            String card_number = ui.ask("Enter Card Number");
            CardType card_type = CardType.valueOf(ui.ask("Enter Card Type"));
            String currency = ui.ask("Enter Currency");

            card = cardService.CreateCard(client_id, card_number, card_type, currency);
        }
        catch (Exception e){
            String mes = "Error occurred: "+ e.getMessage();
            ui.print(mes);
            log.error(mes);
        }

        if (card != null) {
            ui.print("=== Card was created successfully! ===");
            cardService.PrintFullDetails(card);
        }

        return card;
    }

    public Menu CertainCardMenu(Card card){
        Menu certainCardMenu = new Menu("=== WORK WITH THE CARD ===");
        cardService.PrintShortInfo(card);
        certainCardMenu.add(new Command("Return", () -> {}));
        certainCardMenu.add(new Command("Top up card", () -> {
            Menu topUpMenu = new Menu("=== TOP UP CARD ===");
            topUpMenu.add(new Command("Return", () -> {}));
            topUpMenu.add(new Command("Top up current card", () -> {
                BigDecimal amount = new BigDecimal(ui.ask("Which sum would you like to top up?"));
                cardService.TopUpCard(card, amount);
                ui.print("Put money into the Bankomat\nTop up Successful!");
            }));
            topUpMenu.add(new Command("Top up card with the following card number", () -> {
                String cardNumber = ui.ask("Enter card number");
                BigDecimal amount = new BigDecimal(ui.ask("Which sum would you like to top up?"));
                cardService.TopUpCard(cardNumber, amount);
                ui.print("Put money into the Bankomat\nTop up Successful!");
            }));
            topUpMenu.add(ExitCommand());
            topUpMenu.execute();
        }));
        certainCardMenu.add(new Command("Withdraw cash", () -> {
            BigDecimal amount = new BigDecimal(ui.ask("Which sum would you like to withdraw?"));
            cardService.WithDraw(card, amount);
            ui.print("Withdraw Successful!\nRecieve your cash from the Bankomat");
        }));
        certainCardMenu.add(new Command("Make a transaction", () -> {
            ui.print("=== MONEY TRANSFER ===");
            String receiverNumber = ui.ask("Enter receiver card number:");
            BigDecimal amount = new BigDecimal(ui.ask("Enter amount to transfer:"));
            boolean success = cardService.Transfer(card, receiverNumber, amount);
            if (success) {
                // 2. ЯКЩО успішно - записуємо транзакцію через твій сервіс
                transactionService.createTransaction(
                        card.getId(),       // Твоя картка (ID)
                        receiverNumber,     // Номер картки отримувача (String)
                        amount,             // Сума
                        "Transfer to " + receiverNumber // Опис операції
                );
                ui.print("Transaction recorded.");

            } else {
                ui.print("Transfer failed. Please check balance or card number.");
            }
        }));
        certainCardMenu.add(new Command("View card's details", () -> {
            cardService.PrintFullDetails(card);
        }));
        certainCardMenu.add(new Command("Block card", () -> {
            cardService.BlockCard(card);
        }));
        certainCardMenu.add(new Command("Close card", () -> {
            cardService.CloseCard(card);
        }));

        return certainCardMenu;
    }
}
