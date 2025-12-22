package program.Bank;

import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);

    public String ask(String message) {
        System.out.print(message + ": ");
        return scanner.nextLine();
    }

    public void print(String message) {
        System.out.println(message);
    }
}