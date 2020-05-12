package impl;

import api.*;
import api.operations.AlertAccount;
import api.operations.ValidateCheck;
import api.operations.request.AlertAccountBankRequestAttributes;
import api.operations.request.ValidateCheckBankRequestAttributes;
import api.operations.response.AlertAccountBankResponseAttributes;
import api.operations.response.ValidateCheckBankResponseAttributes;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class FraudTest {
    Fraud fraud;
    long accountID = 7437930589658322L;

    Bank testBank;
    List<IBankBranch> bankBranches;
    List<IATM> atms;

    BankBranch testBranch;
    ATM testATM;

    @Before
    public void setUp() throws Exception {
        testBank = new Bank("Test Bank Setup");

        bankBranches = testBank.getBranches();

        for (IBankBranch branch : bankBranches) {
            branch.setBank(testBank);
        }

        atms = new ArrayList<>();

        for (IBankBranch branch : bankBranches) {

            try {

                branch.loadATMsFromFile();
                atms.addAll(branch.getATMs());

            } catch (IOException e) {
                System.out.println("Error loading ATMs from file: " + e.getMessage());
            }
        }

        testBranch = (BankBranch) bankBranches.get(0);
        testATM = (ATM) atms.get(0);

        testBank.setBankName("Test Bank");

        fraud = new Fraud(testBank);

    }

    @Test
    public void respondAlertAccount() {
        AlertAccountBankRequestAttributes alertRequestAttributes = new AlertAccountBankRequestAttributes(accountID);
        BankResponse<AlertAccount, AlertAccountBankResponseAttributes> alertResponse = fraud.respondAlertAccount(new BankRequest<>(alertRequestAttributes));
        assertTrue(alertResponse.getBankResponseAttributes().isSuccessful());
        assertEquals(AlertAccountBankResponseAttributes.AlertAccountStatus.Flagged, alertResponse.getBankResponseAttributes().getStatus());

        alertRequestAttributes = new AlertAccountBankRequestAttributes(accountID);
        alertResponse = fraud.respondAlertAccount(new BankRequest<>(alertRequestAttributes));
        assertTrue(alertResponse.getBankResponseAttributes().isSuccessful());
        assertEquals(AlertAccountBankResponseAttributes.AlertAccountStatus.Locked, alertResponse.getBankResponseAttributes().getStatus());
    }

    @Test
    public void respondValidateCheck() {
        Date date = new Date();
        Check check = new Check(100, 123456, 123456, 123456, date);
        ValidateCheckBankRequestAttributes validateAttributes = new ValidateCheckBankRequestAttributes(accountID, check);
        BankResponse<ValidateCheck, ValidateCheckBankResponseAttributes> validateResponse = fraud.respondValidateCheck(new BankRequest<>(validateAttributes));
        assertTrue(validateResponse.getBankResponseAttributes().isSuccessful());

        Check newCheck = new Check(123, 123456, 123456, 123456, date);
        validateAttributes = new ValidateCheckBankRequestAttributes(accountID, newCheck);
        validateResponse = fraud.respondValidateCheck(new BankRequest<>(validateAttributes));
        assertFalse(validateResponse.getBankResponseAttributes().isSuccessful());
    }
}