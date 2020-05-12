import api.AccountType;
import api.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TransactionTest {

    Transaction testTransaction;

    @Before
    public void setUp() throws Exception {
        long transactionId = 1234567890;
        long accountID = 123456;
        AccountType accountType = AccountType.Checking;
        double amount = 123.45;
        Transaction.TransactionType transactionType = Transaction.TransactionType.Withdraw;
        testTransaction = new Transaction(transactionId, accountID, accountType, amount, transactionType);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void toDataString() {
        String[] data = testTransaction.toDataString().split(",");
        assertEquals("1234567890", data[0]);
        assertEquals("123456", data[2]);
        assertEquals("Checking", data[3]);
        assertEquals("123.45", data[4]);
        assertEquals("Withdraw", data[5]);
    }
}