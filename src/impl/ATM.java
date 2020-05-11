package impl;

import api.*;
import api.operations.*;
import api.operations.request.*;
import api.operations.response.*;

import java.util.HashSet;
import java.util.Set;

import static impl.StorageUtils.addDelimiter;

public class ATM implements IATM {
    private static final Set<Long> ATM_IDS = new HashSet<>();

    private long id;

    private IBankBranch bankBranch;
    private ATMMaintenancePolicy atmMaintenancePolicy;

    private double moneyLevel;
    private int inkLevel;
    private int paperLevel;

    private ATMState state;
    private long currentAccountId;

    public ATM() {
        this.id = IDUtils.generateID(ATM_IDS, 10, true);
        this.state = ATMState.Idle;
        this.currentAccountId = -1;

        this.moneyLevel = ATMMaintenancePolicy.MaxMoneyAmount;
        this.paperLevel = ATMMaintenancePolicy.MaxPaperAmount;
        this.inkLevel = ATMMaintenancePolicy.MaxInkAmount;
    }

    public ATM(String input) {
        String[] data = input.split(",");

        this.id = Long.parseLong(data[0]);
        this.moneyLevel = Double.parseDouble(data[1]);
        this.inkLevel = Integer.parseInt(data[2]);
        this.paperLevel = Integer.parseInt(data[3]);

        this.state = ATMState.Idle;
        this.currentAccountId = -1;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setBankBranch(IBankBranch bankBranch) {
        this.bankBranch = bankBranch;
    }

    @Override
    public void setATMMaintenancePolicy(ATMMaintenancePolicy atmMaintenancePolicy) {
        this.atmMaintenancePolicy = atmMaintenancePolicy;
    }

    @Override
    public void setMoneyLevel(double money) {
        moneyLevel = money;
    }

    @Override
    public void setInkLevel(int ink) {
        inkLevel = ink;
    }

    @Override
    public void setPaperLevel(int paper) {
        paperLevel = paper;
    }

    @Override
    public double getMoneyLevel() {
        return moneyLevel;
    }

    @Override
    public int getInkLevel() {
        return inkLevel;
    }

    @Override
    public int getPaperLevel() {
        return paperLevel;
    }


    @Override
    public void authenticateCustomer(long cardNumber, int pinNumber) {
        state = ATMState.InUse;
        CredentialsValidationBankRequestAttributes requestAttributes = new CredentialsValidationBankRequestAttributes(cardNumber, pinNumber);
        BankResponse<CredentialsValidation, CredentialsValidationBankResponseAttributes> bankResponse = bankBranch.respondCredentialValidation(new BankRequest<>(requestAttributes));
        CredentialsValidationBankResponseAttributes responseAttributes = bankResponse.getBankResponseAttributes();
        if (responseAttributes.isValid()) {
            currentAccountId = responseAttributes.getAccountId();
        }
        else {
            try {
                switch (responseAttributes.getReason()) {
                    case InvalidCardNumber:
                        throw new IllegalArgumentException("Wrong card number");
                    case AccountLocked:
                        throw new IllegalArgumentException("Account locked");
                    case InvalidPinNumber:
                        AlertAccountBankRequestAttributes alertAccountBankRequestAttributes = new AlertAccountBankRequestAttributes(responseAttributes.getAccountId());
                        BankResponse<AlertAccount, AlertAccountBankResponseAttributes> response = bankBranch.respondAlertAccount(new BankRequest<>(alertAccountBankRequestAttributes));
                        AlertAccountBankResponseAttributes alertAccountBankResponseAttributes = response.getBankResponseAttributes();
                        if (alertAccountBankResponseAttributes.getStatus() == AlertAccountBankResponseAttributes.AlertAccountStatus.Flagged) {
                            throw new IllegalArgumentException("Wrong pin number. Please, try again");
                        }
                        else {
                            throw new IllegalArgumentException("Wrong pin number. Account is locked");
                        }
                }
            } finally {
                quit();
            }
        }
    }

    @Override
    public double viewAccount(AccountType accountType) {
        BalanceViewBankRequestAttributes requestAttributes = new BalanceViewBankRequestAttributes(currentAccountId, accountType);
        BankResponse<BalanceView, BalanceViewBankResponseAttributes> bankResponse = bankBranch.respondBalanceView(new BankRequest<>(requestAttributes));
        BalanceViewBankResponseAttributes responseAttributes = bankResponse.getBankResponseAttributes();
        return responseAttributes.getAmount();
    }

    @Override
    public void depositCash(AccountType accountType, double amount, boolean printReceipt) {
        DepositCashBankRequestAttributes requestAttributes = new DepositCashBankRequestAttributes(currentAccountId, amount, accountType);
        BankResponse<DepositCash, DepositCashBankResponseAttributes> bankResponse = bankBranch.respondDepositCash(new BankRequest<>(requestAttributes));
        DepositCashBankResponseAttributes responseAttributes = bankResponse.getBankResponseAttributes();
        moneyLevel += amount;
        if (printReceipt) {
            printingReceipt();
        }
    }


    // Returns true if check successfully deposited, else false if check is not successfully deposited (failed fraud validation)
    @Override
    public boolean depositCheck(AccountType accountType, Check check, boolean printReceipt) {
        DepositCheckBankRequestAttributes requestAttributes = new DepositCheckBankRequestAttributes(currentAccountId, check, accountType);
        BankResponse<DepositCheck, DepositCheckBankResponseAttributes> bankResponse = bankBranch.respondDepositCheck(new BankRequest<>(requestAttributes));
        DepositCheckBankResponseAttributes responseAttributes = bankResponse.getBankResponseAttributes();

        if (responseAttributes.isSuccessful() && printReceipt) {
            printingReceipt();
        }

        return responseAttributes.isSuccessful();
    }

    @Override
    public void withdraw(AccountType accountType, double amount, boolean printReceipt) {
        WithdrawMoneyBankRequestAttributes requestAttributes = new WithdrawMoneyBankRequestAttributes(currentAccountId, amount, accountType);
        BankResponse<WithdrawMoney, WithdrawMoneyBankResponseAttributes> bankResponse = bankBranch.respondWithdrawMoney(new BankRequest<>(requestAttributes));
        WithdrawMoneyBankResponseAttributes responseAttributes = bankResponse.getBankResponseAttributes();
        if (!responseAttributes.isSuccessful()) {
            throw new IllegalArgumentException("Not enough money");
        }
        moneyLevel -= amount;
        if (moneyLevel < atmMaintenancePolicy.getLowMoneyLevel()) {
            MaintainATMBankRequestAttributes maintainATMBankRequestAttributes = new MaintainATMBankRequestAttributes(this, MaintainATMBankRequestAttributes.MaintainRequestType.LowMoney);
            bankBranch.respondMaintainATM(new BankRequest<>(maintainATMBankRequestAttributes));
        }
        if (printReceipt) {
            printingReceipt();
        }
    }

    @Override
    public void quit() {
        this.state = ATMState.Idle;
        currentAccountId = -1;
    }

    private void printingReceipt() {
        inkLevel-=500;
        paperLevel--;
        if (inkLevel < atmMaintenancePolicy.getLowInkLevel()) {
            MaintainATMBankRequestAttributes requestAttributes = new MaintainATMBankRequestAttributes(this, MaintainATMBankRequestAttributes.MaintainRequestType.LowInk);
            bankBranch.respondMaintainATM(new BankRequest<>(requestAttributes));
        }
        if (paperLevel < atmMaintenancePolicy.getLowPaperLevel()) {
            MaintainATMBankRequestAttributes requestAttributes = new MaintainATMBankRequestAttributes(this, MaintainATMBankRequestAttributes.MaintainRequestType.LowPaper);
            bankBranch.respondMaintainATM(new BankRequest<>(requestAttributes));
        }
    }

    public String toDataString() {
        String output = "";

        output += addDelimiter(String.valueOf(id));
        output += addDelimiter(String.valueOf(moneyLevel));
        output += addDelimiter(String.valueOf(inkLevel));
        output += addDelimiter(String.valueOf(paperLevel));

        return output;
    }

    enum ATMState {
        Idle,
        InUse;
    }
}
