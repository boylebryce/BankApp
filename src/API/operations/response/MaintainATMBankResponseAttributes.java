package api.operations.response;

import api.operations.BalanceView;
import api.operations.MaintainATM;

public class MaintainATMBankResponseAttributes extends BankResponseAttributes<MaintainATM> {
    public MaintainATMBankResponseAttributes(boolean isSuccessful) {
        super(isSuccessful);
    }
}
