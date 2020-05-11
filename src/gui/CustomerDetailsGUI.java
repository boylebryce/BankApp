package gui;

import api.Customer;
import impl.BankSystem;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerDetailsGUI extends JFrame {
    private static Pattern phonePattern = Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}");
    private static Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private BankSystem bankSystem;

    private JTextField nameField;
    private JTextField phoneField;
    private JTextField eMailField;
    private JTextField addressField;
    private JButton enterButton;
    private JTextArea customerDetailsShowArea;
    private JPanel mainPanel;

    public CustomerDetailsGUI(BankSystem bankSystem) {
        this.bankSystem = bankSystem;
        setContentPane(mainPanel);
        setVisible(true);
        pack();
        createUIComponents();
    }

    private void createUIComponents() {
        Dimension size = new Dimension(400,250);
        setMaximumSize(size);
        setMinimumSize(size);
        setPreferredSize(size);
        setResizable(false);

        enterButton.addActionListener(e -> processEnterButton());
    }

    private void processEnterButton() {
        String name = nameField.getText().trim();
        String phoneLine = phoneField.getText().trim();
        String emailLine = eMailField.getText().trim();
        String address = addressField.getText().trim();
        if (name.isEmpty()) {
            customerDetailsShowArea.setText("ERROR: Customer name is empty");
            return;
        }
        if (!phonePattern.matcher(phoneLine).find()) {
            customerDetailsShowArea.setText("ERROR: Can not parse phone number");
            return;
        }
        if (!emailPattern.matcher(emailLine).find()) {
            customerDetailsShowArea.setText("ERROR: Can not parse e-mail address");
            return;
        }
        if (address.isEmpty()) {
            customerDetailsShowArea.setText("ERROR: Customer address is empty");
            return;
        }
        Customer customer = new Customer(name, phoneLine, emailLine, address);
        setVisible(false);

        new MainGUI(bankSystem, customer);
    }
}
