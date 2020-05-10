package gui;

import api.IBankBranch;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BankBranchGUI extends JFrame {
    private final MainGUI mainGUI;
    private final IBankBranch bankBranch;

    private JPanel mainPanel;
    private JRadioButton createAccountButton;
    private JRadioButton removeAccountButton;
    private JRadioButton openCardButton;
    private JRadioButton changePinButton;
    private JRadioButton closeCardButton;
    private JButton executeButton;
    private JTextArea branchShowArea;
    private JLabel actionLabel;
    private JTextField inputField;
    private JLabel branchNameLabel;

    public BankBranchGUI(MainGUI mainGUI, IBankBranch bankBranch) {
        this.mainGUI = mainGUI;
        this.bankBranch = bankBranch;
        setContentPane(mainPanel);
        setVisible(true);
        pack();
        createUIComponents();
    }

    private void createUIComponents() {
        branchNameLabel.setText(bankBranch.getBranchName());

        executeButton.addActionListener(e -> processExecuteButtonButton());

        createAccountButton.addActionListener(e -> resetUI());
        removeAccountButton.addActionListener(e -> resetUI());
        openCardButton.addActionListener(e -> resetUI());
        closeCardButton.addActionListener(e -> resetUI());
        changePinButton.addActionListener(e -> resetUI());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainGUI.setVisible(true);
            }
        });
        resetUI();
    }

    private void resetUI() {
        if (createAccountButton.isSelected()) {
            actionLabel.setText("Please, enter new bank client name:");
        } else if (removeAccountButton.isSelected()) {
            actionLabel.setText("Please, enter bank client id to remove:");
        } else if (openCardButton.isSelected()) {
            actionLabel.setText("Please, enter bank client id to create card for:");
        } else if (closeCardButton.isSelected()) {
            actionLabel.setText("Please, enter card number to close:");
        } else if (changePinButton.isSelected()) {
            actionLabel.setText("Please, enter bank card number to change pin and new pin code (space separated):");
        }
    }

    private void processExecuteButtonButton() {
        if (createAccountButton.isSelected()) {
            String line = inputField.getText().trim();
            if (line.isEmpty()) {
                branchShowArea.setText("ERROR: You entered empty client name");
            } else {
                long clientId = bankBranch.createAccount(line);
                branchShowArea.setText("New client created. Bank Account ID: " + clientId);
            }
        } else if (removeAccountButton.isSelected()) {
            String line = inputField.getText().trim();
            try {
                long clientId = Long.parseLong(line);
                bankBranch.deleteAccount(clientId);
                branchShowArea.setText("Bank Account with ID: " + clientId + " was removed");
            } catch (NumberFormatException e) {
                branchShowArea.setText("ERROR: Can not parse account ID");
            } catch (IllegalArgumentException e) {
                branchShowArea.setText("ERROR: " + e.getMessage());
            }
        } else if (openCardButton.isSelected()) {
            String line = inputField.getText().trim();
            try {
                long clientId = Long.parseLong(line);
                long[] numbers = bankBranch.openCard(clientId);
                branchShowArea.setText("A card for Account with ID: " + clientId + " was created" + System.lineSeparator()
                        + "Card number: " + numbers[0] + "  Pin number: " + numbers[1]);
            } catch (NumberFormatException e) {
                branchShowArea.setText("ERROR: Can not parse account ID");
            } catch (IllegalArgumentException e) {
                branchShowArea.setText("ERROR: " + e.getMessage());
            }
        } else if (closeCardButton.isSelected()) {
            String line = inputField.getText().trim();
            try {
                long cardNumber = Long.parseLong(line);
                bankBranch.closeCard(cardNumber);
                branchShowArea.setText("Card with number: " + cardNumber + " was closed");
            } catch (NumberFormatException e) {
                branchShowArea.setText("ERROR: Can not parse card number");
            } catch (IllegalArgumentException e) {
                branchShowArea.setText("ERROR: " + e.getMessage());
            }
        } else if (changePinButton.isSelected()) {
            String line = inputField.getText().trim();
            try (Scanner lineScan = new Scanner(line)) {
                long cardNumber = -1;
                int newPinNumber = -1;
                try {
                    cardNumber = lineScan.nextLong();
                } catch (NoSuchElementException e) {
                    branchShowArea.setText("ERROR: Can not parse card number");
                    return;
                }
                try {
                    newPinNumber = lineScan.nextInt();
                } catch (NoSuchElementException e) {
                    branchShowArea.setText("ERROR: Can not parse PIN number");
                    return;
                }
                try {
                    bankBranch.changePinNumber(cardNumber, newPinNumber);
                    branchShowArea.setText("PIN code for card with number: " + cardNumber + " was changed to " + newPinNumber);
                } catch (IllegalArgumentException e) {
                    branchShowArea.setText("ERROR: " + e.getMessage());
                }
            }
        }
    }
}
