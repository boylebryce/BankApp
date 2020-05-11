package impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BankBranchTest {
    Bank testBank;
    BankBranch testBranch;
    ATM testATM;
    long testAccountID;

    @Before
    public void setUp() throws Exception {
        testBank = new Bank("Test Bank");
        testBranch = new BankBranch("Test Branch");
        testBank.newBranch(testBranch);

        testATM = new ATM();
        testBranch.newATM(testATM);

        testAccountID = testBranch.createAccount("Test Name");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getBranchName() {
    }

    @Test
    public void getBank() {
    }

    @Test
    public void setBank() {
    }

    @Test
    public void setFraud() {
    }

    @Test
    public void setMaintenance() {
    }

    @Test
    public void newATM() {
    }

    @Test
    public void createAccount() {
    }

    @Test
    public void deleteAccount() {
    }

    @Test
    public void openCard() {
    }

    @Test
    public void closeCard() {
    }

    @Test
    public void changePinNumber() {
    }

    @Test
    public void getFraud() {
    }

    @Test
    public void getMaintenance() {
    }

    @Test
    public void respondCredentialValidation() {
    }

    @Test
    public void respondBalanceView() {
    }

    @Test
    public void respondDepositCash() {
    }

    @Test
    public void respondDepositCheck() {
    }

    @Test
    public void respondWithdrawMoney() {
    }

    @Test
    public void respondAlertAccount() {
    }

    @Test
    public void respondMaintainATM() {
    }

    @Test
    public void toDataString() {
        String[] data = testBranch.toDataString().split(",");
        assertEquals("Test Branch", data[0]);   // Branch name
        assertEquals("1", data[1]);             // Number of ATMs
        // data[2] is a randomly generated ATM ID
        assertEquals("1000000.0", data[3]);     // ATM 1 cash level
        assertEquals("2000", data[4]);          // ATM 1 ink level
        assertEquals("2000", data[5]);          // ATM 1 paper level
    }

    @Test
    public void testDataStringConstructor() {
        BankBranch newTestBranch = new BankBranch(testBranch.toDataString());
        String[] data = newTestBranch.toDataString().split(",");
        assertEquals("Test Branch", data[0]);   // Branch name
        assertEquals("1", data[1]);             // Number of ATMs
        // data[2] is a randomly generated ATM ID
        assertEquals("1000000.0", data[3]);     // ATM 1 cash level
        assertEquals("2000", data[4]);          // ATM 1 ink level
        assertEquals("2000", data[5]);          // ATM 1 paper level
    }
}