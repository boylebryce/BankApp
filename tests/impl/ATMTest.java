package impl;

import api.*;
import api.operations.BalanceView;
import api.operations.CredentialsValidation;
import api.operations.DepositCash;
import api.operations.request.BalanceViewBankRequestAttributes;
import api.operations.request.CredentialsValidationBankRequestAttributes;
import api.operations.request.DepositCashBankRequestAttributes;
import api.operations.response.BalanceViewBankResponseAttributes;
import api.operations.response.CredentialsValidationBankResponseAttributes;
import api.operations.response.DepositCashBankResponseAttributes;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ATMTest {
    Bank testBank;
    List<IBankBranch> bankBranches;
    List<IATM> atms;

    BankBranch testBranch;
    ATM testATM;

    long testAccountID = 7405971150L;

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
    }

    @Test
    public void getId() {
        assertEquals(1234567890L, testATM.getId());
    }

    @Test
    public void setMoneyLevel() {
        testATM.setMoneyLevel(12345.0);
        assertEquals(12345.0, testATM.getMoneyLevel(), .001);
    }

    @Test
    public void getMoneyLevel() {
        assertEquals(1000000.0, testATM.getMoneyLevel(), .001);
    }

    @Test
    public void setInkLevel() {
        testATM.setInkLevel(123);
        assertEquals(123, testATM.getInkLevel());
    }

    @Test
    public void getInkLevel() {
        assertEquals(2000, testATM.getInkLevel());
    }

    @Test
    public void setPaperLevel() {
        testATM.setPaperLevel(123);
        assertEquals(123, testATM.getPaperLevel());
    }

    @Test
    public void getPaperLevel() {
        assertEquals(2000, testATM.getPaperLevel());
    }

    @Test(expected = IllegalArgumentException.class)
    public void authenticateCustomerBadCardNumber() {
        testATM.authenticateCustomer(12345L, 1324);
    }

    @Test
    public void viewAccount() {
        testATM.authenticateCustomer(7437930589658322L, 1234);
        assertEquals(12345.0, testATM.viewAccount(AccountType.Checking), .001);
    }

    @Test
    public void depositCash() {
        testATM.authenticateCustomer(7437930589658322L, 1234);
        testATM.depositCash(AccountType.Checking, 10000.0, false);
        assertEquals(22345.0, testATM.viewAccount(AccountType.Checking), .001);

    }

    @Test
    public void depositCheck() {
        Check check = new Check(10000, 123456789, 987654321, 123, new Date());
        testATM.authenticateCustomer(7437930589658322L, 1234);
        testATM.depositCheck(AccountType.Checking, check, false);
        assertEquals(22345.0, testATM.viewAccount(AccountType.Checking), .001);
    }

    @Test
    public void withdraw() {
        testATM.authenticateCustomer(7437930589658322L, 1234);
        testATM.withdraw(AccountType.Checking, 10000.0, false);
        assertEquals(2345.0, testATM.viewAccount(AccountType.Checking), .001);

    }

    @Test
    public void toDataString() {
        String[] data = testATM.toDataString().split(",");
        assertEquals(testATM.getId(), Long.parseLong(data[0]));
        assertEquals(1000000, Double.parseDouble(data[1]), .001);
        assertEquals(2000, Integer.parseInt(data[2]));
        assertEquals(2000, Integer.parseInt(data[3]));
    }

    @Test
    public void testDataStringConstructor() {
        ATM newTestATM = new ATM(testATM.toDataString(), testBranch);
        String[] data = newTestATM.toDataString().split(",");
        assertEquals(testATM.getId(), Long.parseLong(data[0]));
        assertEquals(1000000, Double.parseDouble(data[1]), .001);
        assertEquals(2000, Integer.parseInt(data[2]));
        assertEquals(2000, Integer.parseInt(data[3]));
    }
}