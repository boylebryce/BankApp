package impl;

import api.*;
import api.operations.*;
import api.operations.request.*;
import api.operations.response.*;

import java.util.*;

public class Bank implements IBank {
    private static final Set<Long> CARD_NUMBERS = new HashSet<>();
    private static final Set<Long> ACCOUNT_IDS = new HashSet<>();

    private final String name;
    private final Collection<IBankBranch> branches;
    private final IFraud fraud;
    private final IMaintenance maintenance;

    private final Collection<Account> accounts;
    private final Collection<Transaction> transactions;

    private long accountCounter;
    private long transactionCounter;

    public Bank(String name) {
        this.name = name;
        this.branches = new ArrayList<>();
        this.fraud = new Fraud(this);
        ATMMaintenancePolicy atmMaintenancePolicy = new ATMMaintenancePolicy();
        this.maintenance = new Maintenance(atmMaintenancePolicy);
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();

        this.transactionCounter = 0;
    }


    @Override
    public String getBankName() {
        return name;
    }

    @Override
    public void newBranch(IBankBranch bankBranch) {
        bankBranch.setBank(this);
        bankBranch.setFraud(fraud);
        bankBranch.setMaintenance(maintenance);
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
        CredentialsValidationBankRequestAttributes requestAttributes = bankRequest.getBankRequestAttributes();
        boolean isValid;
        long accountId = -1;
        CredentialsValidationBankResponseAttributes.ValidationFailReason reason = null;

        Card card = findCardByCardNumber(requestAttributes.getCardNumber());
        Account account = findAccountByCardNumber(requestAttributes.getCardNumber());
        if (card == null) {
            isValid = false;
            reason = CredentialsValidationBankResponseAttributes.ValidationFailReason.InvalidCardNumber;
        }
        else {
            if (card.getPinNumber() != requestAttributes.getPinNumber()) {
                isValid = false;
                accountId = account.getAccountId();
                reason = CredentialsValidationBankResponseAttributes.ValidationFailReason.InvalidPinNumber;
            }
            else {
                if (account.isLocked()) {
                    isValid = false;
                    accountId = account.getAccountId();
                    reason = CredentialsValidationBankResponseAttributes.ValidationFailReason.AccountLocked;
                }
                else {
                    isValid = true;
                    accountId = account.getAccountId();
                }
            }
        }

        CredentialsValidationBankResponseAttributes responseAttributes
                = new CredentialsValidationBankResponseAttributes(true, isValid, accountId, reason);
        return new BankResponse<>(responseAttributes);
    }

    @Override
    public BankResponse<BalanceView, BalanceViewBankResponseAttributes> respondBalanceView(BankRequest<BalanceView, BalanceViewBankRequestAttributes> bankRequest) {
        BalanceViewBankRequestAttributes requestAttributes = bankRequest.getBankRequestAttributes();
        Account account = findAccountById(requestAttributes.getAccountId());
        boolean isSuccessful = false;
        double amount = 0;
        if (account != null) {
            if (requestAttributes.getAccountType() == AccountType.Checking) {
                amount = account.getCheckingAmount();
            }
            else {
                amount = account.getSavingAmount();
            }
            isSuccessful = true;
        }
        BalanceViewBankResponseAttributes responseAttributes
                = new BalanceViewBankResponseAttributes(isSuccessful, amount);
        return new BankResponse<>(responseAttributes);
    }

    @Override
    public BankResponse<DepositMoney, DepositMoneyBankResponseAttributes> respondDepositMoney(BankRequest<DepositMoney, DepositMoneyBankRequestAttributes> bankRequest) {
        DepositMoneyBankRequestAttributes requestAttributes = bankRequest.getBankRequestAttributes();
        Account account = findAccountById(requestAttributes.getAccountId());
        boolean isSuccessful = false;
        double amount = requestAttributes.getAmount();
        if (account != null) {
            if (amount > 0) {
                if (requestAttributes.getAccountType() == AccountType.Checking) {
                    account.setCheckingAmount(account.getCheckingAmount() + amount);
                }
                else {
                    account.setSavingAmount(account.getSavingAmount() + amount);
                }
                isSuccessful = true;
                transactions.add(new Transaction(transactionCounter++, account, requestAttributes.getAccountType(),
                        amount, Transaction.TransactionType.Deposit));
            }
        }
        DepositMoneyBankResponseAttributes responseAttributes
                = new DepositMoneyBankResponseAttributes(isSuccessful);
        return new BankResponse<>(responseAttributes);
    }

    @Override
    public BankResponse<WithdrawMoney, WithdrawMoneyBankResponseAttributes> respondWithdrawMoney(BankRequest<WithdrawMoney, WithdrawMoneyBankRequestAttributes> bankRequest) {
        WithdrawMoneyBankRequestAttributes requestAttributes = bankRequest.getBankRequestAttributes();
        Account account = findAccountById(requestAttributes.getAccountId());
        boolean isSuccessful = false;
        double amount = requestAttributes.getAmount();
        if (account != null) {
            if (amount > 0) {
                if (requestAttributes.getAccountType() == AccountType.Checking) {
                    if (amount <= account.getCheckingAmount()) {
                        account.setCheckingAmount(account.getCheckingAmount() - amount);
                        isSuccessful = true;
                        transactions.add(new Transaction(transactionCounter++, account, requestAttributes.getAccountType(),
                                amount, Transaction.TransactionType.Withdraw));
                    }
                }
                else {
                    if (amount <= account.getSavingAmount()) {
                        account.setSavingAmount(account.getSavingAmount() - amount);
                        isSuccessful = true;
                        transactions.add(new Transaction(transactionCounter++, account, requestAttributes.getAccountType(),
                                amount, Transaction.TransactionType.Withdraw));
                    }
                }
                transactions.add(new Transaction(transactionCounter++, account, requestAttributes.getAccountType(),
                        amount, Transaction.TransactionType.Deposit));
            }
        }

        WithdrawMoneyBankResponseAttributes responseAttributes
                = new WithdrawMoneyBankResponseAttributes(isSuccessful);
        return new BankResponse<>(responseAttributes);
    }

