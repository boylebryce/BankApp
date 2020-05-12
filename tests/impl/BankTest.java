package impl;

import api.*;
import api.operations.*;
import api.operations.request.*;
import api.operations.response.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;

import static org.junit.Assert.*;

public class BankTest {
    long accountID = 7405971150L;
    long cardNumber = 7437930589658322L;
    int pin = 1234;

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

    }

    @Test
    public void setBankName() {
        testBank.setBankName("Test Two");
        assertEquals("Test Two", testBank.getBankName());
    }

    @Test
    public void getBankName() {
        assertEquals("Test Bank", testBank.getBankName());
    }

    @Test
    public void addBranch() {
        testBank.addBranch(new BankBranch("New Branch"));

        boolean hasNewBranch = false;
        for (IBankBranch branch : testBank.getBranches()) {
            if (branch.getBranchName().equals("New Branch")) {
                hasNewBranch = true;
                break;
            }
        }

        assertTrue(hasNewBranch);
    }

    @Test
    public void getFraud() {
        assertNotNull(testBank.getFraud());
    }

    @Test
    public void getMaintenance() {
        assertNotNull(testBank.getMaintenance());
    }

    @Test
    public void respondCredentialValidation() {
        CredentialsValidationBankRequestAttributes requestAttributes = new CredentialsValidationBankRequestAttributes(cardNumber, pin);
        BankResponse<CredentialsValidation, CredentialsValidationBankResponseAttributes> bankResponse = testBank.respondCredentialValidation(new BankRequest<>(requestAttributes));
        CredentialsValidationBankResponseAttributes responseAttributes = bankResponse.getBankResponseAttributes();
        assertTrue(responseAttributes.isValid());
    }

    @Test
    public void respondBalanceView() {
        BalanceViewBankRequestAttributes requestAttributes = new BalanceViewBankRequestAttributes(accountID, AccountType.Checking);
        BankResponse<BalanceView, BalanceViewBankResponseAttributes> bankResponse = testBank.respondBalanceView(new BankRequest<>(requestAttributes));
        BalanceViewBankResponseAttributes responseAttributes = bankResponse.getBankResponseAttributes();
        assertEquals(12345.0, responseAttributes.getAmount(), .001);
    }

    @Test
    public void respondDepositCash() {
        DepositCashBankRequestAttributes requestAttributes = new DepositCashBankRequestAttributes(accountID, 10000, AccountType.Checking);
        BankResponse<DepositCash, DepositCashBankResponseAttributes> bankResponse = testBank.respondDepositCash(new BankRequest<>(requestAttributes));
        DepositCashBankResponseAttributes responseAttributes = bankResponse.getBankResponseAttributes();

        BalanceViewBankRequestAttributes balanceRequestAttributes = new BalanceViewBankRequestAttributes(accountID, AccountType.Checking);
        BankResponse<BalanceView, BalanceViewBankResponseAttributes> balanceRequestResponse = testBank.respondBalanceView(new BankRequest<>(balanceRequestAttributes));
        BalanceViewBankResponseAttributes balanceResponseAttributes = balanceRequestResponse.getBankResponseAttributes();
        assertEquals(22345.0, balanceResponseAttributes.getAmount(), .001);
    }

    @Test
    public void respondDepositCheck() {
        Check check = new Check(10000, 123456, 123456, 123, new Date());
        DepositCheckBankRequestAttributes requestAttributes = new DepositCheckBankRequestAttributes(accountID, check, AccountType.Checking);
        BankResponse<DepositCheck, DepositCheckBankResponseAttributes> bankResponse = testBank.respondDepositCheck(new BankRequest<>(requestAttributes));
        DepositCheckBankResponseAttributes responseAttributes = bankResponse.getBankResponseAttributes();

        BalanceViewBankRequestAttributes balanceRequestAttributes = new BalanceViewBankRequestAttributes(accountID, AccountType.Checking);
        BankResponse<BalanceView, BalanceViewBankResponseAttributes> balanceRequestResponse = testBank.respondBalanceView(new BankRequest<>(balanceRequestAttributes));
        BalanceViewBankResponseAttributes balanceResponseAttributes = balanceRequestResponse.getBankResponseAttributes();
        assertEquals(22345.0, balanceResponseAttributes.getAmount(), .001);
    }

    @Test
    public void respondWithdrawMoney() {
        WithdrawMoneyBankRequestAttributes requestAttributes = new WithdrawMoneyBankRequestAttributes(accountID, 10000, AccountType.Checking);
        BankResponse<WithdrawMoney, WithdrawMoneyBankResponseAttributes> bankResponse = testBank.respondWithdrawMoney(new BankRequest<>(requestAttributes));
        WithdrawMoneyBankResponseAttributes responseAttributes = bankResponse.getBankResponseAttributes();

        BalanceViewBankRequestAttributes balanceRequestAttributes = new BalanceViewBankRequestAttributes(accountID, AccountType.Checking);
        BankResponse<BalanceView, BalanceViewBankResponseAttributes> balanceRequestResponse = testBank.respondBalanceView(new BankRequest<>(balanceRequestAttributes));
        BalanceViewBankResponseAttributes balanceResponseAttributes = balanceRequestResponse.getBankResponseAttributes();
        assertEquals(2345.0, balanceResponseAttributes.getAmount(), .001);
    }

    @Test
    public void respondWithdrawMoneyExceedsBalance() {
        WithdrawMoneyBankRequestAttributes requestAttributes = new WithdrawMoneyBankRequestAttributes(accountID, 100000, AccountType.Checking);
        BankResponse<WithdrawMoney, WithdrawMoneyBankResponseAttributes> bankResponse = testBank.respondWithdrawMoney(new BankRequest<>(requestAttributes));
        WithdrawMoneyBankResponseAttributes responseAttributes = bankResponse.getBankResponseAttributes();
        assertFalse(responseAttributes.isSuccessful());
    }

    @Test
    public void respondCreateAccount() {
        CreateAccountBankRequestAttributes requestAttributes = new CreateAccountBankRequestAttributes("Test Name");
        BankResponse<CreateAccount, CreateAccountBankResponseAttributes> response = testBank.respondCreateAccount(new BankRequest<>(requestAttributes));
        assertTrue(response.getBankResponseAttributes().isSuccessful());
    }

    @Test
    public void respondDeleteAccount() {
        DeleteAccountBankRequestAttributes requestAttributes = new DeleteAccountBankRequestAttributes(accountID);
        DeleteAccountBankResponseAttributes responseAttributes = testBank.respondDeleteAccount(new BankRequest<>(requestAttributes)).getBankResponseAttributes();
        assertTrue(responseAttributes.isSuccessful());
    }

    @Test
    public void respondOpenCard() {
        OpenCardBankRequestAttributes requestAttributes = new OpenCardBankRequestAttributes(accountID);
        BankResponse<OpenCard, OpenCardBankResponseAttributes> response = testBank.respondOpenCard(new BankRequest<>(requestAttributes));
        OpenCardBankResponseAttributes responseAttributes = response.getBankResponseAttributes();
        assertTrue(responseAttributes.isSuccessful());
    }

    @Test
    public void respondCloseCard() {
        CloseCardBankRequestAttributes requestAttributes = new CloseCardBankRequestAttributes(cardNumber);
        CloseCardBankResponseAttributes responseAttributes = testBank.respondCloseCard(new BankRequest<>(requestAttributes)).getBankResponseAttributes();
        assertTrue(responseAttributes.isSuccessful());
    }

    @Test
    public void respondChangePinNumber() {
        ChangePinNumberBankRequestAttributes requestAttributes = new ChangePinNumberBankRequestAttributes(cardNumber, 4321);
        ChangePinNumberBankResponseAttributes responseAttributes = testBank.respondChangePinNumber(new BankRequest<>(requestAttributes)).getBankResponseAttributes();
        assertTrue(responseAttributes.isSuccessful());
    }

    @Test
    public void respondLockAccount() {
        LockAccountBankRequestAttributes lockAccountBankRequestAttributes = new LockAccountBankRequestAttributes(accountID);
        BankResponse<LockAccount, LockAccountBankResponseAttributes> response = testBank.respondLockAccount(new BankRequest<>(lockAccountBankRequestAttributes));
        assertTrue(response.getBankResponseAttributes().isSuccessful());
    }
}