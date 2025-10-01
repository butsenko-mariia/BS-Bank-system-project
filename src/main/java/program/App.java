package program;
import program.Bank.Client;

import java.io.*;
import java.io.File;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        ReadFile();


    }
private static void ReadFile(){
    try {
        File myObj = new File("C:/BankProject/DataBase/Clients.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            System.out.println(data);
        }
        myReader.close();
    } catch (FileNotFoundException e) {
        System.out.println("Виникла помилка.");
        e.printStackTrace();
    }
}
}
