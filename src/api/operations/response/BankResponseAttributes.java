package api.operations.response;

import api.operations.BankRequestType;

public abstract class BankResponseAttributes<T extends BankRequestType> {
    private boolean isSuccessful;

    public BankResponseAttributes(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }
}
