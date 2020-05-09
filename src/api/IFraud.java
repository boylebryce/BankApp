package api;

import api.operations.AlertAccount;
import api.operations.ValidateCheck;
import api.operations.request.AlertAccountBankRequestAttributes;
import api.operations.request.ValidateCheckBankRequestAttributes;
import api.operations.response.AlertAccountBankResponseAttributes;
import api.operations.response.ValidateCheckBankResponseAttributes;

public interface IFraud {
    IBank getBank();

    BankResponse<AlertAccount, AlertAccountBankResponseAttributes>
    respondAlertAccount(BankRequest<AlertAccount, AlertAccountBankRequestAttributes> bankRequest);

    BankResponse<ValidateCheck, ValidateCheckBankResponseAttributes>
    respondValidateCheck(BankRequest<ValidateCheck, ValidateCheckBankRequestAttributes> bankRequest);
}
