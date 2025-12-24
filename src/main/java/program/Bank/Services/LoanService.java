package program.Bank.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Bank.*;
import program.Bank.Builders.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoanService {
    private static final Logger log = LogManager.getLogger(LoanService.class);
    private final DataBase dataBase;
    private final ConsoleUI ui =  new ConsoleUI();

    public LoanService(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Loan OpenLoan(UUID client_id, BigDecimal original_sum, LocalDate open_date, LocalDate close_date,
                         BigDecimal interest_rate, String currency){
        log.info("Initiating Loan opening for client: {}. Sum: {} {}", client_id, original_sum, currency);
        Loan loan = LoanBuilder.create()
                .client_id(client_id)
                .interest_rate(interest_rate)
                .original_sum(original_sum)
                .open_date(open_date)
                .close_date(close_date)
                .currency(currency)
                .build();
        dataBase.Upload(loan);
        log.info("Loan opened successfully. ID: {}", loan.getId());
        return loan;
    }

    public void PrintFullDetails(Loan loan){

        log.debug("Printing full details for loan: {}", loan.getId());
        loan.PrintFullInfo();
    }

    public void PrintShortInfo(Loan loan){
        loan.PrintInfo();
    }

    public void ShowAllClientLoans(Client client){
        log.info("Fetching all loans for client: {}", client.getId());
        String query = "SELECT id  FROM loan WHERE client_id = ?";
        boolean foundAny = false;

        try (Connection conn = dataBase.Connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, client.getId());
            ResultSet rs = stmt.executeQuery();
            ui.print("Your loans:");

            while (rs.next()) {
                foundAny = true;
                UUID loanId = (UUID) rs.getObject("id");
                Loan loan = new Loan(loanId);

                if (loan != null) {
                    dataBase.Fetch(loan);
                    loan.PrintInfo();
                    ui.print("------------------------------");
                }
            }

            if (!foundAny) {
                String mes = "This customer has no outstanding loans.";
                log.info(mes);
                ui.print(mes);

            }
        } catch (Exception e) {
            String mes = "Error loading credits: " + e.getMessage();
            log.error(mes);
            ui.print(mes);
        }
    }

    public BigDecimal CloseLoan(Loan loan){
        log.info("Attempting to close loan: {}", loan.getId());
        loan.Close();
        dataBase.Update(loan);
        if (loan.getChange().compareTo(BigDecimal.ZERO) > 0){
            log.info("Loan {} closed. Change returned: {}", loan.getId(), loan.getChange());
            return loan.getChange();
        }
        log.info("Loan {} closed without change.", loan.getId());
        return null;
    }

    public BigDecimal FullEarlyRepayment(Loan loan, BigDecimal amount){
        log.info("Processing early repayment for loan: {}. Amount: {}", loan.getId(), amount);
        loan.FullEarlyRepayment(amount);
        dataBase.Update(loan);
        if (loan.getChange().compareTo(BigDecimal.ZERO) > 0){
            log.info("Early repayment successful. Change: {}", loan.getChange());
            return loan.getChange();
        }
        log.info("Early repayment successful. No change.");
        return null;
    }

    public void DisplaySchedule(Loan loan){
        log.debug("Displaying schedule for loan: {}", loan.getId());
        loan.DisplaySchedule();
    }

    public void RegularPayment(Loan loan, BigDecimal payment) throws Exception {
        log.info("Processing regular payment for loan: {}. Amount: {}", loan.getId(), payment);
        try {
            loan.RegularPayment(payment);
            log.info("Regular payment accepted.");
        }
        catch(Exception e){
            log.warn("Payment failed for loan {}: {}", loan.getId(), e.getMessage());
            ui.print(e.getMessage());
        }
    }

    public Loan GetLoan(UUID loan_id){
        log.debug("Retrieving loan: {}", loan_id);
        Loan loan = new Loan(loan_id);
        dataBase.Fetch(loan);

        return loan;
    }

    public List<Loan> getClientLoans(UUID clientId) {
        log.debug("Getting list of loans for client: {}", clientId);
        List<Loan> loans = new ArrayList<>();
        String query = "SELECT id FROM loan WHERE client_id = ?";

        try (Connection conn = dataBase.Connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setObject(1, clientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                UUID loanId = (UUID) rs.getObject("id");
                Loan loan = new Loan(loanId);
                dataBase.Fetch(loan);
                loans.add(loan);
            }
        } catch (Exception e) {
            log.error("Error retrieving list of loans: " + e.getMessage());
            ui.print("Error retrieving list of loans: " +e.getMessage());
        }
        return loans;
    }
}
