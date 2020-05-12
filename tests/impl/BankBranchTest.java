package impl;

import api.*;
import api.operations.*;
import api.operations.request.*;
import api.operations.response.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class BankBranchTest {
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
    public void getBranchName() {
        assertEquals("Test Branch", testBranch.getBranchName());
    }

    @Test
    public void getBank() {
        assertEquals(testBank, testBranch.getBank());
    }

    @Test
    public void setBank() {
        Bank newBank = new Bank("SFH Bank");
        testBranch.setBank(newBank);
        assertEquals(newBank, testBranch.getBank());
    }


    @Test
    public void addATM() {
        ATM newATM = new ATM();
        testBranch.addATM(newATM);
        boolean hasATM = false;

        for (IATM atm : testBranch.getATMs()) {
            if (atm.equals(newATM)) {
                hasATM = true;
            }
        }

        assertTrue(hasATM);
    }

    @Test
    public void createAccount() {
        assertNotNull(testBranch.createAccount("Test Name"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteAccountWithBadAccountID() {
        testBranch.deleteAccount(0);
    }

    @Test
    public void openCard() {
        assertNotNull(testBranch.openCard(1234567890L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void openCardWithBadAccountID() {
        testBranch.openCard(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void closeCardWithBadCardNumber() {
        testBranch.closeCard(1);
    }

    @Test
    public void getFraud() {
        assertSame(testBank.getFraud(), testBranch.getFraud());
    }

    @Test
    public void getMaintenance() {
        assertSame(testBank.getMaintenance(), testBranch.getMaintenance());
    }

    @Test
    public void respondCredentialValidation() {
        long cardNumber = 7437930589658322L;
        int pin = 1234;
        CredentialsValidationBankRequestAttributes requestAttributes = new CredentialsValidationBankRequestAttributes(cardNumber, pin);
        BankResponse<CredentialsValidation, CredentialsValidationBankResponseAttributes> bankResponse = testBranch.respondCredentialValidation(new BankRequest<>(requestAttributes));
        assertTrue(bankResponse.getBankResponseAttributes().isValid());
    }

    @Test
    public void respondBalanceView() {
        BalanceViewBankRequestAttributes requestAttributes = new BalanceViewBankRequestAttributes(testAccountID, AccountType.Checking);
        BankResponse<BalanceView, BalanceViewBankResponseAttributes> bankResponse = testBranch.respondBalanceView(new BankRequest<>(requestAttributes));
        BalanceViewBankResponseAttributes responseAttributes = bankResponse.getBankResponseAttributes();
        assertEquals(responseAttributes.getAmount(), 12345.0, .001);
    }

    @Test
    public void respondDepositCash() {
        DepositCashBankRequestAttributes requestAttributes = new DepositCashBankRequestAttributes(testAccountID, 10000, AccountType.Checking);
        testBranch.respondDepositCash(new BankRequest<>(requestAttributes));

        BalanceViewBankRequestAttributes balanceRequestAttributes = new BalanceViewBankRequestAttributes(testAccountID, AccountType.Checking);
        BankResponse<BalanceView, BalanceViewBankResponseAttributes> bankResponse = testBranch.respondBalanceView(new BankRequest<>(balanceRequestAttributes));
        BalanceViewBankResponseAttributes responseAttributes = bankResponse.getBankResponseAttributes();
        assertEquals(responseAttributes.getAmount(), 22345.0, .001);
    }

    @Test
    public void respondDepositCheck() {
        Check check = new Check(10000, 123456789, 987654321, 123, new Date());

        DepositCheckBankRequestAttributes requestAttributes = new DepositCheckBankRequestAttributes(testAccountID, check, AccountType.Checking);
        testBranch.respondDepositCheck(new BankRequest<>(requestAttributes));

        BalanceViewBankRequestAttributes balanceRequestAttributes = new BalanceViewBankRequestAttributes(testAccountID, AccountType.Checking);
        BankResponse<BalanceView, BalanceViewBankResponseAttributes> bankResponse = testBranch.respondBalanceView(new BankRequest<>(balanceRequestAttributes));
        BalanceViewBankResponseAttributes responseAttributes = bankResponse.getBankResponseAttributes();
        assertEquals(responseAttributes.getAmount(), 22345.0, .001);
    }

    @Test
    public void respondWithdrawMoney() {
        WithdrawMoneyBankRequestAttributes requestAttributes = new WithdrawMoneyBankRequestAttributes(testAccountID, 10000, AccountType.Checking);
        testBranch.respondWithdrawMoney(new BankRequest<>(requestAttributes));

        BalanceViewBankRequestAttributes balanceRequestAttributes = new BalanceViewBankRequestAttributes(testAccountID, AccountType.Checking);
        BankResponse<BalanceView, BalanceViewBankResponseAttributes> bankResponse = testBranch.respondBalanceView(new BankRequest<>(balanceRequestAttributes));
        BalanceViewBankResponseAttributes responseAttributes = bankResponse.getBankResponseAttributes();
        assertEquals(responseAttributes.getAmount(), 2345.0, .001);
    }
}