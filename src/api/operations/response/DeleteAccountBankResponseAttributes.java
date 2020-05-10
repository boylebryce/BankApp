package api.operations.response;

import api.operations.DeleteAccount;

public class DeleteAccountBankResponseAttributes extends BankResponseAttributes<DeleteAccount> {
    public DeleteAccountBankResponseAttributes(boolean isSuccessful) {
        super(isSuccessful);
    }
}
