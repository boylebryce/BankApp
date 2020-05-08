package api.operations.response;

import api.operations.BalanceView;
import api.operations.ChangePinNumber;

public class ChangePinNumberBankResponseAttributes extends BankResponseAttributes<ChangePinNumber> {
    public ChangePinNumberBankResponseAttributes(boolean isSuccessful) {
        super(isSuccessful);
    }
}
