package gui;

import api.ATMMaintenancePolicy;
import api.AccountType;
import api.Check;
import api.IATM;

import javax.swing.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static javax.swing.JOptionPane.showInputDialog;

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
    private JRadioButton depositCheckRadioButton;
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
        depositCheckRadioButton.addActionListener(e -> {
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
        depositCheckRadioButton.setEnabled(!isGuest);
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
        catch (NullPointerException e) {
            atmScreenTextArea.setText(e.getMessage());
            return;
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

        if (depositCheckRadioButton.isSelected()) {
            AccountType accountType = checkingRadioButton.isSelected() ? AccountType.Checking : AccountType.Saving;

            try {
                double amount = Double.parseDouble(amountField.getText());

                if (amount <= 0) {
                    throw new IllegalArgumentException("Deposit amount must be positive");
                }

                // Get check identifiers
                long accountNumber = Long.parseLong(showInputDialog("Enter the account number on the check"));
                long routingNumber = Long.parseLong(showInputDialog("Enter the routing number on the check"));
                long checkNumber = Long.parseLong(showInputDialog("Enter the check number"));

                DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
                String inputDate = showInputDialog("Enter the check writing date in the format MM/DD/YY");
                Date checkDate = format.parse(inputDate);

                Check check = new Check(amount, routingNumber, accountNumber, checkNumber, checkDate);
                boolean depositResult = atm.depositCheck(accountType, check, receiptCheckBox.isSelected());

                if (depositResult) {
                    atmScreenTextArea.setText("Deposit to " + accountType + " account: $" + String.format("%.2f", amount));
                }
                else {
                    atmScreenTextArea.setText("Error in check deposit - please visit a branch for more details");
                }


            }
            catch (NumberFormatException e) {
                atmScreenTextArea.setText("Error parsing input");
                return;
            }
            catch (IllegalArgumentException e) {
                atmScreenTextArea.setText(e.getMessage());
                return;
            } catch (ParseException e) {
                atmScreenTextArea.setText("Error parsing check writing date");
                return;
            }
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
