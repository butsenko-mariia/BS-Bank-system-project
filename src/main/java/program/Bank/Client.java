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
    public String getClient_id() {
        return client_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        if (full_name == null || full_name.isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty.");
        }
        if (!full_name.matches("^[A-Za-zА-Яа-яІіЇїЄє'\\-\\s]+$")) {
            throw new IllegalArgumentException("Full name cannot contain numbers or special symbols.");
        }
        this.full_name = full_name.trim();
    }
    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        if (date_of_birth == null || !date_of_birth.matches("^\\d{2}\\.\\d{2}\\.\\d{4}$"))
            throw new IllegalArgumentException("Date of birth must be in format dd.mm.yyyy.");
        this.date_of_birth = date_of_birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        if (!sex.equalsIgnoreCase("M") && !sex.equalsIgnoreCase("W")) {
            throw new IllegalArgumentException("Gender must be 'M' or 'W'.");
        }
        this.sex = sex.toUpperCase();
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        if (nationality == null || nationality.trim().isEmpty())
            throw new IllegalArgumentException("Nationality cannot be empty.");
        this.nationality = nationality.trim();
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        if (mobile_phone == null || !mobile_phone.matches("^\\+380\\d{9}$"))
            throw new IllegalArgumentException("Phone number must be in format +380XXXXXXXXX.");
        this.mobile_phone = mobile_phone;
    }

    public int getIndividual_tax_number() {
        return individual_tax_number;
    }

    public void setIndividual_tax_number(int individual_tax_number) {
        if (individual_tax_number <= 0)
            throw new IllegalArgumentException("Tax number must be positive.");
        this.individual_tax_number = individual_tax_number;
    }
    public int getPassport_number() {
        return passport_number;
    }

    public void setPassport_number(int passport_number) {
        if (passport_number <= 0)
            throw new IllegalArgumentException("Passport number must be positive.");
        this.passport_number = passport_number;
    }

    public String getRecord_number() {
        return record_number;
    }

    public void setRecord_number(String record_number) {
        if (record_number == null || record_number.trim().isEmpty())
            throw new IllegalArgumentException("Record number cannot be empty.");
        this.record_number = record_number.trim();
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        if (place_of_birth == null || place_of_birth.trim().isEmpty())
            throw new IllegalArgumentException("Place of birth cannot be empty.");
        this.place_of_birth = place_of_birth.trim();
    }
    public String getLegal_address() {
        return legal_address;
    }

    public void setLegal_address(String legal_address) {
        if (legal_address == null || legal_address.trim().isEmpty())
            throw new IllegalArgumentException("Address cannot be empty.");
        this.legal_address = legal_address.trim();
    }

    public String getPlace_of_work_or_study() {
        return place_of_work_or_study;
    }

    public void setPlace_of_work_or_study(String place_of_work_or_study) {
        if (place_of_work_or_study == null || place_of_work_or_study.trim().isEmpty())
            throw new IllegalArgumentException("Place of work/study cannot be empty.");
        this.place_of_work_or_study = place_of_work_or_study.trim();
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
                    "Registered address: %s\nPlace of work/study: %s\n", client_id, full_name, date_of_birth, sex, nationality,
            mobile_phone, individual_tax_number, passport_number, legal_address, place_of_birth,record_number,
            place_of_work_or_study);
}
    public void Print(){
        System.out.printf(this.toString());
    }
}
