package api;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;

import static impl.StorageUtils.addDelimiter;

public class Account {
    private final IBank bank;
    private final long accountId;
    private final String name;
    private double savingAmount;
    private double checkingAmount;
    private final ArrayList<Card> cards;
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

    public Account(String input) {
        this.cards = new ArrayList<>();
        this.transactions = new ArrayList<>();

        String[] data = input.split(",");

        if (data.length < 7) {
            throw new InvalidParameterException("Error parsing account");
        }

        this.bank = null;
        this.accountId = Long.parseLong(data[0]);
        this.name = data[1];
        this.savingAmount = Double.parseDouble(data[2]);
        this.checkingAmount = Double.parseDouble(data[3]);

        int numCards = Integer.parseInt(data[4]);

        for (int i = 0; i < numCards; ++i) {
            int cardNumberOffset = 5 + (i * 2);
            int cardPinOffset = 6 + (i * 2);

            long cardNumber = Long.parseLong(data[cardNumberOffset]);
            int pin = Integer.parseInt(data[cardPinOffset]);

            cards.add(new Card(cardNumber, pin));
        }

        int i = 1 + (2 * numCards);

        // Index i holds the transaction counter, which is always 0, so just skip it
        this.isLocked = Boolean.parseBoolean(data[i + 1]);
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

    public ArrayList<Card> getCards() {
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
        for (int i = 0; i < cards.size(); ++i) {
            if (card.getCardNumber() == cards.get(i).getCardNumber()) {
                cards.remove(i);
                break;
            }
        }
    }

    // Generate a CSV string for saving Account state to file
    public String toDataString() {
        String output = "";

        output += addDelimiter(String.valueOf(accountId));
        output += addDelimiter(name);
        output += addDelimiter(String.valueOf(savingAmount));
        output += addDelimiter(String.valueOf(checkingAmount));
        output += addDelimiter(String.valueOf(cards.size()));

        for (Card card : cards) {
            output += card.toDataString();
        }

        output += addDelimiter(String.valueOf(transactions.size()));

        for (Transaction transaction : transactions) {
            output += transaction.toDataString();
        }

        output += addDelimiter(String.valueOf(isLocked));

        return output;

    }
}
