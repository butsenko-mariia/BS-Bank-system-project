package program.Bank.Builders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.*;
import program.Bank.Enums.*;
import program.Bank.Menu.*;
import program.Bank.Services.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
            String full_name = ui.ask("Enter Full name");
            String date_of_birth = ui.ask("Enter Date of Birth");
            String sex = ui.ask("Enter Sex");
            String nationality = ui.ask("Enter Nationality");
            String mobile_phone = ui.ask("Enter Mobile phone");
            String individual_tax_number = ui.ask("Enter Individual tax number");
            String passport_number = ui.ask("Enter Passport number");
            String legal_address = ui.ask("Enter Legal address");
            String place_of_birth = ui.ask("Enter Place of Birth");
            String record_number = ui.ask("Enter Record number");
            String place_of_work_or_study = ui.ask("Enter Place of Work or Study");

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
//MAIN MENU
    public Menu ClientMenu(Client client){
        Menu clientMenu = new Menu("=== CLIENT PERSONAL CABINET ===");

        clientMenu.add(new Command("Client's full information", () -> {
            ui.print("=== CLIENT INFO ===");
            clientService.FullInfo(client);
        }));

        clientMenu.add(new Command("Return", () -> {}));

        clientMenu.add(new Command("Cards", () -> {
            Menu cardMenu = CardMenu(client);
            cardMenu.execute();
        }));

        clientMenu.add(new Command("Deposit", () -> {
            Menu depositMenu = DepositMenu(client);
            depositMenu.execute();
        }));

        clientMenu.add(new Command("Credits", () -> {
            Menu loanMenu = LoanMenu(client);
            loanMenu.execute();
        }));

        clientMenu.add(new Command("Transactions' history", () -> {
            ui.print("\n=== TRANSACTION HISTORY ===");
            List<Account> allAccounts = new java.util.ArrayList<>();
            allAccounts.addAll(cardService.getClientCards(client.getId()));
            allAccounts.addAll(depositeService.getClientDeposits(client.getId()));
            allAccounts.addAll(loanService.getClientLoans(client.getId()));

            if (allAccounts.isEmpty()) {
                ui.print("У вас немає активних рахунків (карток, депозитів чи кредитів).");
                return;
            }

            for (Account account : allAccounts) {
                String type = "ACCOUNT";
                String info = account.toString();

                if (account instanceof Card) {
                    type = "CARD";
                } else if (account instanceof Deposit) {
                    type = "DEPOSIT";
                } else if (account instanceof Loan) {
                    type = "LOAN";
                }

                ui.print("\n-----------------------------------------------------");
                ui.print(" " + type + ": " + info + " (" + account.getCurrency() + ")");
                ui.print("-----------------------------------------------------");

                java.util.List<Transaction> history = transactionService.getTransactionHistory(account.getId());

                if (history.isEmpty()) {
                    ui.print("  No transactions found.");
                } else {
                    System.out.printf("%-12s | %-22s | %-15s%n", "DATE", "INFO", "AMOUNT");
                    System.out.println("-------------|------------------------|----------------");

                    for (Transaction t : history) {
                        String date = t.getOpen_date().toString();
                        String infoText = t.getOperation_info();

                        if (infoText.length() > 20) infoText = infoText.substring(0, 20) + "..";

                        String amount = t.getSign() + t.getSum() + " " + t.getCurrency();

                        System.out.printf("%-12s | %-22s | %15s%n", date, infoText, amount);
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

    public Menu CardMenu(Client client){
        Menu cardMenu = new Menu("=== CARDS CABINET ===");
        ui.print("Your current cards:");
        cardService.PrintAllClientsCards(client);

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

             if (!success) {
                ui.print("Transfer failed. Please check balance or card number.");

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

        certainCardMenu.add(ExitCommand());

        return certainCardMenu;
    }

    public Menu DepositMenu(Client client){
        Menu depositMenu = new Menu("=== DEPOSIT CABINET ===");
        depositeService.ShowAllClientDeposits(client);

        depositMenu.add(new Command("Return", () -> {}));
        depositMenu.add(new Command("Open Deposit", () -> {
            Deposit currentDeposit = OpenDeposit();
            Menu certainDepositMenu = CertainDepositMenu(currentDeposit);
            certainDepositMenu.execute();
        }));
        depositMenu.add(new Command("Work with an existing deposit", () -> {
            UUID idDeposit = UUID.fromString(ui.ask("Enter deposit's id"));
            Deposit currentDeposit = depositeService.GetDeposit(idDeposit);
            Menu certainDepositMenu = CertainDepositMenu(currentDeposit);
            certainDepositMenu.execute();
        }));

        depositMenu.add(ExitCommand());

        return  depositMenu;
    }

    public Deposit OpenDeposit(){
        ui.print("=== OPEN NEW DEPOSIT ===");

        Deposit deposit = null;

        try {
            UUID client_id = this.currentClient.getId();
            BigDecimal original_sum = new BigDecimal(ui.ask("Enter original sum"));
            LocalDate open_date = LocalDate.parse(ui.ask("Enter open date"));
            LocalDate close_date = LocalDate.parse(ui.ask("Enter close date"));
            BigDecimal interest_rate =  new BigDecimal(ui.ask("Enter interest rate"));
            String currency =  ui.ask("Enter currency");
            String type  = ui.ask("Enter type of deposit");

            deposit = type.equals("StandardDeposit") ?
                    depositeService.OpenStandardDeposit(client_id,original_sum,open_date,close_date,interest_rate,currency)
                    : depositeService.OpenCapitalizationDeposit(client_id,original_sum,open_date,close_date,interest_rate,currency);
        }
        catch (Exception e){
            String mes = "Error occurred: "+ e.getMessage();
            ui.print(mes);
            log.error(mes);
        }

        if (deposit != null) {
            ui.print("=== Deposit was created successfully! ===");
            depositeService.PrintFullDetails(deposit);
        }

        return deposit;
    }

    public Menu CertainDepositMenu(Deposit deposit){
        Menu certainDepositMenu = new Menu("=== WORK WITH THE DEPOSIT ===");
        depositeService.PrintFullDetails(deposit);
        certainDepositMenu.add(new Command("Return", () -> {}));
        certainDepositMenu.add(new Command("Show Deposit's information details", () -> {
            depositeService.PrintFullDetails(deposit);
        }));
        certainDepositMenu.add(new Command("Early close Deposit", () -> {

            BigDecimal depositSum = depositeService.EarlyCloseDeposit(deposit);
            DepositPayment(depositSum);
        }));
        certainDepositMenu.add(new Command("Close Deposit", () -> {
            BigDecimal depositSum = depositeService.CloseDeposit(deposit);
            DepositPayment(depositSum);
        }));

        certainDepositMenu.add(ExitCommand());

        return  certainDepositMenu;
    }

    private void DepositPayment(BigDecimal depositSum){
        String cardNumber = ui.ask("Enter card number where deposit money should be sent");
        boolean succes = cardService.Transfer(cardNumber, depositSum, "Deposit payment");
        if (succes) {
            String mes = "Deposit has been sent successfully!";
            ui.print(mes);
            log.debug(mes);
        }
        else{
            String mes = "Deposit hasn't been sent successfully!";
            ui.print(mes);
            log.debug(mes);
        }
    }

    public Menu LoanMenu(Client client){
        Menu loanMenu = new Menu("=== LOAN CABINET ===");
        loanService.ShowAllClientLoans(client);

        loanMenu.add(new Command("Return", () -> {}));
        loanMenu.add(new Command("Open Loan", () -> {
            Loan currentLoan= OpenLoan();
            Menu certainLoanMenu = CertainLoanMenu(currentLoan);
            certainLoanMenu.execute();
        }));
        loanMenu.add(new Command("Work with an existing loan", () -> {
            UUID idLoan = UUID.fromString(ui.ask("Enter loan's id"));
            Loan currentLoan = loanService.GetLoan(idLoan);
            Menu certainLoanMenu = CertainLoanMenu(currentLoan);
            certainLoanMenu.execute();
        }));

        return  loanMenu;
    }

    public Loan OpenLoan(){
        ui.print("=== OPEN NEW Loan ===");

        Loan loan = null;

        try {
            UUID client_id = this.currentClient.getId();
            BigDecimal original_sum = new BigDecimal(ui.ask("Enter original sum"));
            LocalDate open_date = LocalDate.parse(ui.ask("Enter open date"));
            LocalDate close_date = LocalDate.parse(ui.ask("Enter close date"));
            BigDecimal interest_rate =  new BigDecimal(ui.ask("Enter interest rate"));
            String currency =  ui.ask("Enter currency");

            loan = loanService.OpenLoan(client_id, original_sum,open_date,close_date,interest_rate,currency);
        }
        catch (Exception e){
            String mes = "Error occurred: "+ e.getMessage();
            ui.print(mes);
            log.error(mes);
        }

        if (loan != null) {
            ui.print("=== Loan was created successfully! ===");
            loanService.PrintFullDetails(loan);
        }

        return loan;
    }

    public Menu CertainLoanMenu(Loan loan){
        Menu certainLoanMenu = new Menu("=== WORK WITH THE LOAN ===");
        loanService.PrintFullDetails(loan);
        certainLoanMenu.add(new Command("Return", () -> {}));
        certainLoanMenu.add(new Command("Show Loan's information details", () -> {
            loanService.PrintFullDetails(loan);
        }));
        certainLoanMenu.add(new Command("Make monthly payment", () -> {
            BigDecimal amount =  new BigDecimal(ui.ask("Enter how much do you want to pay"));
            try {
                loanService.RegularPayment(loan, amount);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }));
        certainLoanMenu.add(new Command("Display payment schedule", () -> {
            loanService.DisplaySchedule(loan);
        }));
        certainLoanMenu.add(new Command( "Early repayment and closure", () -> {
            BigDecimal amount =  new BigDecimal(ui.ask("Enter how much do you want to pay"));
            loanService.FullEarlyRepayment(loan, amount);
        }));
        certainLoanMenu.add(new Command("Close Loan", () -> {
            BigDecimal change = loanService.CloseLoan(loan);

            String cardNumber = ui.ask("Enter card number where change should be sent");
            boolean succes = cardService.Transfer(cardNumber, change, "Change from loan");
            if (succes) {
                String mes = "Loan has been sent successfully!";
                ui.print(mes);
                log.debug(mes);
            }
            else{
                String mes = "Loan hasn't been sent successfully!";
                ui.print(mes);
                log.debug(mes);
            }
        }));

        certainLoanMenu.add(ExitCommand());

        return  certainLoanMenu;
    }
}
