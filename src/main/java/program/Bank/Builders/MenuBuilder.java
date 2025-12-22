package program.Bank.Builders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.*;
import program.Bank.Menu.*;
import program.Bank.Services.*;

import java.util.Scanner;

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
            LoginMenu();
        }));

        return mainMenu;
    }

    public void RegisterNewClient() {
        ui.print("=== REGISTER CLIENT ===");

        String full_name = ui.ask("Enter Full name:");
        String date_of_birth = ui.ask("Enter Date of Birth:");
        String sex =  ui.ask("Enter Sex");
        String nationality = ui.ask("Enter Nationality:");
        String mobile_phone = ui.ask("Enter Mobile phone:");
        String individual_tax_number = ui.ask("Enter Individual tax number:");
        String passport_number = ui.ask("Enter Passport number:");
        String legal_address = ui.ask("Enter Legal address:");
        String place_of_birth = ui.ask("Enter Place of Birth:");
        String record_number =ui.ask("Enter Record number:");
        String place_of_work_or_study = ui.ask("Enter Place of Work or Study:");

        Client client = clientService.RegisterClient(full_name, date_of_birth, sex, nationality, mobile_phone, individual_tax_number,
                passport_number, legal_address, place_of_birth, record_number, place_of_work_or_study);

        ui.print("=== Client was created successfully! ===");
        clientService.FullInfo(client);

        this.currentClient = client;
    }

    public void LoginMenu(){
        Menu loginMenu = new Menu("=== LOG IN CLIENT's CABINET ===");

        loginMenu.add(new Command("Return", () -> {}));
        loginMenu.add(new Command(("Enter Client's account by id"), () -> {
            Login("name", "Enter Client`s full name");
        }));
        loginMenu.add(new Command(("Enter Client's account by passport number"), () -> {
            Login("passport", "Enter Client`s passport number");
        }));
        loginMenu.add(new Command(("Enter Client's account by phone number"), () -> {
            Login("phone", "Enter Client`s phone number");
        }));
        loginMenu.add(ExitCommand());
        loginMenu.execute();
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
            // Тут виклик підменю карток
        }));

        clientMenu.add(new Command("Deposit", () -> {
            // Функціонал депозитів
        }));

        clientMenu.add(new Command("Credits", () -> {
            //Функціонал кредитів
        }));

        clientMenu.add(new Command("Transactions' history", () -> {
            //Історія
        }));

        clientMenu.add(new Command("Client's data", () -> {
            clientService.ShortInfoShow(client);
            clientService.FullInfo(client);
        }));

        clientMenu.add(ExitCommand());

        return clientMenu;
    }
}
