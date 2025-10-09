package seedu.duke;

public class BudgetPlan {
    private double totalBudget;

    public void setBudget(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Budget cannot be negative");
        }
        this.totalBudget = amount;
    }

    public double getTotalBudget() {
        return totalBudget;
    }
}
