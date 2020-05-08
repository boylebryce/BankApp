package api.operations.response;

import api.operations.BalanceView;
import api.operations.CloseCard;

public class CloseCardBankResponseAttributes extends BankResponseAttributes<CloseCard> {
    public CloseCardBankResponseAttributes(boolean isSuccessful) {
        super(isSuccessful);
    }
}
