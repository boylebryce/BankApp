package api.operations.request;

import api.AccountType;
import api.operations.BalanceView;
import api.operations.CredentialsValidation;
import api.operations.response.BankResponseAttributes;

public class BalanceViewBankRequestAttributes extends BankRequestAttributes<BalanceView> {
    private final long accountId;
    private final AccountType accountType;

    public BalanceViewBankRequestAttributes(long accountId, AccountType accountType) {
        this.accountId = accountId;
        this.accountType = accountType;
    }

    public long getAccountId() {
        return accountId;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
