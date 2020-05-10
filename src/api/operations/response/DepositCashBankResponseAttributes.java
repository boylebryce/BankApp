package api.operations.response;

import api.operations.DepositCash;

public class DepositCashBankResponseAttributes extends BankResponseAttributes<DepositCash> {
    public DepositCashBankResponseAttributes(boolean isSuccessful) {
        super(isSuccessful);
    }
}
