package api;

public interface IATM {
    long getId();

    void setBankBranch(IBankBranch bankBranch);
    void setATMMaintenancePolicy(ATMMaintenancePolicy atmMaintenancePolicy);

    // Maintenance methods
    void setMoneyLevel(double money);
    void setInkLevel(int ink);
    void setPaperLevel(int paper);

    double getMoneyLevel();
    int getInkLevel();
    int getPaperLevel();

    // Logic methods
    void authenticateCustomer(long cardNumber, int pinNumber);
    double viewAccount(AccountType accountType);
    void depositCash(AccountType accountType, double amount, boolean printReceipt);
    boolean depositCheck(AccountType accountType, Check check, boolean printReceipt);
    void withdraw(AccountType accountType, double amount, boolean printReceipt);
    void quit();

    String toDataString();
}
