package impl;

import api.*;
import api.operations.AlertAccount;
import api.operations.ValidateCheck;
import api.operations.request.AlertAccountBankRequestAttributes;
import api.operations.request.LockAccountBankRequestAttributes;
import api.operations.request.ValidateCheckBankRequestAttributes;
import api.operations.response.AlertAccountBankResponseAttributes;
import api.operations.response.ValidateCheckBankResponseAttributes;

import java.util.HashSet;
import java.util.Set;

public class Fraud implements IFraud {
    private IBank bank;
    private Set<Long> flaggedAccounts;
    private Set<Check> depositedChecks;

    public Fraud(IBank bank) {
        this.bank = bank;
        this.flaggedAccounts = new HashSet<>();
        this.depositedChecks = new HashSet<>();
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

    @Override
    public BankResponse<ValidateCheck, ValidateCheckBankResponseAttributes> respondValidateCheck(BankRequest<ValidateCheck, ValidateCheckBankRequestAttributes> bankRequest) {
        ValidateCheckBankRequestAttributes requestAttributes = bankRequest.getBankRequestAttributes();
        long accountId = requestAttributes.getAccountId();
        Check check = requestAttributes.getCheck();
        boolean valid = depositedChecks.contains(check);

        ValidateCheckBankResponseAttributes responseAttributes = new ValidateCheckBankResponseAttributes(valid);
        return new BankResponse<>(responseAttributes);
    }
}
