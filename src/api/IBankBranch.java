package api;

import api.operations.AlertAccount;
import api.operations.MaintainATM;
import api.operations.request.AlertAccountBankRequestAttributes;
import api.operations.request.MaintainATMBankRequestAttributes;
import api.operations.response.AlertAccountBankResponseAttributes;
import api.operations.response.MaintainATMBankResponseAttributes;

public interface IBankBranch extends IBankRequestServer, IDepartmentClient {
    String getBranchName();
    IBank getBank();

    void setBank(IBank bank);
    void setFraud(IFraud fraud);
    void setMaintenance(IMaintenance maintenance);

    void newATM(IATM atm);

    long createAccount(String name);
    void deleteAccount(long accountId);
    long openCard(long accountId);
    void closeCard(long cardNumber);
    void changePinNumber(long cardNumber, int pinNumber);

    BankResponse<AlertAccount, AlertAccountBankResponseAttributes>
    respondAlertAccount(BankRequest<AlertAccount, AlertAccountBankRequestAttributes> bankRequest);

    BankResponse<MaintainATM, MaintainATMBankResponseAttributes>
    respondMaintainATM(BankRequest<MaintainATM, MaintainATMBankRequestAttributes> bankRequest);
}