    @Override
    public BankResponse<CreateAccount, CreateAccountBankResponseAttributes> respondCreateAccount(BankRequest<CreateAccount, CreateAccountBankRequestAttributes> bankRequest) {
        CreateAccountBankRequestAttributes requestAttributes = bankRequest.getBankRequestAttributes();
        long accountId = IDUtils.generateID(ACCOUNT_IDS, 10, false);
        Account account = new Account(this, accountId, requestAttributes.getName());
        accounts.add(account);

        CreateAccountBankResponseAttributes responseAttributes
                = new CreateAccountBankResponseAttributes(true, accountId);
        return new BankResponse<>(responseAttributes);
    }

    @Override
    public BankResponse<DeleteAccount, DeleteAccountBankResponseAttributes> respondDeleteAccount(BankRequest<DeleteAccount, DeleteAccountBankRequestAttributes> bankRequest) {
        DeleteAccountBankRequestAttributes requestAttributes = bankRequest.getBankRequestAttributes();
        boolean isSuccessful = false;
        Account account = findAccountById(requestAttributes.getAccountId());
        if (account != null) {
            accounts.remove(account);
            isSuccessful = true;
        }

        DeleteAccountBankResponseAttributes responseAttributes
                = new DeleteAccountBankResponseAttributes(isSuccessful);
        return new BankResponse<>(responseAttributes);
    }

    @Override
    public BankResponse<OpenCard, OpenCardBankResponseAttributes> respondOpenCard(BankRequest<OpenCard, OpenCardBankRequestAttributes> bankRequest) {
        OpenCardBankRequestAttributes requestAttributes = bankRequest.getBankRequestAttributes();
        boolean isSuccessful = false;
        Account account = findAccountById(requestAttributes.getAccountId());
        long cardNumber = -1;
        if (account != null) {
            cardNumber = IDUtils.generateID(CARD_NUMBERS, 16, false);
            account.addCard(new Card(cardNumber, (int)IDUtils.generateID(new HashSet<>(), 4, true)));
            isSuccessful = true;
        }

        OpenCardBankResponseAttributes responseAttributes
                = new OpenCardBankResponseAttributes(isSuccessful, cardNumber );
        return new BankResponse<>(responseAttributes);
    }

    @Override
    public BankResponse<CloseCard, CloseCardBankResponseAttributes> respondCloseCard(BankRequest<CloseCard, CloseCardBankRequestAttributes> bankRequest) {
        CloseCardBankRequestAttributes requestAttributes = bankRequest.getBankRequestAttributes();
        boolean isSuccessful = false;
        Account account = findAccountByCardNumber(requestAttributes.getCardNumber());
        Card card = findCardByCardNumber(requestAttributes.getCardNumber());
        if ((account != null) && (card != null)) {
            account.getCards().remove(card);
            isSuccessful = true;
        }

        CloseCardBankResponseAttributes responseAttributes
                = new CloseCardBankResponseAttributes(isSuccessful );
        return new BankResponse<>(responseAttributes);
    }

    @Override
    public BankResponse<ChangePinNumber, ChangePinNumberBankResponseAttributes> respondChangePinNumber(BankRequest<ChangePinNumber, ChangePinNumberBankRequestAttributes> bankRequest) {
        ChangePinNumberBankRequestAttributes requestAttributes = bankRequest.getBankRequestAttributes();
        boolean isSuccessful = false;
        Card card = findCardByCardNumber(requestAttributes.getCardNumber());
        if (card != null) {
            card.setPinNumber(requestAttributes.getPinNumber());
            isSuccessful = true;
        }

        ChangePinNumberBankResponseAttributes responseAttributes
                = new ChangePinNumberBankResponseAttributes(isSuccessful );
        return new BankResponse<>(responseAttributes);
    }

    @Override
    public BankResponse<LockAccount, LockAccountBankResponseAttributes> respondLockAccount(BankRequest<LockAccount, LockAccountBankRequestAttributes> bankRequest) {
        LockAccountBankRequestAttributes requestAttributes = bankRequest.getBankRequestAttributes();
        boolean isSuccessful = false;
        Account account = findAccountById(requestAttributes.getAccountId());
        if (account != null) {
            account.setLocked(true);
        }

        LockAccountBankResponseAttributes responseAttributes
                = new LockAccountBankResponseAttributes(isSuccessful );
        return new BankResponse<>(responseAttributes);
    }

    private Account findAccountById(long accountId) {
        for (Account account : accounts) {
            if (account.getAccountId() == accountId) {
                return account;
            }
        }
        return null;
    }


    private Card findCardByCardNumber(long cardNumber) {
        for (Account account : accounts) {
            for (Card card : account.getCards()) {
                if (card.getCardNumber() == cardNumber) {
                    return card;
                }
            }
        }
        return null;
    }

    private Account findAccountByCardNumber(long cardNumber) {
        for (Account account : accounts) {
            for (Card card : account.getCards()) {
                if (card.getCardNumber() == cardNumber) {
                    return account;
                }
            }
        }
        return null;
    }
}
