
public class ATMMaintenancePolicy {
    private static final double DefaultLowMoneyLevel = 5000;
    private static final int DefaultLowInkLevel = 5;
    private static final int DefaultLowPaperLevel = 5;

    private static final double DefaultMoneyAddAmont = 500000;
    private static final int DefaultInkAddAmount = 1000;
    private static final int DefaultPaperAddAmount = 1000;

    private final double lowMoneyLevel;
    private final int lowInkLevel;
    private final int lowPaperLevel;

    private final double moneyAddAmount;
    private final double inkAddAmount;
    private final double paperAddAmount;

    public ATMMaintenancePolicy() {
        this(DefaultLowMoneyLevel, DefaultLowInkLevel, DefaultLowPaperLevel,
                DefaultMoneyAddAmont, DefaultInkAddAmount, DefaultPaperAddAmount);
    }

    public ATMMaintenancePolicy(double lowMoneyLevel, int lowInkLevel, int lowPaperLevel,
                                double moneyAddAmount, double inkAddAmount, double paperAddAmount) {
        this.lowMoneyLevel = lowMoneyLevel;
        this.lowInkLevel = lowInkLevel;
        this.lowPaperLevel = lowPaperLevel;
        this.moneyAddAmount = moneyAddAmount;
        this.inkAddAmount = inkAddAmount;
        this.paperAddAmount = paperAddAmount;
    }

    public double getLowMoneyLevel() {
        return lowMoneyLevel;
    }

    public int getLowInkLevel() {
        return lowInkLevel;
    }

    public int getLowPaperLevel() {
        return lowPaperLevel;
    }

    public double getMoneyAddAmount() {
        return moneyAddAmount;
    }

    public double getInkAddAmount() {
        return inkAddAmount;
    }

    public double getPaperAddAmount() {
        return paperAddAmount;
    }
}
