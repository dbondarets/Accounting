package model;

public class Statistic {
    private Month month;
    private int amount;
    private boolean isExpense;

    public Statistic(Month month, int amount, boolean isExpense) {
        this.month = month;
        this.amount = amount;
        this.isExpense = isExpense;
    }

    public Month getMonth() {
        return month;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isExpense() {
        return isExpense;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "month=" + month +
                ", amount=" + amount +
                ", isExpense=" + isExpense +
                '}';
    }
}
