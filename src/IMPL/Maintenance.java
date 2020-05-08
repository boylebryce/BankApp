package impl;

import api.*;
import api.operations.MaintainATM;
import api.operations.request.MaintainATMBankRequestAttributes;
import api.operations.response.MaintainATMBankResponseAttributes;

public class Maintenance implements IMaintenance {
    private ATMMaintenancePolicy atmMaintenancePolicy;

    public Maintenance(ATMMaintenancePolicy atmMaintenancePolicy) {
        this.atmMaintenancePolicy = atmMaintenancePolicy;
    }

    @Override
    public ATMMaintenancePolicy getATMMaintenancePolicy() {
        return atmMaintenancePolicy;
    }

    @Override
    public BankResponse<MaintainATM, MaintainATMBankResponseAttributes> respondMaintainATM(BankRequest<MaintainATM, MaintainATMBankRequestAttributes> bankRequest) {
        MaintainATMBankRequestAttributes requestAttributes = bankRequest.getBankRequestAttributes();
        IATM atm = requestAttributes.getAtm();
        switch (requestAttributes.getMaintainRequestType()) {
            case LowMoney:
                atm.setMoneyLevel(atmMaintenancePolicy.getMoneyAddAmount());
                break;
            case LowInk:
                atm.setInkLevel(atmMaintenancePolicy.getLowInkLevel());
                break;
            case LowPaper:
                atm.setPaperLevel(atmMaintenancePolicy.getLowPaperLevel());
                break;
        }

        MaintainATMBankResponseAttributes responseAttributes = new MaintainATMBankResponseAttributes(true);
        return new BankResponse<>(responseAttributes);
    }
}
