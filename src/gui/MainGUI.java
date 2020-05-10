package gui;

import api.IATM;
import api.IBank;
import api.IBankBranch;
import impl.BankSystem;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class MainGUI extends JFrame {
    private JPanel mainPanel;
    private JButton goToATMButton;
    private JButton goToBankBranchButton;
    private JTable atmTable;
    private JTable bankBranchTable;

    private BankSystem bankSystem;

    public MainGUI(BankSystem bankSystem) {
        this.bankSystem = bankSystem;

        setContentPane(mainPanel);
        setVisible(true);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        goToATMButton.addActionListener(e -> processGoToATMButtonAction());
        goToBankBranchButton.addActionListener(e -> processGoToBankBranchButtonAction());
    }

    private void createUIComponents() {
        ATMTableModel atmTableModel = new ATMTableModel();
        for (IATM atm : bankSystem.getAtms()) {
            atmTableModel.addRow(atm);
        }
        atmTable = new JTable(atmTableModel);
        atmTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        atmTable.getSelectionModel().addListSelectionListener(e -> goToATMButton.setEnabled(atmTable.getSelectedRow() >= 0));

        BankBranchTableModel bankBranchTableModel = new BankBranchTableModel();
        for (IBankBranch bankBranch : bankSystem.getBankBranches()) {
            bankBranchTableModel.addRow(bankBranch);
        }
        bankBranchTable = new JTable(bankBranchTableModel);
        bankBranchTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bankBranchTable.getSelectionModel().addListSelectionListener(e -> goToBankBranchButton.setEnabled(bankBranchTable.getSelectedRow() >= 0));
    }

    private void processGoToATMButtonAction() {
        this.setVisible(false);
        ATMTableModel atmTableModel = (ATMTableModel) atmTable.getModel();
        IATM atm = atmTableModel.getSelectedATM(atmTable.getSelectedRow());
        new ATMGUI(this, atm);
    }

    void processGoToBankBranchButtonAction() {
        this.setVisible(false);
        BankBranchTableModel bankBranchTableModel = (BankBranchTableModel) bankBranchTable.getModel();
        IBankBranch iBankBranch = bankBranchTableModel.getSelectedBranch(bankBranchTable.getSelectedRow());
        new BankBranchGUI(this, iBankBranch);
    }

    static class ATMTableModel extends AbstractTableModel {
        private final List<IATM> atms;

        public ATMTableModel() {
            atms = new ArrayList<>();
        }

        @Override
        public String getColumnName(int columnIndex) {
            if (columnIndex == 0) {
                return "ATM ID";
            }
            return null;
        }

        @Override
        public int getRowCount() {
            return atms.size();
        }

        @Override
        public int getColumnCount() {
            return 1;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            IATM atm = atms.get(rowIndex);
            if (columnIndex == 0) {
                return "ATM#" + atm.getId();
            }
            return null;
        }

        public void addRow(IATM atm) {
            int rowCount = getRowCount();
            atms.add(atm);
            fireTableRowsInserted(rowCount, rowCount);
        }

        public IATM getSelectedATM(int i) {
            return atms.get(i);
        }
    }

    static class BankBranchTableModel extends AbstractTableModel {
        private final List<IBankBranch> bankBranches;

        public BankBranchTableModel() {
            bankBranches = new ArrayList<>();
        }

        @Override
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "Branch Name";
                case 1:
                    return "Bank";
            }
            return null;
        }

        @Override
        public int getRowCount() {
            return bankBranches.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            IBankBranch bankBranch = bankBranches.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return bankBranch.getBranchName();
                case 1:
                    return bankBranch.getBank().getBankName();
            }
            return null;
        }

        public void addRow(IBankBranch bankBranch) {
            int rowCount = getRowCount();
            bankBranches.add(bankBranch);
            fireTableRowsInserted(rowCount, rowCount);
        }

        public IBankBranch getSelectedBranch(int i) {
            return bankBranches.get(i);
        }
    }
}
