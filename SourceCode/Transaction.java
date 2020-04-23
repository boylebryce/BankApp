import java.util.Date;

public class Transaction {
    private long transactionId;
    private Date time;
    private Account account;
    private AccountType accountType;
    private double amount;
    private TransactionType transactionType;

    public Transaction(long transactionId, Account account, AccountType accountType, double amount, TransactionType transactionType) {
        this.transactionId = transactionId;
        this.time = new Date();
        this.account = account;
        this.accountType = accountType;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public enum TransactionType {
        Deposit,
        Withdraw;
    }