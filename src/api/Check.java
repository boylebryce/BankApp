package api;

import java.util.Date;

import static impl.StorageUtils.addDelimiter;

public class Check {
    private double amount;
    private long routingNumber;
    private long accountNumber;
    private long checkNumber;
    private Date checkDate;

    public Check(double amount, long routingNumber, long accountNumber, long checkNumber, Date checkDate) {
        this.amount = amount;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
        this.checkNumber = checkNumber;
        this.checkDate = checkDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(long routingNumber) {
        this.routingNumber = routingNumber;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(int checkNumber) {
        this.checkNumber = checkNumber;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String toString() {
        String output = "";

        output += addDelimiter(String.valueOf(accountNumber));
        output += addDelimiter(String.valueOf(routingNumber));
        output += addDelimiter(String.valueOf(checkNumber));

        return output;
    }

    public String toDataString() {
        String output = "";

        output += addDelimiter(String.valueOf(amount));
        output += addDelimiter(String.valueOf(routingNumber));
        output += addDelimiter(String.valueOf(accountNumber));
        output += addDelimiter(String.valueOf(checkNumber));
        output += addDelimiter(String.valueOf(checkDate.getTime()));

        return output;
    }
}