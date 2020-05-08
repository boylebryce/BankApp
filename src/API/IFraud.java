package api;

import api.operations.AlertAccount;
import api.operations.request.AlertAccountBankRequestAttributes;
import api.operations.response.AlertAccountBankResponseAttributes;

public interface IFraud {
    IBank getBank();

    BankResponse<AlertAccount, AlertAccountBankResponseAttributes>
    respondAlertAccount(BankRequest<AlertAccount, AlertAccountBankRequestAttributes> bankRequest);
}
