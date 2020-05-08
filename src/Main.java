import api.AccountType;
import api.IATM;
import api.IBank;
import api.IBankBranch;
import impl.ATM;
import impl.Bank;
import impl.BankBranch;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        IBank bank = new Bank("Test Bank");
        IBankBranch bankBranch = new BankBranch(" London Test Bank");
        IATM atm = new ATM();

        bank.newBranch(bankBranch);
        bankBranch.newATM(atm);

        String accountName = "John Smith";
        long accountId = bankBranch.createAccount(accountName);
        long cardId = bankBranch.openCard(accountId);
        int newPinNumber = 1234;
        bankBranch.changePinNumber(cardId, newPinNumber);


        System.out.println("Your card id: " + cardId);
        System.out.println("Your pin number: " + newPinNumber);

        while (true) {
            try {
                System.out.println("Enter your card id");
                long enterdCardId = Long.parseLong(scanner.nextLine().trim());
                System.out.println("Enter your card id");

                int enteredPin = Integer.parseInt(scanner.nextLine().trim());
                atm.authenticateCustomer(enterdCardId, enteredPin);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                continue;
            }

            while (true) {
                try {
                    if (processMainMenu(atm)) {
                        break;
                    }
                } catch (NumberFormatException nfe) {
                    System.err.println("Invalid input. Please, try again");
                }
            }
            break;
        }
    }

    private static boolean processMainMenu(IATM atm) {
        System.out.println("1. View Account");
        System.out.println("2. Deposit money");
        System.out.println("3. Withdraw money");
        System.out.println("4. Exit");
        int choice = Integer.parseInt(scanner.nextLine().trim());
        boolean exit = false;
        switch (choice) {
            case 1:
                viewAccountMenu(atm);
                break;
            case 2:
                depositMoneyMenu(atm);
                break;
            case 3:
                withdrawMoneyMenu(atm);
                break;
            case 4:
                atm.quit();
                exit = true;
                break;
            default:
                throw new NumberFormatException();
        }
        return exit;
    }

    private static void viewAccountMenu(IATM iatm) {
        while (true) {
            try {
                System.out.println("1. Saving");
                System.out.println("2. Checking");
                int choice = Integer.parseInt(scanner.nextLine().trim());
                double amount;
                switch (choice) {
                    case 1:
                        amount = iatm.viewAccount(AccountType.Saving);
                        break;
                    case 2:
                        amount = iatm.viewAccount(AccountType.Checking);
                        break;
                    default:
                        throw new NumberFormatException();
                }
                System.out.println("Current amount is: $" + amount);
                return;
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid input. Please, try again");
            }
        }
    }

    private static void depositMoneyMenu(IATM iatm) {
        while (true) {
            double amount = amountEntering();
            try {
                System.out.println("1. Saving");
                System.out.println("2. Checking");
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        iatm.deposit(AccountType.Saving, amount, false);
                        break;
                    case 2:
                        iatm.deposit(AccountType.Checking, amount, false);
                        break;
                    default:
                        throw new NumberFormatException();
                }
                System.out.println("Amount $" + amount + " was deposit");
                return;
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid input. Please, try again");
            }
        }
    }

    private static void withdrawMoneyMenu(IATM iatm) {
        while (true) {
            double amount = amountEntering();
            try {
                System.out.println("1. Saving");
                System.out.println("2. Checking");
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        iatm.withdraw(AccountType.Saving, amount, false);
                        break;
                    case 2:
                        iatm.withdraw(AccountType.Checking, amount, false);
                        break;
                    default:
                        throw new NumberFormatException();
                }
                System.out.println("Amount $" + amount + " was withdrew");
                return;
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid input. Please, try again");
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                return;
            }
        }
    }

    private static double amountEntering() {
        while (true) {
            try {
                System.out.println("Please, enter the amount");
                double amount = Double.parseDouble(scanner.nextLine().trim());
                if (amount <= 0) {
                    throw new NumberFormatException();
                }
                return amount;
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid input. Please, try again");
            }
        }
    }
}
