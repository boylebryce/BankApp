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

    // Constructor for loading from file
    public Transaction(String input) {
        String[] data = input.split(",");

        this.transactionId = Long.parseLong(data[0]);
        this.time = new Date(Long.parseLong(data[1]));
        this.accountID = Long.parseLong(data[2]);
        this.accountType = AccountType.valueOf(data[3]);
        this.amount = Double.parseDouble(data[4]);
        this.transactionType = TransactionType.valueOf(data[5]);

        if (data[6] == "1") {
            double checkAmount = Double.parseDouble(data[7]);
            Long routingNumber = Long.parseLong(data[8]);
            Long accountNumber = Long.parseLong(data[9]);
            Long checkNumber = Long.parseLong(data[10]);
            Date checkDate = new Date(Long.parseLong(data[11]));
            this.check = new Check(checkAmount, routingNumber, accountNumber, checkNumber, checkDate);
        }
        else {
            this.check = null;
        }
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
            output += check.toDataString();
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