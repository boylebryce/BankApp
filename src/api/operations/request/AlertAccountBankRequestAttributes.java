package api.operations.request;

import api.AccountType;
import api.operations.AlertAccount;
import api.operations.BalanceView;

public class AlertAccountBankRequestAttributes extends BankRequestAttributes<AlertAccount> {
    private final long accountId;

    public AlertAccountBankRequestAttributes(long accountId) {
        this.accountId = accountId;
    }

    public long getAccountId() {
        return accountId;
    }
}
