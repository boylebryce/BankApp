package impl;

import api.BankRequest;
import api.BankResponse;
import api.IBank;
import api.IFraud;
import api.operations.AlertAccount;
import api.operations.request.AlertAccountBankRequestAttributes;
import api.operations.request.LockAccountBankRequestAttributes;
import api.operations.response.AlertAccountBankResponseAttributes;

import java.util.HashSet;
import java.util.Set;

public class Fraud implements IFraud {
    private IBank bank;
    private Set<Long> flaggedAccounts;

    public Fraud(IBank bank) {
        this.bank = bank;
        this.flaggedAccounts = new HashSet<>();
    }

    @Override
    public IBank getBank() {
        return bank;
    }

    @Override
    public BankResponse<AlertAccount, AlertAccountBankResponseAttributes> respondAlertAccount(BankRequest<AlertAccount, AlertAccountBankRequestAttributes> bankRequest) {
        AlertAccountBankRequestAttributes requestAttributes = bankRequest.getBankRequestAttributes();
        long accountId = requestAttributes.getAccountId();
        AlertAccountBankResponseAttributes.AlertAccountStatus status = null;

        // If the account is not flagged, flag the account for potentially fraudulent activity.
        if (!flaggedAccounts.contains(accountId)) {
            flaggedAccounts.add(accountId);
            status = AlertAccountBankResponseAttributes.AlertAccountStatus.Flagged;
        }

        // If the account is already flagged, lock the account.
        else {
            LockAccountBankRequestAttributes lockAccountBankRequestAttributes = new LockAccountBankRequestAttributes(requestAttributes.getAccountId());
            bank.respondLockAccount(new BankRequest<>(lockAccountBankRequestAttributes));
            status = AlertAccountBankResponseAttributes.AlertAccountStatus.Locked;
        }

        AlertAccountBankResponseAttributes responseAttributes = new AlertAccountBankResponseAttributes(true, status);
        return new BankResponse<>(responseAttributes);
    }
}
