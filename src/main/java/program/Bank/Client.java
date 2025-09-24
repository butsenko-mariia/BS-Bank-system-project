package program.Bank;
import java.util.Scanner;
import java.util.UUID;



public class Client
{
    private String id;
    private  String full_name;
    private  String date_of_birth;
    private int mobile_phone;
    private  int individual_tax_number;
    private int passport_number;
    private  String legal_address;
    private String place_of_birth;
    private  String record_number;
    private  String place_of_work_or_study;
    public Client()
    {
        Scanner scanner = new Scanner(System.in);

        this.id = UUID.randomUUID().toString();

        System.out.println("Введіть ПІБ клієнта");
        this.full_name = scanner.nextLine();
        System.out.println("Введіть дату народження клієнта");
        this.date_of_birth = scanner.nextLine();
        System.out.println("Введіть контактний телефон клієнта");
        this.mobile_phone = scanner.nextInt();
        System.out.println("Введіть РНОКПП клієнта");
        this.individual_tax_number = scanner.nextInt();
        System.out.println("Введіть номер паспорту клієнта");
        this.passport_number = scanner.nextInt();
        System.out.println("Введіть зареєстроване місце проживання клієнта");
        this.legal_address = scanner.nextLine();
        System.out.println("Введіть місце народження клієнта");
        this.place_of_birth = scanner.nextLine();
        System.out.println("Введіть запис № (УНЗР) клієнта");
        this.record_number = scanner.nextLine();
        System.out.println("Введіть місце роботи/навчання клієнта");
        this.place_of_work_or_study = scanner.nextLine();


        System.out.println("Даного клієнта зареєстровано");
    }

}
