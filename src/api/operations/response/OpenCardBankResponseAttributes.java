package api.operations.response;

import api.operations.OpenCard;

public class OpenCardBankResponseAttributes extends BankResponseAttributes<OpenCard> {
    private final long cardNumber;
    private final int pinNumber;

    public OpenCardBankResponseAttributes(boolean isSuccessful, long cardNumber, int pinNumber) {
        super(isSuccessful);
        this.cardNumber = cardNumber;
        this.pinNumber = pinNumber;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public int getPinNumber() {
        return pinNumber;
    }
}
