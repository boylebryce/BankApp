package api.operations.response;

import api.operations.BalanceView;
import api.operations.OpenCard;

public class OpenCardBankResponseAttributes extends BankResponseAttributes<OpenCard> {
    private final long cardNumber;

    public OpenCardBankResponseAttributes(boolean isSuccessful, long cardNumber) {
        super(isSuccessful);
        this.cardNumber = cardNumber;
    }

    public long getCardNumber() {
        return cardNumber;
    }
}
