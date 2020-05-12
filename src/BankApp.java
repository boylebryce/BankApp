import api.IATM;
import api.IBank;
import api.IBankBranch;
import gui.CustomerDetailsGUI;
import gui.MainGUI;
import impl.ATM;
import impl.Bank;
import impl.BankBranch;
import impl.BankSystem;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BankApp {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(BankApp::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        IBank bank = new Bank("Test Bank");
//        IBankBranch bankBranch = new BankBranch("Test London Branch");
//        bank.newBranch(bankBranch);

        // Load Bank's branches from file
        try {
            bank.loadBranchesFromFile();
        } catch (IOException e) {
            System.out.println("Error loading branches from file: " + e.getMessage());
        }

        List<IBankBranch> bankBranches = bank.getBranches();

        for (IBankBranch branch : bankBranches) {
            branch.setBank(bank);
        }

//        try {
//            bank.saveBranchesToFile();
//        }
//        catch (IOException e) {
//            System.out.println("Error loading branches from file: " + e.getMessage());
//        }


//        long accountId = bankBranch.createAccount("John Smith");
//        long cardNumber = bankBranch.openCard(accountId);
//        bankBranch.changePinNumber(cardNumber, 1234);
//        System.out.println("Your card number: " + cardNumber);
//        System.out.println("Your pin number: " + 1234);

        List<IATM> atms = new ArrayList<>();

        for (IBankBranch branch : bankBranches) {
            try {
                branch.loadATMsFromFile();

                for (IATM atm : branch.getATMs()) {
                    atms.add(atm);
                }

            } catch (IOException e) {
                System.out.println("Error loading ATMs from file: " + e.getMessage());
            }
        }

        BankSystem system = new BankSystem(atms, bankBranches);

        CustomerDetailsGUI gui = new CustomerDetailsGUI(system);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gui.pack();
        gui.setVisible(true);
    }
}
