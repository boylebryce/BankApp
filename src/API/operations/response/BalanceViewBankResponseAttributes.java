package api.operations.response;

import api.operations.BalanceView;

public class BalanceViewBankResponseAttributes extends BankResponseAttributes<BalanceView> {
    private final double amount;

    public BalanceViewBankResponseAttributes(boolean isSuccessful, double amount) {
        super(isSuccessful);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}
