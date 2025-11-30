package program.Bank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


public class Client
{
    private UUID id;
    private  String full_name;
    private LocalDate date_of_birth;
    private  String sex;
    private  String nationality;
    private String mobile_phone;
    private  String individual_tax_number;
    private String passport_number;
    private  String legal_address;
    private String place_of_birth;
    private  String record_number;
    private  String place_of_work_or_study;
    private ClientStatus  status;
    private BigDecimal balance;
    public Client(){}
    public Client(UUID id) {
        this.id = id;
    }
    public UUID getId() {
        return id;
    }
    public void setId() {
        if (id != null) {
            throw  new IllegalStateException("Client ID is already set");
        }
        this.id = UUID.randomUUID();
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
    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        if (date_of_birth == null)
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

    public String getIndividual_tax_number() {
        return individual_tax_number;
    }

    public void setIndividual_tax_number(String individual_tax_number) {
        if (individual_tax_number == null || individual_tax_number.trim().isEmpty())
            throw new IllegalArgumentException("Individual tax  number cannot be empty.");
        this.individual_tax_number = individual_tax_number;
    }
    public String getPassport_number() {
        return passport_number;
    }

    public void setPassport_number(String passport_number) {
        if (passport_number == null || passport_number.trim().isEmpty())
            throw new IllegalArgumentException("Passport number cannot be empty.");
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
    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {

        boolean ifMatchStatus = false;
        for (ClientStatus clientStatus : ClientStatus.values()) {
            if (status == clientStatus) {
                this.status = status;
                ifMatchStatus = true;

            }
        }

        if (!ifMatchStatus) {
            throw new IllegalArgumentException("Invalid client status.");
        }
    }
    public BigDecimal getBalance() {
        //тут має бути функція яка рахує суму на всії рахунках клієнта
        return balance;
    }

    @Override
    public String toString() {
        return String.format(
                "Сlient id = " + this.getId() + "\n" +
                ",\nFull name = " + this.getFull_name() +
                ",\nDate of birth = " + this.getDate_of_birth() +
                ",\nSex = " + this.getSex() +
                ",\nNationality = " + this.getNationality() +
                ",\nMobile phone = " + this.getMobile_phone() +
                ",\nIndividual tax number = " + this.getIndividual_tax_number() +
                ",\nPassport number = " + this.getPassport_number() +
                ",\nLegal address = " + this.getLegal_address() +
                ",\nPlace of birt = " + this.getPlace_of_birth() +
                ",\nRecord number = " + this.getRecord_number() +
                ",\nPlace of work or study = " + this.getPlace_of_work_or_study() +
                ",\nClient status = " + this.getStatus() );
    }
    public void PrintClientFullInfo(){
        System.out.printf(this.toString());
    }
    public void PrintClientAccountInfo(){
        String info = "Вітаємо, Іван Петрович!\n" +
                "Статус: "+ this.getStatus() +"\n" +
                "Баланс: "+ this.getBalance() +" грн\n" +
                "ВАШІ РАХУНКИ\n";
        //тут має бути функція яка по номеру ід клієнта знаходить всі його рахунки в базі даних і виводить їх
        //+ "UAH (#acc001) - 5,000 грн\n" +
                //"USD (#acc002) - 1,200 $ ";
        System.out.printf(info);
    }
    public void PrintClientTransactionHistory(){
        //тут має бути функція яка за номером ід клієнта знаходить усі транзакції з усіх рахунків даного клієнта
        String info = "";
        System.out.printf(info);
    }
    public void PrintClientActiveDeposit(){
        //тут має бути функція яка перебирає усі наявні депозити за номером ід клієнта в базі даних
        //які є активними та виводить інфу про них
    }
    public void PrintClientDepositHistory(){
        //тут має бути функція яка перебирає усі наявні депозити за номером ід клієнта в базі даних
        //та виводить інфу про кожен з них в порядку по даті створення
    }
    public void PrintClientActiveCredit(){
        //тут має бути функція яка перебирає усі наявні кредити за номером ід клієнта в базі даних
        //які є активними та виводить інфу про них
    }
    public void PrintClientCreditHistory(){
        //тут має бути функція яка перебирає усі наявні кредити за номером ід клієнта в базі даних
        //та виводить інфу про кожен з них в порядку по даті створення
    }
    public void PrintClientActiveCard(){
        //тут має бути функція яка перебирає усі наявні кредити за номером ід клієнта в базі даних
        //які є активними та виводить інфу про них
    }
}
