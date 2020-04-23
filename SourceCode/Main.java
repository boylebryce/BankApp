package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner KB=new Scanner(System.in);

        //prototype to open an account
        System.out.print("How many accounts do you want to create: ");
        int b=KB.nextInt();
        Bank A[]=new Bank[b];
        for(int i=0;i<A.length;i++)
        {
            A[i]=new Bank();
            A[i].createAccount();
        }

        //prototype to show some ATM interaction 
        int userChoice;
        do
        {
            System.out.println("\nATM Menu:\n 1.Display Account information\n 2.Search for an Account\n 3.Deposit\n 4.Withdrawal\n  5.Exit\n");
            System.out.println("Pick an option :");
            userChoice=KB.nextInt();
            switch(userChoice)
            {
                case 1:
                    for(int i=0;i<A.length;i++)
                    {
                        A[i].displayAccount();
                    }
                    break;

                case 2:
                    System.out.print("Enter account number you want to search for: ");
                    String acn=KB.next();
                    boolean found=false;
                    for(int i=0;i<A.length;i++)
                    {
                        found=A[i].search(acn);
                        if(found)
                        {
                            break;
                        }
                    }
                    if(!found)
                    {
                        System.out.println("Account does not exist..");
                    }
                    break;

                case 3:
                    System.out.println("Deposit amount");
                    for(int i=0;i<A.length;i++)
                    {
                        A[i].deposit();
                    }
                    break;

                case 4:
                    System.out.print("Withdrawal amount: ");
                    for(int i=0;i<A.length;i++)
                    {
                        A[i].withdrawal();
                    }
                    break;

                case 5:
                    System.out.println("Thank you for using our services.");
                    break;
            }
        }
        while(userChoice!=5);
    }


    }

