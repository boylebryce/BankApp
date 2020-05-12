package api;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {
    Card card;

    @Before
    public void setUp() throws Exception {
        card = new Card(1234567890L, 1234);
    }

    @Test
    public void getCardNumber() {
        assertEquals(1234567890L, card.getCardNumber());
    }

    @Test
    public void getPinNumber() {
        assertEquals(1234, card.getPinNumber());
    }

    @Test
    public void setPinNumber() {
        card.setPinNumber(4321);
        assertEquals(4321, card.getPinNumber());
    }

    @Test
    public void toDataString() {
        String[] data = card.toDataString().split(",");
        assertEquals("1234567890", data[0]);
        assertEquals("1234", data[1]);
    }
}