package gui;

import api.ATMMaintenancePolicy;
import api.AccountType;
import api.IATM;

import javax.swing.*;
import java.awt.event.*;

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
            atmScreenTextArea.setText("Can not parse card number");
            return;
        }
        try {
            pinNumber = Integer.parseInt(pinNumberField.getText());
        }
        catch (NumberFormatException e) {
            atmScreenTextArea.setText("Can not parse pin number");
            return;
        }

        try {
            atm.authenticateCustomer(cardNumber, pinNumber);
        }
        catch (IllegalArgumentException e) {
            atmScreenTextArea.setText(e.getMessage());
            return;
        }

        cardNumberField.setText("");
        pinNumberField.setText("");
        setUIEnabled(false);
    }

    private void processExecuteButtonButton() {
        if (viewAccountRadioButton.isSelected()) {
            AccountType accountType = checkingRadioButton.isSelected() ? AccountType.Checking : AccountType.Saving;

            double amount = atm.viewAccount(accountType);
            atmScreenTextArea.setText("Your " + accountType + " account amount: $" + String.format("%.2f", amount));

            return;
        }

        if (depositRadioButton.isSelected()) {
            AccountType accountType = checkingRadioButton.isSelected() ? AccountType.Checking : AccountType.Saving;

            try {
                double amount = Double.parseDouble(amountField.getText());

                if (amount <= 0) {
                    throw new IllegalStateException("Deposit amount must be positive");
                }

                atm.depositCash(accountType, amount, receiptCheckBox.isSelected());
                atmScreenTextArea.setText("Deposit to " + accountType + " account: $" + String.format("%.2f", amount));
                updateMaintenanceCounters();
            }
            catch (IllegalStateException e) {
                atmScreenTextArea.setText(e.getMessage());
                return;
            }
            catch (NumberFormatException e) {
                atmScreenTextArea.setText("Can not parse deposit amount");
                return;
            }

            return;
        }

        if (withdrawRadioButton.isSelected()) {
            AccountType accountType = checkingRadioButton.isSelected() ? AccountType.Checking : AccountType.Saving;

            try {
                double amount = Double.parseDouble(amountField.getText());

                if (amount <= 0) {
                    throw new IllegalStateException("Withdraw amount must be positive");
                }

                atm.withdraw(accountType, amount, receiptCheckBox.isSelected());
                atmScreenTextArea.setText("Withdraw from " + accountType + " account: $" + String.format("%.2f", amount));
                updateMaintenanceCounters();
            }
            catch (NumberFormatException e) {
                atmScreenTextArea.setText("Can not parse withdraw amount");
                return;
            }
            catch (IllegalArgumentException | IllegalStateException e) {
                atmScreenTextArea.setText(e.getMessage());
                return;
            }

            return;
        }
     }

    private void processExitButton() {
        atm.quit();
        setUIEnabled(true);
    }

}
