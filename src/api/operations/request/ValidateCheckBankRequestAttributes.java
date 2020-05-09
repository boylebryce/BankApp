package api.operations.request;

import api.Check;
import api.operations.ValidateCheck;

public class ValidateCheckBankRequestAttributes extends BankRequestAttributes<ValidateCheck> {
    private final long accountId;
    private final Check check;


    public ValidateCheckBankRequestAttributes(long accountId, Check check) {
        this.accountId = accountId;
        this.check = check;
    }

    public long getAccountId() {
        return accountId;
    }

    public Check getCheck() {
        return check;
    }
}
