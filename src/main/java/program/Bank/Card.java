package program.Bank;

import java.util.Scanner;
import java.util.UUID;

public class Card implements Account{
    private String account_id;
    private String client_id;
    private AccountType account_type;
    private double balance;
    private String currency;
    private AccountStatus status;

    public Card() {
        Scanner scanner = new Scanner(System.in);

        this.account_id = UUID.randomUUID().toString();
        this.balance = 0;
        this.status = AccountStatus.OPEN;

        System.out.println("Enter owner id");
        this.client_id = scanner.nextLine();
        System.out.println("Enter account type");
        //this.account_type =
        System.out.println("Enter account currency");
        this.currency = scanner.nextLine();
    }
    //реалізувати метод стрінг
    @Override
    public String toString() {
        return "";
    }
    public void Print() {
        System.out.println(this.toString());
    }
}