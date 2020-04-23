import java.util.ArrayList;
import java.util.Collection;

public class Account {
    private final IBank bank;
    private final long accountId;
    private final String name;
    private double savingAmount;
    private double checkingAmount;
    private final Collection<Card> cards;
    private final Collection<Transaction> transactions;
    private boolean isLocked;

    public Account(IBank bank, long accountId, String name) {
        this.bank = bank;
        this.accountId = accountId;
        this.name = name;
        this.savingAmount = 0;
        this.checkingAmount = 0;
        this.cards = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.isLocked = false;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public double getSavingAmount() {
        return savingAmount;
    }

    public double getCheckingAmount() {
        return checkingAmount;
    }

    public Collection<Card> getCards() {
        return cards;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setSavingAmount(double savingAmount) {
        this.savingAmount = savingAmount;
    }

    public void setCheckingAmount(double checkingAmount) {
        this.checkingAmount = checkingAmount;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }
}
