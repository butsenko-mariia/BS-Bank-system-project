package program;

import program.Bank.Builders.CardBuilder;
import program.Bank.Card;
import program.Bank.Client;
import program.Bank.Loan;


import java.io.FileWriter;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import program.Bank.StandardDeposit;

public class App {
    private static String path = "C:/DataBase/Clients.txt";
    private static File clientsInfoReader;
    private static FileWriter clientsInfoWriter;


    public static void main(String[] args) {
        try {
            // Використовуємо "UTF-8" як текст (у лапках). Це працює всюди.
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Loan loan = new Loan();
        //loan.PrintFullInfo();

        //Card card = new Card();
        //card.PrintFullInfo();

        //Client client = new Client();
        //client.PrintClientFullInfo();


        System.out.println("--- START ---");


        String testClientIdString = "11111111-1111-1111-1111-111111111111";

        try {

             UUID id = UUID.fromString(testClientIdString);

             Client client = new Client(id);
             System.out.println("EMPTY CLIENT START");

            client.Fetch();
            System.out.println("DOWNLOAD FROM DB.");

            client.PrintClientFullInfo();


            UUID cardId = UUID.fromString("c0000001-1111-1111-1111-111111111111");
            Card card = new Card(cardId);
            card.Fetch();
            card.PrintFullInfo();



/*
            System.out.println("\n--- START DEPOSIT ---");
            // Вставте сюди той ID депозиту, який ви щойно створили в pgAdmin
            UUID depositId = UUID.fromString("d0000001-1111-1111-1111-111111111111");

            // Використовуємо StandardDeposit, бо Deposit - абстрактний
            StandardDeposit deposit = new StandardDeposit(depositId);

            deposit.Fetch(); // Завантажуємо дані
            System.out.println("SUPER.");

            deposit.PrintFullInfo(); // Виводимо на екран

*/
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR FORMAT ID.");
        } catch (Exception e) {
            System.out.println("ERROR:");
            e.printStackTrace();
        }















//        try{
//            clientsInfoReader = new File("C:/DataBase/Clients.txt");
//            clientsInfoWriter = new FileWriter("C:/DataBase/Clients.txt", true);
//        }
//        catch(Exception e){
//            System.out.println("Exception was occured.");
//            e.printStackTrace();
//        }


        //WriteFile(clientsInfoWriter );
        //ReadFile(clientsInfoReader);
    }

private static void ReadFile(File file) {

//    try {;
//        Scanner scanner = new Scanner(file);
//        while (scanner.hasNextLine()) {
//            String data = scanner.nextLine();
//            System.out.println(data);
//        }
//        scanner.close();
//    } catch (Exception e) {
//        System.out.println("Exception was occured.");
//        e.printStackTrace();
//    }
}
//    private static void WriteFile(FileWriter file){
//        try {
//            Client client = new Client();
//            file.write(client.toString()+"\n\n");
//            file.close();
//            System.out.println("Successfully saved to file.");
//        } catch (Exception e) {
//            System.out.println("Exception was occured.");
//            e.printStackTrace();
//        }

}
