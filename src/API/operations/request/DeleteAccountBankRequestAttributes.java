package api.operations.request;

import api.operations.DeleteAccount;

public class DeleteAccountBankRequestAttributes extends BankRequestAttributes<DeleteAccount> {
    private final long accountId;

    public DeleteAccountBankRequestAttributes(long accountId) {
        this.accountId = accountId;
    }

    public long getAccountId() {
        return accountId;
    }
}
