package program.Bank;

import java.util.UUID;

public interface Account {
    UUID getId();
    UUID getClient_id();
    String toString();
    void PrintFullInfo();
    void PrintInfo();
}
