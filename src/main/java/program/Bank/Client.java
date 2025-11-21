package program.Bank;
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
    public Client() {

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

    @Override
    public String toString() {
        return String.format(
                "Сlient id = " + id +
                ",\nFull name = " + full_name +
                ",\nDate of birth = " + date_of_birth +
                ",\nSex = " + sex +
                ",\nNationality = " + nationality +
                ",\nMobile phone = " + mobile_phone +
                ",\nIndividual tax number = " + individual_tax_number +
                ",\nPassport number = " + passport_number +
                ",\nLegal address = " + legal_address +
                ",\nPlace of birt = " + place_of_birth +
                ",\nRecord number = " + record_number +
                ",\nPlace of work or study = " + place_of_work_or_study +
                ",\nClient status = " + status );
    }
    public void Print(){
        System.out.printf(this.toString());
    }
}
