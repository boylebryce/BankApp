package api;

import java.util.Date;

import static impl.StorageUtils.addDelimiter;

public class Transaction {
    private long transactionId;
    private Date time;
    private long accountID;
    private AccountType accountType;
    private double amount;
    private TransactionType transactionType;
    private Check check = null;

    // Constructor for transaction with no check
    public Transaction(long transactionId, long accountID, AccountType accountType, double amount, TransactionType transactionType) {
        this.transactionId = transactionId;
        this.time = new Date();
        this.accountID = accountID;
        this.accountType = accountType;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    // Constructor for transaction with a check
    public Transaction(long transactionId, long accountID, AccountType accountType, double amount, TransactionType transactionType, Check check) {
        this.transactionId = transactionId;
        this.time = new Date();
        this.accountID = accountID;
        this.accountType = accountType;
        this.amount = amount;
        this.transactionType = transactionType;
        this.check = check;
    }

    public String toDataString() {
        String output = "";

        output += addDelimiter(String.valueOf(transactionId));
        output += addDelimiter(String.valueOf(time.getTime()));
        output += addDelimiter(String.valueOf(accountID));
        output += addDelimiter(String.valueOf(accountType));
        output += addDelimiter(String.valueOf(amount));
        output += addDelimiter(String.valueOf(transactionType));

        if (check != null) {
            output += addDelimiter("1");
            output += addDelimiter(check.toDataString());
        }
        else {
            output += addDelimiter("0");
        }

        return output;
    }

    public enum TransactionType {
        Deposit,
        Withdraw;
    }
}