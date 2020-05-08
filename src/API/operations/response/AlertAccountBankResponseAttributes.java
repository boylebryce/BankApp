package api.operations.response;

import api.operations.AlertAccount;

public class AlertAccountBankResponseAttributes extends BankResponseAttributes<AlertAccount> {
    private AlertAccountStatus status;

    public AlertAccountBankResponseAttributes(boolean isSuccessful, AlertAccountStatus status) {
        super(isSuccessful);
        this.status = status;
    }

    public AlertAccountStatus getStatus() {
        return status;
    }

    public enum AlertAccountStatus {
        Flagged,
        Locked;
    }
}
