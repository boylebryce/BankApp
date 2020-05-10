package gui;

import api.ATMMaintenancePolicy;
import api.AccountType;
import api.IATM;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarFile;

public class ATMGUI extends JFrame {
    private MainGUI mainGUI;
    private IATM atm;

    private JPanel mainPanel;
    private JTextField cardNumberField;
    private JPasswordField pinNumberField;
    private JButton authenticateButton;
    private JProgressBar papersProgressBar;
    private JPanel maintenancePanel;
    private JProgressBar inkProgressBar;
    private JProgressBar moneyProgressBar;
    private JButton exitButton;
    private JRadioButton withdrawRadioButton;
    private JRadioButton viewAccountRadioButton;
    private JRadioButton depositRadioButton;
    private JTextField amountField;
    private JButton executeButton;
    private JTextArea atmScreenTextArea;
    private JRadioButton checkingRadioButton;
    private JRadioButton savingRadioButton;
    private JLabel atmIdLabel;
    private JCheckBox receiptCheckBox;

    public ATMGUI(MainGUI mainGUI, IATM atm) {
        this.mainGUI = mainGUI;
        this.atm = atm;
        setContentPane(mainPanel);
        setVisible(true);
        pack();
        createUIComponents();
    }

    private void createUIComponents() {
        atmIdLabel.setText("ATM #" + atm.getId());
        moneyProgressBar.setMaximum((int)ATMMaintenancePolicy.MaxMoneyAmount);
        papersProgressBar.setMaximum(ATMMaintenancePolicy.MaxPaperAmount);
        inkProgressBar.setMaximum(ATMMaintenancePolicy.MaxInkAmount);

        updateMaintenanceCounters();

        authenticateButton.addActionListener(e -> processAuthenticateButton());
        executeButton.addActionListener(e -> processExecuteButtonButton());
        exitButton.addActionListener(e -> processExitButton());

        viewAccountRadioButton.addActionListener(e -> {
            amountField.setEnabled(false);
            amountField.setEditable(false);
            amountField.setText("");
            receiptCheckBox.setEnabled(false);
            receiptCheckBox.setSelected(false);
        });
        depositRadioButton.addActionListener(e -> {
            amountField.setEnabled(true);
            amountField.setEditable(true);
            amountField.setText("");
            receiptCheckBox.setEnabled(true);
            receiptCheckBox.setSelected(false);
        });
        withdrawRadioButton.addActionListener(e -> {
            amountField.setEnabled(true);
            amountField.setEditable(true);
            amountField.setText("");
            receiptCheckBox.setEnabled(true);
            receiptCheckBox.setSelected(false);
        });

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                mainGUI.setVisible(true);
            }
        });
        setUIEnabled(true);
    }

    private void updateMaintenanceCounters() {
        SwingUtilities.invokeLater(() -> {
            moneyProgressBar.setValue((int)atm.getMoneyLevel());
            papersProgressBar.setValue(atm.getPaperLevel());
            inkProgressBar.setValue(atm.getInkLevel());
        });
    }

    private void setUIEnabled(boolean isGuest) {
        cardNumberField.setEnabled(isGuest);
        cardNumberField.setEditable(isGuest);
        cardNumberField.setText("");
        pinNumberField.setEnabled(isGuest);
        pinNumberField.setEditable(isGuest);
        pinNumberField.setText("");
        authenticateButton.setEnabled(isGuest);

        atmScreenTextArea.setText("");

        viewAccountRadioButton.setEnabled(!isGuest);
        viewAccountRadioButton.setSelected(true);
        depositRadioButton.setEnabled(!isGuest);
        withdrawRadioButton.setEnabled(!isGuest);
        checkingRadioButton.setEnabled(!isGuest);
        savingRadioButton.setEnabled(!isGuest);
        receiptCheckBox.setEnabled(false);
        amountField.setEnabled(false);
        amountField.setEditable(false);
        amountField.setText("");
        executeButton.setEnabled(!isGuest);
        exitButton.setEnabled(!isGuest);
    }

    private void processAuthenticateButton() {
        long cardNumber;
        int pinNumber;
        try {
            cardNumber = Long.parseLong(cardNumberField.getText());
        }
        catch (NumberFormatException e) {
            atmScreenTextArea.setText("ERROR: Can not parse card number");
            return;
        }
        try {
            pinNumber = Integer.parseInt(pinNumberField.getText());
        }
        catch (NumberFormatException e) {
            atmScreenTextArea.setText("ERROR: Can not parse pin number");
            return;
        }

        try {
            atm.authenticateCustomer(cardNumber, pinNumber);
        }
        catch (IllegalArgumentException e) {
            atmScreenTextArea.setText("ERROR: " + e.getMessage());
            return;
        }

        cardNumberField.setText("");
        pinNumberField.setText("");
        setUIEnabled(false);
    }

    private void processExecuteButtonButton() {
        if (viewAccountRadioButton.isSelected()) {
            if (checkingRadioButton.isSelected()) {
                double amount = atm.viewAccount(AccountType.Checking);
                atmScreenTextArea.setText("Your checking account amount: $" + String.format("%.2f", amount));
            }
            else {
                double amount = atm.viewAccount(AccountType.Saving);
                atmScreenTextArea.setText("Your saving account amount: $" + String.format("%.2f", amount));
            }
            return;
        }
        if (depositRadioButton.isSelected()) {
            if (checkingRadioButton.isSelected()) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    if (amount <= 0) {
                        throw new IllegalStateException("Deposit amount must be positive");
                    }
                    atm.deposit(AccountType.Checking, amount, receiptCheckBox.isSelected());
                    atmScreenTextArea.setText("Deposit to checking account: $" + String.format("%.2f", amount));
                    updateMaintenanceCounters();
                }
                catch (IllegalStateException e) {
                    atmScreenTextArea.setText("ERROR: " + e.getMessage());
                    return;
                }
                catch (NumberFormatException e) {
                    atmScreenTextArea.setText("ERROR: Can not parse deposit amount");
                    return;
                }
            }
            else {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    if (amount <= 0) {
                        throw new IllegalStateException("Deposit amount must be positive");
                    }
                    atm.deposit(AccountType.Saving, amount, receiptCheckBox.isSelected());
                    atmScreenTextArea.setText("Deposit to saving account: $" + String.format("%.2f", amount));
                    updateMaintenanceCounters();
                }
                catch (IllegalStateException e) {
                    atmScreenTextArea.setText("ERROR: " + e.getMessage());
                    return;
                }
                catch (NumberFormatException e) {
                    atmScreenTextArea.setText("ERROR: Can not parse deposit amount");
                    return;
                }
            }
            return;
        }
        if (withdrawRadioButton.isSelected()) {
            if (checkingRadioButton.isSelected()) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    if (amount <= 0) {
                        throw new IllegalStateException("Withdraw amount must be positive");
                    }
                    atm.withdraw(AccountType.Checking, amount, receiptCheckBox.isSelected());
                    atmScreenTextArea.setText("Withdraw from checking account: $" + String.format("%.2f", amount));
                    updateMaintenanceCounters();
                }
                catch (NumberFormatException e) {
                    atmScreenTextArea.setText("ERROR: Can not parse withdraw amount");
                    return;
                }
                catch (IllegalArgumentException | IllegalStateException e) {
                    atmScreenTextArea.setText("ERROR: " + e.getMessage());
                    return;
                }
            }
            else {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    if (amount <= 0) {
                        throw new IllegalStateException("Withdraw amount must be positive");
                    }
                    atm.withdraw(AccountType.Saving, amount, receiptCheckBox.isSelected());
                    atmScreenTextArea.setText("Withdraw from saving account: $" + String.format("%.2f", amount));
                    updateMaintenanceCounters();
                }
                catch (NumberFormatException e) {
                    atmScreenTextArea.setText("ERROR: Can not parse withdraw amount");
                    return;
                }
                catch (IllegalArgumentException | IllegalStateException e) {
                    atmScreenTextArea.setText(e.getMessage());
                    return;
                }
            }
            return;
        }
     }

    private void processExitButton() {
        atm.quit();
        setUIEnabled(true);
    }
}
