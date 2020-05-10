import api.*;
import impl.ATM;
import impl.Bank;
import impl.BankBranch;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BankTest {
    private IBank bank;
    private IBankBranch bankBranch;
    private IATM atm;
    private String accountName;

    @Before
    public void setUp() {
       bank = new Bank("Test Bank");
       bankBranch = new BankBranch(" London Test Bank");
       atm = new ATM();

       bank.newBranch(bankBranch);
       bankBranch.newATM(atm);
    }

    @Test
    public void test1() {
        System.out.println("TEST #1");
        accountName = "John Smith";
        long accountId = bankBranch.createAccount(accountName);
        System.out.println("Account for " + accountName + " created. Id = " + accountId);
        long[] ns = bankBranch.openCard(accountId);
        long cardId = ns[0];
        System.out.println(accountName + " opened card. Card Id = " + cardId);
        int newPinNumber = (int)ns[1];
        bankBranch.changePinNumber(cardId, newPinNumber);

        try {
            atm.authenticateCustomer(cardId, 0);
            System.out.println("ERROR!!! This code must NOT execute");
        }
        catch (Exception e ) {
            System.out.println("Authentication error. Since pin number is incorrect");
        }

        try {
            atm.authenticateCustomer(cardId, newPinNumber);
            System.out.println("ATM Authentication Success");
            atm.quit();
        }
        catch (Exception e ) {
            System.out.println("ERROR!!! This code must NOT execute");
        }
        System.out.println();
    }

    @Test
    public void test2() {
        System.out.println("TEST #2");
        accountName = "Jack Brown";
        long accountId = bankBranch.createAccount(accountName);
        System.out.println("Account for " + accountName + " created. Id = " + accountId);
        long[] ns = bankBranch.openCard(accountId);
        long cardId = ns[0];
        System.out.println(accountName + " opened card. Card Id = " + cardId);
        long newPinNumber = ns[1];
        atm.authenticateCustomer(cardId, (int)newPinNumber);
        System.out.println("ATM Authentication Success");

        double amount = atm.viewAccount(AccountType.Saving);
        System.out.println("User " + accountName + " got amount from " + AccountType.Saving  + " account: " + amount);
        Assert.assertEquals(0.0, amount, 0.01);
        amount = atm.viewAccount(AccountType.Checking);
        System.out.println("User " + accountName + " got amount from " + AccountType.Checking  + " account: " + amount);
        Assert.assertEquals(0.0, amount, 0.01);

        double depositAmount = 500;
        atm.deposit(AccountType.Saving, depositAmount, false);
        System.out.println("User " + accountName + " made deposit to " + AccountType.Saving  + " account: " + depositAmount);
        amount = atm.viewAccount(AccountType.Saving);
        System.out.println("User " + accountName + " got amount from " + AccountType.Saving  + " account: " + amount);
        Assert.assertEquals(depositAmount, amount, 0.01);
        amount = atm.viewAccount(AccountType.Checking);
        System.out.println("User " + accountName + " got amount from " + AccountType.Checking  + " account: " + amount);
        Assert.assertEquals(0.0, amount, 0.01);

        double withdrawAmount = 200;
        atm.withdraw(AccountType.Saving, withdrawAmount, false);
        System.out.println("User " + accountName + " withdrew money from " + AccountType.Saving  + " account: " + withdrawAmount);
        amount = atm.viewAccount(AccountType.Saving);
        System.out.println("User " + accountName + " got amount from " + AccountType.Saving  + " account: " + amount);
        Assert.assertEquals(300.0, amount, 0.01);
        amount = atm.viewAccount(AccountType.Checking);
        System.out.println("User " + accountName + " got amount from " + AccountType.Checking  + " account: " + amount);
        Assert.assertEquals(0.0, amount, 0.01);

    }
}
