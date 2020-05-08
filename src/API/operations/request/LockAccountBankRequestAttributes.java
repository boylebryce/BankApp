package api.operations.request;

import api.AccountType;
import api.operations.BalanceView;
import api.operations.LockAccount;

public class LockAccountBankRequestAttributes extends BankRequestAttributes<LockAccount> {
    private final long accountId;

    public LockAccountBankRequestAttributes(long accountId) {
        this.accountId = accountId;
    }

    public long getAccountId() {
        return accountId;
    }
}
