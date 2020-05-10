package api.operations.request;

import api.AccountType;
import api.operations.DepositCash;

public class DepositCashBankRequestAttributes extends BankRequestAttributes<DepositCash> {
    private final long accountId;
    private final double amount;
    private final AccountType accountType;

    public DepositCashBankRequestAttributes(long accountId, double amount, AccountType accountType) {
        this.accountId = accountId;
        this.amount = amount;
        this.accountType = accountType;
    }

    public long getAccountId() {
        return accountId;
    }

    public double getAmount() {
        return amount;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
