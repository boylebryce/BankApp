import api.IATM;
import api.IBank;
import api.IBankBranch;
import gui.MainGUI;
import impl.ATM;
import impl.Bank;
import impl.BankBranch;
import impl.BankSystem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BankApp {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(BankApp::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        IBank bank = new Bank("Test Bank");
        IBankBranch bankBranch = new BankBranch("Test London Branch");
        bank.newBranch(bankBranch);

//        long accountId = bankBranch.createAccount("John Smith");
//        long cardNumber = bankBranch.openCard(accountId);
//        bankBranch.changePinNumber(cardNumber, 1234);
//        System.out.println("Your card number: " + cardNumber);
//        System.out.println("Your pin number: " + 1234);

        IATM atm1 = new ATM();
        IATM atm2 = new ATM();
        IATM atm3 = new ATM();
        bankBranch.newATM(atm1);
        bankBranch.newATM(atm2);
        bankBranch.newATM(atm3);
        List<IATM> atms = new ArrayList<>();
        atms.add(atm1);
        atms.add(atm2);
        atms.add(atm3);
        BankSystem system = new BankSystem(atms, Collections.singletonList(bankBranch));

        MainGUI gui = new MainGUI(system);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gui.pack();
        gui.setVisible(true);
    }
}
