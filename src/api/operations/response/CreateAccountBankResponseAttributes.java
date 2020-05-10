package api.operations.response;

import api.operations.BalanceView;
import api.operations.CreateAccount;

public class CreateAccountBankResponseAttributes extends BankResponseAttributes<CreateAccount> {
    private final long account;

    public CreateAccountBankResponseAttributes(boolean isSuccessful, long account) {
        super(isSuccessful);
        this.account = account;
    }

    public long getAccount() {
        return account;
    }
}
