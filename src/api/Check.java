package api;

import java.util.Date;

public class Check {
    private double amount;
    private long routingNumber;
    private long accountNumber;
    private long checkNumber;
    private Date checkDate;

    public Check(double amount, int routingNumber, int accountNumber, int checkNumber, Date checkDate) {
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
}