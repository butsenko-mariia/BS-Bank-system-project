package program.Bank;
import java.util.Scanner;
import java.util.UUID;

public class Client
{
    private String client_id;
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
    private ClientStatus  status;
    public Client() {
        Scanner scanner = new Scanner(System.in);

        this.client_id = UUID.randomUUID().toString();

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
        scanner.nextLine();
        System.out.println("Enter client's record number");
        this.record_number = scanner.nextLine();
        System.out.println("Enter client's place of birth");
        this.place_of_birth = scanner.nextLine();
        System.out.println("Enter client's legal address");
        this.legal_address = scanner.nextLine();
        System.out.println("Enter client's place of work/study");
        this.place_of_work_or_study = scanner.nextLine();
        this.status = ClientStatus.ACTIVE;
        System.out.println("This client has been successfully registered");
    }
//    public String getId() { return this.id;}
//    public String getFullName() {
//        return this.full_name;
//    }
//    public String getDateOfBirth() {
//        return this.date_of_birth;
//    }
//    public String getSex() {
//        return this.sex;
//    }
//    public String getNationality() {
//        return this.nationality;
//    }
//    public void setMobilePhone(String mobile_phone) {
//        this.mobile_phone = mobile_phone;
//    }
//    public String getMobilePhone() {
//        return this.mobile_phone;
//    }
//    public int getIndividualTaxNumber() { return this.individual_tax_number; }
//    public int getPassportNumber() {
//        return this.individual_tax_number;
//    }
//    public  void setLegalAddress(String legal_address) { this.legal_address = legal_address; }
//    public String getLegalAddress() {
//        return this.legal_address;
//    }
//    public String getPlaceOfBirth() { return this.place_of_birth; }
//    public String getRecordNumber() { return this.record_number; }
//    public String getPlaceOfWorkOrStudy() {
//        return this.place_of_work_or_study;
//    }
   public String toString(){
        return  String.format("Database ID: %s\nFull name: %s\nDate of birth: %s\nContact phone number: %s\nGender: %s\n" +
                "Nationality: %s\nIndividual tax number: %d\nPassport number: %d\nRecord number: %s\nPlace of birth: %s\n" +
                "Registered address: %s\nPlace of work/study: %s\n", id, full_name, date_of_birth, sex, nationality,
                mobile_phone, individual_tax_number, passport_number, legal_address, place_of_birth,record_number,
                place_of_work_or_study);
        }
    public void Print(){
        System.out.printf(this.toString());
    }
}
