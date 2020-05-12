import api.*;
import impl.ATMTest;
import impl.BankBranchTest;
import impl.BankTest;
import impl.FraudTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountTest.class,
        CardTest.class,
        CheckTest.class,
        TransactionTest.class,
        ATMTest.class,
        BankBranchTest.class,
        BankTest.class,
        FraudTest.class,
        TestCases.class
})

public class TestSuite {
}
