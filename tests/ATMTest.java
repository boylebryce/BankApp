import impl.ATM;
import impl.Bank;
import impl.BankBranch;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ATMTest {
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

    @Test
    public void getId() {
    }

    @Test
    public void setBankBranch() {
    }

    @Test
    public void setATMMaintenancePolicy() {
    }

    @Test
    public void setMoneyLevel() {
    }

    @Test
    public void setInkLevel() {
    }

    @Test
    public void setPaperLevel() {
    }

    @Test
    public void getMoneyLevel() {
    }

    @Test
    public void getInkLevel() {
    }

    @Test
    public void getPaperLevel() {
    }

    @Test
    public void authenticateCustomer() {
    }

    @Test
    public void viewAccount() {
    }

    @Test
    public void depositCash() {
    }

    @Test
    public void depositCheck() {
    }

    @Test
    public void withdraw() {
    }

    @Test
    public void quit() {
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