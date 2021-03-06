package impl;

import api.*;
import api.operations.*;
import api.operations.request.*;
import api.operations.response.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static impl.StorageUtils.addDelimiter;

public class BankBranch implements IBankBranch {
    private final String name;
    private IBank bank;
    private IFraud fraud;
    private IMaintenance maintenance;

    private final List<IATM> atms;

    public BankBranch(String name) {
        this.name = name;
        this.atms = new ArrayList<>();
    }

    public List<IATM> getATMs() {
        return atms;
    }

    @Override
    public String getBranchName() {
        return name;
    }

    @Override
    public IBank getBank() {
        return bank;
    }

    @Override
    public void setBank(IBank bank) {
        this.bank = bank;
    }

    @Override
    public void setFraud(IFraud fraud) {
        this.fraud = fraud;
    }

    @Override
    public void setMaintenance(IMaintenance maintenance) {
        this.maintenance = maintenance;
    }

    @Override
    public void addATM(IATM atm) {
        atms.add(atm);
        atm.setBankBranch(this);
        atm.setATMMaintenancePolicy(maintenance.getATMMaintenancePolicy());
    }

    @Override
    public long createAccount(String name) {
        CreateAccountBankRequestAttributes requestAttributes = new CreateAccountBankRequestAttributes(name);
        BankResponse<CreateAccount, CreateAccountBankResponseAttributes> response = bank.respondCreateAccount(new BankRequest<>(requestAttributes));
        return response.getBankResponseAttributes().getAccount();
    }

    @Override
    public void deleteAccount(long accountId) {
        DeleteAccountBankRequestAttributes requestAttributes = new DeleteAccountBankRequestAttributes(accountId);
        DeleteAccountBankResponseAttributes responseAttributes =
                bank.respondDeleteAccount(new BankRequest<>(requestAttributes)).getBankResponseAttributes();
        if (!responseAttributes.isSuccessful()) {
            throw new IllegalArgumentException("No account with given ID: " + accountId);
        }
    }

    @Override
    public long[] openCard(long accountId) {
        OpenCardBankRequestAttributes requestAttributes = new OpenCardBankRequestAttributes(accountId);
        BankResponse<OpenCard, OpenCardBankResponseAttributes> response = bank.respondOpenCard(new BankRequest<>(requestAttributes));
        OpenCardBankResponseAttributes responseAttributes = response.getBankResponseAttributes();
        if (!responseAttributes.isSuccessful()) {
            throw new IllegalArgumentException("No account with given ID: " + accountId);
        }
        return new long[]{response.getBankResponseAttributes().getCardNumber(), response.getBankResponseAttributes().getPinNumber()};
    }

    @Override
    public void closeCard(long cardNumber) {
        CloseCardBankRequestAttributes requestAttributes = new CloseCardBankRequestAttributes(cardNumber);
        CloseCardBankResponseAttributes responseAttributes =
                bank.respondCloseCard(new BankRequest<>(requestAttributes)).getBankResponseAttributes();
        if (!responseAttributes.isSuccessful()) {
            throw new IllegalArgumentException("No card with given number: " + cardNumber);
        }
    }

    @Override
    public void changePinNumber(long cardNumber, int pinNumber) {
        ChangePinNumberBankRequestAttributes requestAttributes = new ChangePinNumberBankRequestAttributes(cardNumber, pinNumber);
        ChangePinNumberBankResponseAttributes responseAttributes =
                bank.respondChangePinNumber(new BankRequest<>(requestAttributes)).getBankResponseAttributes();
        if (!responseAttributes.isSuccessful()) {
            if (responseAttributes.getChangePinFailReason() == ChangePinNumberBankResponseAttributes.ChangePinFailReason.NoSuchCard) {
                throw new IllegalArgumentException(responseAttributes.getChangePinFailReason().toString() + cardNumber);
            }
            else {
                throw new IllegalArgumentException(responseAttributes.getChangePinFailReason().toString() + pinNumber);
            }
        }
    }

    @Override
    public IFraud getFraud() {
        return fraud;
    }

    @Override
    public IMaintenance getMaintenance() {
        return maintenance;
    }

    @Override
    public BankResponse<CredentialsValidation, CredentialsValidationBankResponseAttributes> respondCredentialValidation(BankRequest<CredentialsValidation, CredentialsValidationBankRequestAttributes> bankRequest) {
        return bank.respondCredentialValidation(bankRequest);
    }

    @Override
    public BankResponse<BalanceView, BalanceViewBankResponseAttributes> respondBalanceView(BankRequest<BalanceView, BalanceViewBankRequestAttributes> bankRequest) {
        return bank.respondBalanceView(bankRequest);
    }

    @Override
    public BankResponse<DepositCash, DepositCashBankResponseAttributes> respondDepositCash(BankRequest<DepositCash, DepositCashBankRequestAttributes> bankRequest) {
        return bank.respondDepositCash(bankRequest);
    }

    @Override
    public BankResponse<DepositCheck, DepositCheckBankResponseAttributes> respondDepositCheck(BankRequest<DepositCheck, DepositCheckBankRequestAttributes> bankRequest) {
        return bank.respondDepositCheck(bankRequest);
    }

    @Override
    public BankResponse<WithdrawMoney, WithdrawMoneyBankResponseAttributes> respondWithdrawMoney(BankRequest<WithdrawMoney, WithdrawMoneyBankRequestAttributes> bankRequest) {
        return bank.respondWithdrawMoney(bankRequest);
    }

    @Override
    public BankResponse<AlertAccount, AlertAccountBankResponseAttributes> respondAlertAccount(BankRequest<AlertAccount, AlertAccountBankRequestAttributes> bankRequest) {
        return fraud.respondAlertAccount(bankRequest);
    }

    @Override
    public BankResponse<MaintainATM, MaintainATMBankResponseAttributes> respondMaintainATM(BankRequest<MaintainATM, MaintainATMBankRequestAttributes> bankRequest) {
        return maintenance.respondMaintainATM(bankRequest);
    }

    public void loadATMsFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("data/atms/" + this.name + ".txt"));

        String line = reader.readLine();
        while (line != null) {
            this.addATM(new ATM(line, this));
            line = reader.readLine();
        }


        reader.close();
    }

    public void saveATMsToFile() throws IOException {
        String filename = "data/atms/" + this.name + ".txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("");

        for (IATM atm : atms) {
            writer.append(atm.toDataString() + "\n");
        }

        writer.close();
    }

    public String toDataString() {
        return name;
    }
}
