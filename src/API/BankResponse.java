package api;

import api.operations.BankOperation;
import api.operations.BankRequestType;
import api.operations.response.BankResponseAttributes;

public class BankResponse<V extends BankRequestType,T extends BankResponseAttributes<V>> {
    private T bankResponseAttributes;

    public BankResponse(T bankResponseAttributes) {
        this.bankResponseAttributes = bankResponseAttributes;
    }

    public T getBankResponseAttributes() {
        return bankResponseAttributes;
    }
}
