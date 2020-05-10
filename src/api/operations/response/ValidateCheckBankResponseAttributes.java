package api.operations.response;

import api.operations.ValidateCheck;

public class ValidateCheckBankResponseAttributes extends BankResponseAttributes<ValidateCheck> {
    public ValidateCheckBankResponseAttributes(boolean isSuccessful) {
        super(isSuccessful);
    }
}