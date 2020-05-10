package api.operations.request;

import api.AccountType;
import api.Check;
import api.operations.DepositCheck;

public class DepositCheckBankRequestAttributes extends BankRequestAttributes<DepositCheck> {
    private final long accountId;
    private final Check check;
    private final AccountType accountType;

    public DepositCheckBankRequestAttributes(long accountId, Check check, AccountType accountType) {
        this.accountId = accountId;
        this.check = check;
        this.accountType = accountType;
    }

    public long getAccountId() {
        return accountId;
    }

    public Check getCheck() {
        return check;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}