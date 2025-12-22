package program.Bank;

import java.util.UUID;

public interface Account {
    public UUID getId();
    public UUID getClient_id();
    public String toString();
    public void PrintFullInfo();
    public void PrintInfo();
}
