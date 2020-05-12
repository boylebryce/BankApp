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

        List<IBankBranch> bankBranches = bank.getBranches();

        for (IBankBranch branch : bankBranches) {
            branch.setBank(bank);
        }

        List<IATM> atms = new ArrayList<>();

        for (IBankBranch branch : bankBranches) {

            try {

                branch.loadATMsFromFile();
                atms.addAll(branch.getATMs());

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
