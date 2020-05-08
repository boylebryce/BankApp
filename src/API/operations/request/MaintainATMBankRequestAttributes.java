package api.operations.request;

import api.IATM;
import api.operations.AlertAccount;
import api.operations.MaintainATM;

public class MaintainATMBankRequestAttributes extends BankRequestAttributes<MaintainATM> {
    private final IATM atm;
    private MaintainRequestType maintainRequestType;

    public MaintainATMBankRequestAttributes(IATM atm, MaintainRequestType maintainRequestType) {
        this.atm = atm;
        this.maintainRequestType = maintainRequestType;
    }

    public IATM getAtm() {
        return atm;
    }

    public MaintainRequestType getMaintainRequestType() {
        return maintainRequestType;
    }

    public enum MaintainRequestType {
        LowMoney,
        LowInk,
        LowPaper;
    }
}
