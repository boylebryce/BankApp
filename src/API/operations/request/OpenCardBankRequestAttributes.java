package api.operations.request;

import api.AccountType;
import api.operations.BalanceView;
import api.operations.OpenCard;

public class OpenCardBankRequestAttributes extends BankRequestAttributes<OpenCard> {
    private final long accountId;

    public OpenCardBankRequestAttributes(long accountId) {
        this.accountId = accountId;
    }

    public long getAccountId() {
        return accountId;
    }
}
