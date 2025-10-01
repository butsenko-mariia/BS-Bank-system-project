package program.Bank;
import java.util.Scanner;
import java.util.UUID;



public class Client
{
    private String id;
    private  String full_name;
    private  String date_of_birth;
    private  String sex;
    private  String nationality;
    private String mobile_phone;
    private  int individual_tax_number;
    private int passport_number;


    private  String legal_address;
    private String place_of_birth;
    private  String record_number;
    private  String place_of_work_or_study;
    public Client() {
        Scanner scanner = new Scanner(System.in);

        this.id = UUID.randomUUID().toString();

        System.out.println("Enter client's full name");
        this.full_name = scanner.nextLine();
        System.out.println("Enter client's date of birth");
        this.date_of_birth = scanner.nextLine();
        System.out.println("Enter client's contact phone");
        this.mobile_phone = scanner.nextLine();
        System.out.println("Enter client's gender");
        this.sex = scanner.nextLine();
        System.out.println("Enter client's nationality");
        this.nationality = scanner.nextLine();
        System.out.println("Enter client's individual tax number");
        this.individual_tax_number = scanner.nextInt();
        System.out.println("Enter client's passport number");
        this.passport_number = scanner.nextInt();
        System.out.println("Enter client's record number");
        this.record_number = scanner.nextLine();
        System.out.println("Enter client's place of birth");
        this.place_of_birth = scanner.nextLine();
        System.out.println("Enter client's legal address");
        this.legal_address = scanner.nextLine();
        System.out.println("Enter client's place of work/study");
        this.place_of_work_or_study = scanner.nextLine();
        System.out.println("This client has been successfully registered");
    }
    public void Print(){
        System.out.printf("Database ID: %s\nFull name: %s\nDate of birth: %s\nContact phone number: %s\nGender: %s\n" +
                        "Nationality: %s\nIndividual tax number: %d\nPassport number: %d\nRecord number: %s\nPlace of birth: %s\n" +
                        "Registered address: %s\nPlace of work/study: %s\n",  id, full_name, date_of_birth,
                sex, nationality, mobile_phone, individual_tax_number, passport_number, legal_address, place_of_birth,
                record_number, place_of_work_or_study);
    }
}
