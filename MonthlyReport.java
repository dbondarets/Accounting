package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MonthlyReport {
    private Month month;
    private List<Transaction> transactions;

    public MonthlyReport(Month month) {
        this.month = month;
        this.transactions = new ArrayList<>();
    }

    public void add(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return List.copyOf(transactions);
    }

    public Month getMonth() {
        return month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthlyReport that = (MonthlyReport) o;
        return Objects.equals(transactions, that.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactions);
    }

    @Override
    public String toString() {
        return "MonthlyReport{" +
                "transactions=" + transactions +
                '}';
    }
}
