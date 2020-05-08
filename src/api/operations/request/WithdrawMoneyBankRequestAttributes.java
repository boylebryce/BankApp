package api.operations.request;

import api.AccountType;
import api.operations.CredentialsValidation;
import api.operations.WithdrawMoney;
import api.operations.response.BankResponseAttributes;

public class WithdrawMoneyBankRequestAttributes extends BankRequestAttributes<WithdrawMoney> {
    private final long accountId;
    private final double amount;
    private final AccountType accountType;

    public WithdrawMoneyBankRequestAttributes(long accountId, double amount, AccountType accountType) {
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
