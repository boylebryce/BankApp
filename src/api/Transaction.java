package api;

import java.util.Date;

import static impl.StorageUtils.append;

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

        output = append(output, String.valueOf(transactionId));
        output = append(output, String.valueOf(time.getTime()));
        output = append(output, String.valueOf(accountID));
        output = append(output, String.valueOf(accountType));
        output = append(output, String.valueOf(amount));
        output = append(output, String.valueOf(transactionType));

        if (check != null) {
            output = append(output, "1");
            output = append(output, check.toDataString());
        }
        else {
            output = append(output, "0");
        }

        return output;
    }

    public enum TransactionType {
        Deposit,
        Withdraw;
    }
}