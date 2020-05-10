package api.operations.response;

import api.operations.DepositCheck;

public class DepositCheckBankResponseAttributes extends BankResponseAttributes<DepositCheck> {
    public DepositCheckBankResponseAttributes(boolean isSuccessful) {
        super(isSuccessful);
    }
}