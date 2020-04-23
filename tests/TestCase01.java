import com.company.Customer;
import com.company.ATM;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCase01 {

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
    }

    @Test
    public void accessATMWithValidCredentials() {
        assertEquals(ATM.successfulValidation(), testCustomer.goToATM(testATM));
    }
}