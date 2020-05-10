package api;

public class Card {
    private final long cardNumber;
    private int pinNumber;

    public Card(long cardNumber, int pinNumber) {
        this.cardNumber = cardNumber;
        this.pinNumber = pinNumber;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public boolean setPinNumber(int pinNumber) {
        if (this.pinNumber == pinNumber) {
            return false;
        }
        this.pinNumber = pinNumber;
        return true;
    }
}
