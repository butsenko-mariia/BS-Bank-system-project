package program;

import program.Bank.Builders.CardBuilder;
import program.Bank.Card;
import program.Bank.Loan;

import java.io.FileWriter;
import java.io.File;

public class App {
    private static String path = "C:/DataBase/Clients.txt";
    private static File clientsInfoReader;
    private static FileWriter clientsInfoWriter;
    public static void main(String[] args) {
        Loan loan = new Loan();
        loan.PrintFullInfo();

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
