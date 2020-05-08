package api.operations.request;

import api.operations.CredentialsValidation;

public class CredentialsValidationBankRequestAttributes extends BankRequestAttributes<CredentialsValidation> {
    private final long cardNumber;
    private final int pinNumber;

    public CredentialsValidationBankRequestAttributes(long cardNumber, int pinNumber) {
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
