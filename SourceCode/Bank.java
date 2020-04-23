package com.company;

import java.util.List;
import java.util.Scanner;

public class Bank {
    private List<Branch> branches;
    private List<Customer> customers;
    private List<Account> accounts;
    private List<Transaction> transactions;
    private String firstName;
    private String lastName;
    private double balance;
    private String pinNumber;
    private double cardNumber;
    private String accountNum;

    Scanner KB=new Scanner(System.in);

    public Bank() {

    }

    //method to create a bank account
    public void createAccount()
    {
        System.out.print("Enter first Name: ");
        firstName=KB.next();
        System.out.println("Enter last name: ");
        lastName=KB.next();
        System.out.print("Enter an account Number: ");
        accountNum=KB.next();
        System.out.println("Please enter a pin number for the account:");
        pinNumber=KB.next();
        System.out.print("Enter the amount of money you want to deposit into your new account: ");
        balance=KB.nextLong();
    }

    public void displayAccount()
    {
        System.out.println("\nDisplaying account information:");
        System.out.println("Account Number:" + accountNum+ "\n First Name:"+firstName+"\n Last Name:"+lastName+"\n Balance:" +balance+"\n");
    }

    public void deposit()
    {
        long amt;
        System.out.println("Enter Amount you want to Deposit into your account : ");
        amt=KB.nextLong();
        balance=balance+amt;
    }

    //method to withdraw money
    public void withdrawal()
    {
        long amt;
        System.out.println("Enter Amount you want to withdraw : ");
        amt=KB.nextLong();
        if(balance>=amt)
        {
            balance=balance-amt;
        }
        else
        {
            System.out.println("Transaction Failed (not enough money in the account to process this transaction");
        }
    }

    //method to search an account number
    public boolean search(String acn)
    {
        if(accountNum.equals(acn))
        {
            displayAccount();displayAccount();
            return(true);
        }
        return(false);
    }



    private void sendMoneyToATM() {

    }

    private void sendMoneyToBranch() {

    }

    private void newBranch(Branch newBranch) {

    }

    private void validateRequest() {

    }

    public void lockAccount() {

    }


}
