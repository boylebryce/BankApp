package api;

import java.util.Date;

import static impl.StorageUtils.append;

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

    public void setRoutingNumber(int routingNumber) {
        this.routingNumber = routingNumber;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
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

    public boolean sameIdentifiers(Check check) {
        return (routingNumber == check.getRoutingNumber()
                && accountNumber == check.getAccountNumber()
                && checkNumber == check.getCheckNumber());
    }

    public String toString() {
        String output = "";

        output += "Routing number: " + Long.toString(routingNumber);
        output += "/Account number: " + Long.toString(accountNumber);
        output += "/Check number: " + Long.toString(checkNumber);
        output += "/Date: " + checkDate.toString();

        return output;
    }

    public String toDataString() {
        String output = "";

        output = append(output, String.valueOf(amount));
        output = append(output, String.valueOf(routingNumber));
        output = append(output, String.valueOf(accountNumber));
        output = append(output, String.valueOf(checkNumber));
        output = append(output, String.valueOf(checkDate.getTime()));

        return output;
    }
}