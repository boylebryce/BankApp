package api;

import api.operations.BankOperation;
import api.operations.BankRequestType;
import api.operations.request.BankRequestAttributes;

public class BankRequest<V extends BankRequestType,T extends BankRequestAttributes<V>> {
    private final T bankRequestAttributes;

    public BankRequest(T bankRequestAttributes) {
        this.bankRequestAttributes = bankRequestAttributes;
    }

    public T getBankRequestAttributes() {
        return bankRequestAttributes;
    }
}
