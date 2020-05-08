package api.operations.request;

import api.AccountType;
import api.operations.BalanceView;
import api.operations.ChangePinNumber;

public class ChangePinNumberBankRequestAttributes extends BankRequestAttributes<ChangePinNumber> {
    private final long cardNumber;
    private final int pinNumber;

    public ChangePinNumberBankRequestAttributes(long cardNumber, int pinNumber) {
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
