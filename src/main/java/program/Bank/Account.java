package program.Bank;

import java.util.Scanner;
import java.util.UUID;

public class Account {
    private String account_id;
    private String owner_id;
    private AccountType account_type;
    private double balance;
    private String currency;
    private AccountStatus status;

    public Account() {
        Scanner scanner = new Scanner(System.in);

        this.account_id = UUID.randomUUID().toString();
        this.balance = 0;
        this.status = AccountStatus.OPEN;

        System.out.println("Enter owner id");
        this.owner_id = scanner.nextLine();
        System.out.println("Enter account type");
        this.account_type = SetAccountType(scanner.nextLine());
        System.out.println("Enter account currency");
        this.currency = scanner.nextLine();
    }

    private AccountType SetAccountType(String line) {
        switch (line) {
            case "UNIVERSAL":
                return AccountType.UNIVERSAL;
            case "PAYMENT":
                return AccountType.PAYMENT;
            case "JUNIOR ":
                return AccountType.JUNIOR;
            case "ESUPPORT":
                return AccountType.ESUPPORT;
            case "NATIONAL_CASHBACK":
                return AccountType.NATIONAL_CASHBACK;
        }
        return  AccountType.UNIVERSAL;
    }
}