package api;

import api.operations.*;
import api.operations.request.*;
import api.operations.response.*;

public interface IBankRequestServer {
    default BankResponse<CredentialsValidation, CredentialsValidationBankResponseAttributes>
    respondCredentialValidation(BankRequest<CredentialsValidation, CredentialsValidationBankRequestAttributes> bankRequest) {
        throw new UnsupportedOperationException();
    }

    default BankResponse<BalanceView, BalanceViewBankResponseAttributes>
    respondBalanceView(BankRequest<BalanceView, BalanceViewBankRequestAttributes> bankRequest) {
        throw new UnsupportedOperationException();
    }

    default BankResponse<DepositMoney, DepositMoneyBankResponseAttributes>
    respondDepositMoney(BankRequest<DepositMoney, DepositMoneyBankRequestAttributes> bankRequest) {
        throw new UnsupportedOperationException();
    }

    default BankResponse<WithdrawMoney, WithdrawMoneyBankResponseAttributes>
    respondWithdrawMoney(BankRequest<WithdrawMoney, WithdrawMoneyBankRequestAttributes> bankRequest) {
        throw new UnsupportedOperationException();
    }

    default BankResponse<CreateAccount, CreateAccountBankResponseAttributes>
    respondCreateAccount(BankRequest<CreateAccount, CreateAccountBankRequestAttributes> bankRequest) {
        throw new UnsupportedOperationException();
    }

    default BankResponse<DeleteAccount, DeleteAccountBankResponseAttributes>
    respondDeleteAccount(BankRequest<DeleteAccount, DeleteAccountBankRequestAttributes> bankRequest) {
        throw new UnsupportedOperationException();
    }

    default BankResponse<OpenCard, OpenCardBankResponseAttributes>
    respondOpenCard(BankRequest<OpenCard, OpenCardBankRequestAttributes> bankRequest) {
        throw new UnsupportedOperationException();
    }

    default BankResponse<CloseCard, CloseCardBankResponseAttributes>
    respondCloseCard(BankRequest<CloseCard, CloseCardBankRequestAttributes> bankRequest) {
        throw new UnsupportedOperationException();
    }

    default BankResponse<ChangePinNumber, ChangePinNumberBankResponseAttributes>
    respondChangePinNumber(BankRequest<ChangePinNumber, ChangePinNumberBankRequestAttributes> bankRequest) {
        throw new UnsupportedOperationException();
    }

    default BankResponse<LockAccount, LockAccountBankResponseAttributes>
    respondLockAccount(BankRequest<LockAccount, LockAccountBankRequestAttributes> bankRequest) {
        throw new UnsupportedOperationException();
    }
}
