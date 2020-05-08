package api.operations.response;

import api.operations.CredentialsValidation;
import api.operations.DepositMoney;

public class DepositMoneyBankResponseAttributes extends BankResponseAttributes<DepositMoney> {
    public DepositMoneyBankResponseAttributes(boolean isSuccessful) {
        super(isSuccessful);
    }
}
