import api.AccountType;
import api.Check;
import api.IATM;
import api.IBankBranch;
import impl.ATM;
import impl.Bank;
import impl.BankBranch;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class TestCases {
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
    public void customerAccessesATMWithValidCredentials() {
        testATM.authenticateCustomer(cardNumber, pin);

        assertTrue(testATM.inSession());
    }

    @Test(expected = IllegalArgumentException.class)
    public void customerAccessATMWithInvalidCredentials() {
        testATM.authenticateCustomer(cardNumber + 123, pin +123);
    }

    @Test(expected = IllegalArgumentException.class)
    public void customerAccessesATMWithValidCredentialsLockedAccount() {
        testATM.authenticateCustomer(7437930589658323L, 1234);
    }

    @Test
    public void customerMakesValidDepositAtATM() {
        testATM.authenticateCustomer(cardNumber, pin);
        testATM.depositCash(AccountType.Checking, 10000, false);

        assertEquals( 22345.0, testATM.viewAccount(AccountType.Checking), .001);
    }

    @Test
    public void customerMakesFraudulentDepositAtATM() {
        testATM.authenticateCustomer(cardNumber, pin);

        Check check = new Check(10000, 123456, 123456, 123, new Date());
        testATM.depositCheck(AccountType.Checking, check, false);
        boolean depositResult = testATM.depositCheck(AccountType.Checking, check, false);

        assertFalse(depositResult);
    }

    @Test
    public void customerMakesValidWithdrawAtATM() {
        testATM.authenticateCustomer(cardNumber, pin);
        testATM.withdraw(AccountType.Checking, 100, false);

        assertEquals( 12245.0, testATM.viewAccount(AccountType.Checking), .001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void customerMakesInvalidWithdrawAtATMInsufficientBalance() {
        testATM.authenticateCustomer(cardNumber, pin);
        testATM.withdraw(AccountType.Checking, 123456, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void customerMakesInvalidWithdrawAtATMLowCash() {
        testATM.authenticateCustomer(cardNumber, pin);
        testATM.withdraw(AccountType.Saving, 9999999999L, false);
    }

    @Test
    public void customerEndsATMSession() {
        testATM.authenticateCustomer(cardNumber, pin);
        testATM.quit();
        assertFalse(testATM.inSession());
    }

    @Test
    public void customerOpensNewAccount() {
        long newAccount = testBranch.createAccount("Test Name");
        long[] newCard = testBranch.openCard(newAccount);
        testATM.authenticateCustomer(newCard[0], (int) newCard[1]);
        assertTrue(testATM.inSession());
    }

    @Test
    public void customerOpensNewCard() {
        long[] newCard = testBranch.openCard(accountID);
        testATM.authenticateCustomer(newCard[0], (int) newCard[1]);
        assertTrue(testATM.inSession());
    }

    @Test (expected = IllegalArgumentException.class)
    public void customerOpensNewCardInvalidAccountNumber() {
        testBranch.openCard(accountID + 123);
    }

    @Test
    public void customerClosesCard() {
        long[] newCard = testBranch.openCard(accountID);

    }

    @Test (expected = IllegalArgumentException.class)
    public void customerClosesCardInvalidCardNumber() {
        testBranch.closeCard(cardNumber + 123);
    }

    @Test
    public void customerChangesPin() {
        testBranch.changePinNumber(cardNumber, 4321);
        testATM.authenticateCustomer(cardNumber, 4321);
        assertTrue(testATM.inSession());
    }

    @Test(expected = IllegalArgumentException.class)
    public void customerChangesPinInvalidCardNumber() {
        testBranch.changePinNumber(cardNumber + 123, 4321);
    }

    @Test(expected = IllegalArgumentException.class)
    public void customerChangesPinSamePin() {
        testBranch.changePinNumber(cardNumber, pin);
    }


}
