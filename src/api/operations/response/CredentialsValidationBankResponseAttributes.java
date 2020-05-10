package api.operations.response;

import api.operations.CredentialsValidation;

public class CredentialsValidationBankResponseAttributes extends BankResponseAttributes<CredentialsValidation> {
    private final boolean isValid;
    private final long accountId;
    private final ValidationFailReason reason;

    public CredentialsValidationBankResponseAttributes(boolean isSuccessful, boolean isValid, long accountId, ValidationFailReason reason) {
        super(isSuccessful);
        this.isValid = isValid;
        this.accountId = accountId;
        this.reason = reason;
    }

    public boolean isValid() {
        return isValid;
    }

    public long getAccountId() {
        return accountId;
    }

    public ValidationFailReason getReason() {
        return reason;
    }

    public enum ValidationFailReason {
        InvalidCardNumber,
        InvalidPinNumber,
        AccountLocked;
    }
}
