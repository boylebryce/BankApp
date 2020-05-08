package api;

import api.operations.MaintainATM;
import api.operations.request.MaintainATMBankRequestAttributes;
import api.operations.response.MaintainATMBankResponseAttributes;

public interface IMaintenance {
    ATMMaintenancePolicy getATMMaintenancePolicy();

    BankResponse<MaintainATM, MaintainATMBankResponseAttributes>
    respondMaintainATM(BankRequest<MaintainATM, MaintainATMBankRequestAttributes> bankRequest);
}
