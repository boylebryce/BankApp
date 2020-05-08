package api.operations.response;

import api.operations.CredentialsValidation;
import api.operations.WithdrawMoney;

public class WithdrawMoneyBankResponseAttributes extends BankResponseAttributes<WithdrawMoney> {
    public WithdrawMoneyBankResponseAttributes(boolean isSuccessful) {
        super(isSuccessful);
    }
}
