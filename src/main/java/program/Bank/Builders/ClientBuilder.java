package program.Bank.Builders;

import program.Bank.Client;
import program.Bank.DateBase;
import program.Bank.Enums.ClientStatus;

import java.time.LocalDate;

public class ClientBuilder {
    private Client client;
    public ClientBuilder(){
        client = new Client();
    }
    public static ClientBuilder create(){
        return new ClientBuilder();
    }
    public ClientBuilder full_name(String full_name) {
        client.setFull_name(full_name);
        return this;
    }
    public ClientBuilder date_of_birth(LocalDate date_of_birth) {
        client.setDate_of_birth(date_of_birth);
        return this;
    }
    public ClientBuilder sex(String sex){
        client.setSex(sex);
        return this;
    }
    public ClientBuilder nationality(String nationality) {
        client.setNationality(nationality);
        return this;
    }
    public ClientBuilder mobile_phone(String mobile_phone) {
        client.setMobile_phone(mobile_phone);
        return this;
    }
    public ClientBuilder individual_tax_number(String individual_tax_number) {
        client.setIndividual_tax_number(individual_tax_number);
        return this;
    }
    public ClientBuilder passport_number(String passport_number) {
        client.setPassport_number(passport_number);
        return this;
    }
    public ClientBuilder legal_address(String legal_address) {
        client.setLegal_address(legal_address);
        return this;
    }
    public ClientBuilder place_of_birth(String place_of_birth) {
        client.setPlace_of_birth(place_of_birth);
        return this;
    }
    public ClientBuilder record_number(String record_number) {
        client.setRecord_number(record_number);
        return this;
    }
    public ClientBuilder place_of_work_or_study(String place_of_work_or_study) {
        client.setPlace_of_work_or_study(place_of_work_or_study);
        return this;
    }
    public ClientBuilder status(ClientStatus status) {
        client.setStatus(status);
        return this;
    }
    public ClientBuilder fetch(){
        DateBase.Fetch(client);
        return this;
    }
    public Client build() {
        if (client == null || client.getId() == null || client.getFull_name() == null || client.getPassport_number() == null
                || client.getIndividual_tax_number() == null) {
            throw  new IllegalStateException("Some fields are null.");
        }
        return client;
    }
    public void reset(){
        client = new Client();
    }
}
