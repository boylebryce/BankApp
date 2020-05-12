package api;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class CheckTest {
    Check check;
    Date date;

    @Before
    public void setUp() throws Exception {
        date = new Date();

        check = new Check(123.45, 1234567890, 987654321, 123, date);
    }

    @Test
    public void getAmount() {
        assertEquals(123.45, check.getAmount(), .001);
    }

    @Test
    public void setAmount() {
        check.setAmount(543.21);
        assertEquals(543.21, check.getAmount(), .001);
    }

    @Test
    public void getRoutingNumber() {
        assertEquals(1234567890, check.getRoutingNumber());
    }

    @Test
    public void setRoutingNumber() {
        check.setRoutingNumber(9876543210L);
        assertEquals(9876543210L, check.getRoutingNumber());
    }

    @Test
    public void getAccountNumber() {
        assertEquals(987654321, check.getAccountNumber());
    }

    @Test
    public void setAccountNumber() {
        check.setAccountNumber(1234567890);
        assertEquals(1234567890, check.getAccountNumber());
    }

    @Test
    public void getCheckNumber() {
        assertEquals(123, check.getCheckNumber());
    }

    @Test
    public void setCheckNumber() {
        check.setCheckNumber(321);
        assertEquals(321, check.getCheckNumber());
    }

    @Test
    public void getCheckDate() {
        assertEquals(date, check.getCheckDate());
    }

    @Test
    public void setCheckDate() {
        Date newDate = new Date(date.getTime() - 1000000); // ensures new date is different from constructor date
        check.setCheckDate(newDate);
        assertEquals(newDate, check.getCheckDate());
    }

    @Test
    public void toDataString() {
        String[] data = check.toDataString().split(",");
        assertEquals("123.45", data[0]);
        assertEquals("1234567890", data[1]);
        assertEquals("987654321", data[2]);
        assertEquals("123", data[3]);
        assertEquals(date.getTime(), Long.parseLong(data[4]));
    }
}