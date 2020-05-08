package api.operations.response;

import api.operations.LockAccount;

public class LockAccountBankResponseAttributes extends BankResponseAttributes<LockAccount> {
    public LockAccountBankResponseAttributes(boolean isSuccessful) {
        super(isSuccessful);
    }
}
