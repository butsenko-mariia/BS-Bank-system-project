package program.Bank;

import program.Bank.Enums.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDate;
import java.util.UUID;

public class Client {
    private static final Logger log = LogManager.getLogger(Client.class);
    private UUID id;
    private String full_name;
    private LocalDate date_of_birth;
    private String sex;
    private String nationality;
    private String mobile_phone;
    private String individual_tax_number;
    private String passport_number;
    private String legal_address;
    private String place_of_birth;
    private String record_number;
    private String place_of_work_or_study;
    private ClientStatus status;
    private final ConsoleUI ui =  new ConsoleUI();

    public Client() {
        log.debug("Creating a new Client instance");
        this.setId();
        this.status = ClientStatus.ACTIVE;
        log.debug("Client created with status ACTIVE");
    }

    public Client(UUID id) {
        log.debug("Creating Client instance with existing ID: {}", id);
        this.setId(id);
    }

    public UUID getId() {
        return id;
    }

    public void setId() {
        log.info("Attempting to generate new client ID");
        if (id != null) {
            log.error("Client ID already set: {}", id);
            throw new IllegalStateException("Client ID is already set");
        }
        this.id = UUID.randomUUID();
        log.info("Client ID successfully generated: {}", id);
    }

    public void setId(UUID id) {
        log.info("Setting existing client ID: {}", id);
        if (id == null) {
            log.error("Attempt to set null as client ID");
            throw new NumberFormatException("ID must be not null");
        }
        this.id = id;
        log.debug("Client ID successfully set");
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        log.info("Setting full name: {}", full_name);
        if (full_name == null || full_name.isEmpty()) {
            log.error("Full name is empty or null");
            throw new IllegalArgumentException("Full name cannot be empty.");
        }
        if (!full_name.matches("^[A-Za-zА-Яа-яІіЇїЄє'\\-\\s]+$")) {
            log.error("Invalid full name format (contains numbers or special symbols): {}", full_name);
            throw new IllegalArgumentException("Full name cannot contain numbers or special symbols.");
        }
        this.full_name = full_name.trim();
        log.debug("Full name successfully set: {}", this.full_name);
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        log.info("Setting date of birth: {}", date_of_birth);
        if (date_of_birth == null) {
            log.error("Date of birth is null");
            throw new IllegalArgumentException("Date of birth must be in format dd.mm.yyyy.");
        }
        this.date_of_birth = date_of_birth;
        log.debug("Date of birth successfully set");
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        log.info("Setting gender: {}", sex);
        if (!sex.equalsIgnoreCase("M") && !sex.equalsIgnoreCase("W")) {
            log.error("Invalid gender value: {}", sex);
            throw new IllegalArgumentException("Gender must be 'M' or 'W'.");
        }
        this.sex = sex.toUpperCase();
        log.debug("Gender successfully set: {}", this.sex);
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        log.info("Setting nationality: {}", nationality);
        if (nationality == null || nationality.trim().isEmpty()) {
            log.error("Nationality is empty or null");
            throw new IllegalArgumentException("Nationality cannot be empty.");
        }
        this.nationality = nationality.trim();
        log.debug("Nationality successfully set: {}", this.nationality);
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        log.info("Setting mobile phone: {}", mobile_phone);
        if (mobile_phone == null || !mobile_phone.matches("^\\+380\\d{9}$")) {
            log.error("Invalid phone number format: {}", mobile_phone);
            throw new IllegalArgumentException("Phone number must be in format +380XXXXXXXXX.");
        }
        this.mobile_phone = mobile_phone;
        log.debug("Phone number successfully set");
    }

    public String getIndividual_tax_number() {
        return individual_tax_number;
    }

    public void setIndividual_tax_number(String individual_tax_number) {
        log.info("Setting individual tax number (ITN)");
        if (individual_tax_number == null || individual_tax_number.trim().isEmpty()) {
            log.error("ITN is empty or null");
            throw new IllegalArgumentException("Individual tax  number cannot be empty.");
        }
        this.individual_tax_number = individual_tax_number;
        log.debug("ITN successfully set: {}", this.individual_tax_number);
    }

    public String getPassport_number() {
        return passport_number;
    }

    public void setPassport_number(String passport_number) {
        log.info("Setting passport number");
        if (passport_number == null || passport_number.trim().isEmpty()) {
            log.error("Passport number is empty or null");
            throw new IllegalArgumentException("Passport number cannot be empty.");
        }
        this.passport_number = passport_number;
        log.debug("Passport number successfully set: {}", this.passport_number);
    }

    public String getRecord_number() {
        return record_number;
    }

    public void setRecord_number(String record_number) {
        log.info("Setting record number");
        if (record_number == null || record_number.trim().isEmpty()) {
            log.error("Record number is empty or null");
            throw new IllegalArgumentException("Record number cannot be empty.");
        }
        this.record_number = record_number.trim();
        log.debug("Record number successfully set: {}", this.record_number);
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        log.info("Setting place of birth: {}", place_of_birth);
        if (place_of_birth == null || place_of_birth.trim().isEmpty()) {
            log.error("Place of birth is empty or null");
            throw new IllegalArgumentException("Place of birth cannot be empty.");
        }
        this.place_of_birth = place_of_birth.trim();
        log.debug("Place of birth successfully set: {}", this.place_of_birth);

    }

    public String getLegal_address() {
        return legal_address;
    }

    public void setLegal_address(String legal_address) {
        log.info("Setting legal address: {}", legal_address);
        if (legal_address == null || legal_address.trim().isEmpty()) {
            log.error("Legal address is empty or null");
            throw new IllegalArgumentException("Address cannot be empty.");
        }
        this.legal_address = legal_address.trim();
        log.debug("Legal address successfully set: {}", this.legal_address);
    }

    public String getPlace_of_work_or_study() {
        return place_of_work_or_study;
    }

    public void setPlace_of_work_or_study(String place_of_work_or_study) {
        log.info("Setting place of work/study: {}", place_of_work_or_study);
        if (place_of_work_or_study == null || place_of_work_or_study.trim().isEmpty()) {
            log.error("Place of work/study is empty or null");
            throw new IllegalArgumentException("Place of work/study cannot be empty.");
        }
        this.place_of_work_or_study = place_of_work_or_study.trim();
        log.debug("Place of work/study successfully set: {}", this.place_of_work_or_study);
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        log.info("Setting client status: {}", status);
        this.status = status;
        log.debug("Client status successfully set: {}", this.status);
    }

    @Override
    public String toString() {
        log.debug("Converting client data to text representation");
        return String.format(
                "Client id = " + this.getId()+
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
                        ",\nClient status = " + this.getStatus());
    }

    public void PrintClientFullInfo() {
        log.info("Printing full info for client ID: {}", id);
        ui.print(this.toString());
        log.debug("Full client info printed");
    }

    public void PrintInfo() {
        log.debug("Printing short client info");
        String mes = String.format(
                "Welcome, " + this.getFull_name() + "!\n" +
                        "Status: " + this.getStatus() + "\n" +
                        "Mobile phone: " + this.getMobile_phone());

        ui.print(mes);
    }
}