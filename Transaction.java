package model;

import java.util.Objects;

public class Transaction {
    private String itemName;
    private boolean isExpense;
    private int quantity;
    private int unitPrice;

    public Transaction(String itemName, boolean isExpense, int quantity, int unitPrice) {
        this.itemName = itemName;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isExpense() {
        return isExpense;
    }

    public void setExpense(boolean expense) {
        isExpense = expense;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return isExpense == that.isExpense && quantity == that.quantity && unitPrice == that.unitPrice && Objects.equals(itemName, that.itemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, isExpense, quantity, unitPrice);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "itemName='" + itemName + '\'' +
                ", isExpense=" + isExpense +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
