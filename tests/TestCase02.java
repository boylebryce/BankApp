import com.company.Customer;
import com.company.ATM;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCase02 {

    Customer testCustomer;
    ATM testATM;

    @Before
    public void setUp() throws Exception {
        String name = "John Smith";
        String phoneNumber = "123-456-7890";
        String emailAddress = "johnsmith@gmail.com";
        String address = "123 Main Street";

        testCustomer = new Customer(name, phoneNumber, emailAddress, address);

        testATM = new ATM();

        // Validates testCustomer at testATM, setting ATM state to use
        // testCustomer for any actions taken during this session
        testCustomer.goToATM(testATM);
    }

    @Test
    public void makeValidDepositAtATM() {
        int accountNumber = 0;
        int depositAmount = 100;
        testATM.deposit(accountNumber, depositAmount);

    }
}