package program.Bank;

import java.util.UUID;

public class Account {
    private String id;
    private double balance;
    public Account() {
        this.id = UUID.randomUUID().toString();
        this.balance = 0;
    }

}
