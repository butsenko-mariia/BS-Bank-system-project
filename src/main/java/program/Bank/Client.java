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
        log.debug("Створення нового екземпляру Client");
        this.setId();
        this.status = ClientStatus.ACTIVE;
        log.debug("Client створено зі статусом ACTIVE");
    }

    public Client(UUID id) {
        log.debug("Створення екземпляру Client з існуючим ID: {}", id);
        this.setId(id);
    }

    public UUID getId() {
        return id;
    }

    public void setId() {
        log.info("Спроба генерації нового ID клієнта");
        if (id != null) {
            log.error("ID клієнта вже встановлено: {}", id);
            throw new IllegalStateException("Client ID is already set");
        }
        this.id = UUID.randomUUID();
        log.info("ID клієнта успішно згенеровано: {}", id);
    }

    public void setId(UUID id) {
        log.info("Встановлення існуючого ID клієнта: {}", id);
        if (id == null) {
            log.error("Спроба встановити null як ID клієнта");
            throw new NumberFormatException("ID must be not null");
        }
        this.id = id;
        log.debug("ID клієнта успішно встановлено");
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        log.info("Встановлення повного імені: {}", full_name);
        if (full_name == null || full_name.isEmpty()) {
            log.error("Повне ім'я порожнє або null");
            throw new IllegalArgumentException("Full name cannot be empty.");
        }
        if (!full_name.matches("^[A-Za-zА-Яа-яІіЇїЄє'\\-\\s]+$")) {
            log.error("Невірний формат повного імені (містить цифри чи спец. символи): {}", full_name);
            throw new IllegalArgumentException("Full name cannot contain numbers or special symbols.");
        }
        this.full_name = full_name.trim();
        log.debug("Повне ім'я успішно встановлено: {}", this.full_name);
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        log.info("Встановлення дати народження: {}", date_of_birth);
        if (date_of_birth == null) {
            log.error("Дата народження є null");
            throw new IllegalArgumentException("Date of birth must be in format dd.mm.yyyy.");
        }
        this.date_of_birth = date_of_birth;
        log.debug("Дату народження успішно встановлено");
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        log.info("Встановлення статі: {}", sex);
        if (!sex.equalsIgnoreCase("M") && !sex.equalsIgnoreCase("W")) {
            log.error("Невірне значення статі: {}", sex);
            throw new IllegalArgumentException("Gender must be 'M' or 'W'.");
        }
        this.sex = sex.toUpperCase();
        log.debug("Стать успішно встановлено: {}", this.sex);
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        log.info("Встановлення національності: {}", nationality);
        if (nationality == null || nationality.trim().isEmpty()) {
            log.error("Національність порожня або null");
            throw new IllegalArgumentException("Nationality cannot be empty.");
        }
        this.nationality = nationality.trim();
        log.debug("Національність успішно встановлено: {}", this.nationality);
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        log.info("Встановлення мобільного телефону: {}", mobile_phone);
        if (mobile_phone == null || !mobile_phone.matches("^\\+380\\d{9}$")) {
            log.error("Невірний формат номеру телефону: {}", mobile_phone);
            throw new IllegalArgumentException("Phone number must be in format +380XXXXXXXXX.");
        }
        this.mobile_phone = mobile_phone;
        log.debug("Номер телефону успішно встановлено");
    }

    public String getIndividual_tax_number() {
        return individual_tax_number;
    }

    public void setIndividual_tax_number(String individual_tax_number) {
        log.info("Встановлення індивідуального податкового номеру (ІПН)");
        if (individual_tax_number == null || individual_tax_number.trim().isEmpty()) {
            log.error("ІПН порожній або null");
            throw new IllegalArgumentException("Individual tax  number cannot be empty.");
        }
        this.individual_tax_number = individual_tax_number;
        log.debug("ІПН успішно встановлено: {}", this.individual_tax_number);
    }

    public String getPassport_number() {
        return passport_number;
    }

    public void setPassport_number(String passport_number) {
        log.info("Встановлення номеру паспорта");
        if (passport_number == null || passport_number.trim().isEmpty()) {
            log.error("Номер паспорта порожній або null");
            throw new IllegalArgumentException("Passport number cannot be empty.");
        }
        this.passport_number = passport_number;
        log.debug("Номер паспорта успішно встановлено: {}", this.passport_number);
    }

    public String getRecord_number() {
        return record_number;
    }

    public void setRecord_number(String record_number) {
        log.info("Встановлення номеру запису");
        if (record_number == null || record_number.trim().isEmpty()) {
            log.error("Номер запису порожній або null");
            throw new IllegalArgumentException("Record number cannot be empty.");
        }
        this.record_number = record_number.trim();
        log.debug("Номер запису успішно встановлено: {}", this.record_number);
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        log.info("Встановлення місця народження: {}", place_of_birth);
        if (place_of_birth == null || place_of_birth.trim().isEmpty()) {
            log.error("Місце народження порожнє або null");
            throw new IllegalArgumentException("Place of birth cannot be empty.");
        }
        this.place_of_birth = place_of_birth.trim();
        log.debug("Місце народження успішно встановлено: {}", this.place_of_birth);

    }

    public String getLegal_address() {
        return legal_address;
    }

    public void setLegal_address(String legal_address) {
        log.info("Встановлення юридичної адреси: {}", legal_address);
        if (legal_address == null || legal_address.trim().isEmpty()) {
            log.error("Юридична адреса порожня або null");
            throw new IllegalArgumentException("Address cannot be empty.");
        }
        this.legal_address = legal_address.trim();
        log.debug("Юридичну адресу успішно встановлено: {}", this.legal_address);
    }

    public String getPlace_of_work_or_study() {
        return place_of_work_or_study;
    }

    public void setPlace_of_work_or_study(String place_of_work_or_study) {
        log.info("Встановлення місця роботи/навчання: {}", place_of_work_or_study);
        if (place_of_work_or_study == null || place_of_work_or_study.trim().isEmpty()) {
            log.error("Місце роботи/навчання порожнє або null");
            throw new IllegalArgumentException("Place of work/study cannot be empty.");
        }
        this.place_of_work_or_study = place_of_work_or_study.trim();
        log.debug("Місце роботи/навчання успішно встановлено: {}", this.place_of_work_or_study);
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        log.info("Встановлення статусу клієнта: {}", status);
        this.status = status;
        log.debug("Статус клієнта успішно встановлено: {}", this.status);
    }

    @Override
    public String toString() {
        log.debug("Перетворення даних клієнта в текстове представлення");
        return String.format(
                "Client id = " + this.getId() + "\n" +
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
        log.info("Виведення повної інформації про клієнта ID: {}", id);
        ui.print(this.toString());
        log.debug("Повну інформацію про клієнта виведено");
    }

    public void PrintInfo() {
        log.debug("Виводимо коротку інформацію про клієнта");
        String mes = String.format(
                "Welcome, " + this.getFull_name() + "!\n" +
                        "Status: " + this.getStatus() + "\n" +
                        "Mobile phone: " + this.getMobile_phone());

        ui.print(mes);
    }
}
