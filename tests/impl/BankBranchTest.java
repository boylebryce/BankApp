package impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BankBranchTest {
    Bank testBank;
    BankBranch testBranch;
    ATM testATM;
    long testAccountID;

    @Before
    public void setUp() throws Exception {
        testBank = new Bank("Test Bank");
        testBranch = new BankBranch("Test Branch");
        testBank.addBranch(testBranch);

        testATM = new ATM();
        testBranch.addATM(testATM);

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
}