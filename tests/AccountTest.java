
import api.Account;
import api.Card;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class AccountTest {

    Account testAccount;

    @Before
    public void setUp() throws Exception {
        testAccount = new Account(null, 123456789, "John Smith");
        testAccount.setCheckingAmount(123.45);
        testAccount.setSavingAmount(543.21);

        Card card = new Card(1234567890, 1234);
        testAccount.addCard(card);


    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAccountId() {
        assertEquals(123456789, testAccount.getAccountId());
    }

    @Test
    public void getName() {
        assertEquals("John Smith", testAccount.getName());
    }

    @Test
    public void getSavingAmount() {
        assertEquals(543.21, testAccount.getSavingAmount(), .001);
    }

    @Test
    public void getCheckingAmount() {
        assertEquals(123.45, testAccount.getCheckingAmount(), .001);
    }

    @Test
    public void getCards() {
        ArrayList<Card> cards = testAccount.getCards();

        for (Card card : cards) {
            assertEquals(1234567890, card.getCardNumber());
            assertEquals(1234, card.getPinNumber());
        }
    }

    @Test
    public void isLocked() {
        assertFalse(testAccount.isLocked());
    }

    @Test
    public void setSavingAmount() {
        testAccount.setSavingAmount(555.55);
        assertEquals(555.55, testAccount.getSavingAmount(), .001);
    }

    @Test
    public void setCheckingAmount() {
        testAccount.setCheckingAmount(555.55);
        assertEquals(555.55, testAccount.getCheckingAmount(), .001);
    }

    @Test
    public void addCard() {
        testAccount.addCard(new Card(1234567890, 1234));
        ArrayList<Card> cards = testAccount.getCards();

        for (Card card : cards) {
            assertEquals(1234567890, card.getCardNumber());
            assertEquals(1234, card.getPinNumber());
        }
    }

    @Test
    public void setLocked() {
        testAccount.setLocked(true);
        assertTrue(testAccount.isLocked());
    }

    @Test
    public void removeCard() {
        testAccount.removeCard(new Card(1234567890, 1234));
        assertTrue(testAccount.getCards().isEmpty());
    }

    @Test
    public void toDataString() {
        String[] data = testAccount.toDataString().split(",");
        assertEquals("123456789", data[0]);
        assertEquals("John Smith", data[1]);
        assertEquals("543.21", data[2]);
        assertEquals("123.45", data[3]);
        assertEquals("1", data[4]);
        assertEquals("1234567890", data[5]);
        assertEquals("1234", data[6]);
        assertEquals("0", data[7]);
        assertEquals("false", data[8]);
    }
}