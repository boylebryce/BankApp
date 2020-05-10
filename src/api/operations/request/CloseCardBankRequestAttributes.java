package api.operations.request;

import api.AccountType;
import api.operations.BalanceView;
import api.operations.CloseCard;

public class CloseCardBankRequestAttributes extends BankRequestAttributes<CloseCard> {
    private final long cardNumber;

    public CloseCardBankRequestAttributes(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public long getCardNumber() {
        return cardNumber;
    }
}
