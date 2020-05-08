package api.operations.request;

import api.AccountType;
import api.operations.BalanceView;
import api.operations.CreateAccount;

public class CreateAccountBankRequestAttributes extends BankRequestAttributes<CreateAccount> {
    private final String name;

    public CreateAccountBankRequestAttributes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
