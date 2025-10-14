package seedu.duke;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BudgetPlan {

    private static final Logger logger = Logger.getLogger(BudgetPlan.class.getName());
    private static final String LINE = "______________________________________________________________________";

    private double totalBudget = 0;

    private final ArrayList<String> names = new ArrayList<>();
    private final ArrayList<Double> amounts = new ArrayList<>();
    private final ArrayList<String> categories = new ArrayList<>();

    public void setBudget(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Budget cannot be negative");
        }
        this.totalBudget = amount;
        logger.log(Level.INFO, "Budget set to {0}", amount);
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public double getTotalSpent() {
        double sum = 0.0;
        for (double a : amounts) {
            sum += a;
        }
        return sum;
    }

    public double getRemainingBudget() {
        return totalBudget - getTotalSpent();
    }

    public void addExpense(String name, String cost, String category) {
        assert name != null && !name.isBlank() : "Expense name cannot be empty";
        assert cost != null && !cost.isBlank() : "Expense cost cannot be empty";

        double amount = parseAmount(cost);
        if (category == null || category.isBlank()){
            category = "Uncategorized";
        }

        names.add(name.trim());
        amounts.add(amount);
        categories.add(category.trim());

        logger.log(Level.INFO, "Added expense: {0} (${1}) [{2}]",
                new Object[]{name, String.format(Locale.US, "%.2f", amount), category});
    }

    public void deleteExpense(int oneBasedIndex) {
        assert oneBasedIndex > 0 : "Index must be positive";
        int idx = oneBasedIndex - 1;
        if (idx < 0 || idx >= names.size()) {
            printBox("Invalid index. No expense deleted.");
            return;
        }
        String removedName = names.remove(idx);
        double removedAmt = amounts.remove(idx);
        String removedCat = categories.remove(idx);
        logger.log(Level.INFO, "Deleted expense: {0} (${1}) [{2}]",
                new Object[]{removedName, removedAmt, removedCat});
        printBox(String.format(Locale.US,
                "Deleted Expense: %s | Cost: $%.2f | Category: %s",
                removedName, removedAmt, removedCat));
    }

    public void listExpenses() {
        System.out.println(LINE);
        if (names.isEmpty()) {
            System.out.println("No expenses recorded yet.");
        } else {
            for (int i = 0; i < names.size(); i++) {
                System.out.printf(Locale.US, "%d. %s - $%.2f (%s)%n",
                        i + 1, names.get(i), amounts.get(i), categories.get(i));
            }
            System.out.printf(Locale.US, "Total Spent: $%.2f | Remaining Budget: $%.2f%n",
                    getTotalSpent(), getRemainingBudget());
        }
        System.out.println(LINE);
    }

    private static double parseAmount(String raw) {
        String cleaned = raw.trim().replace("$", "").replace(",", "");
        try {
            double val = Double.parseDouble(cleaned);
            assert val >= 0 : "Expense amount cannot be negative";
            if (val < 0) {
                throw new IllegalArgumentException("Expense amount cannot be negative");
            }
            return val;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid cost format: " + raw);
        }
    }

    private static void printBox(String msg) {
        System.out.println(LINE);
        System.out.println(msg);
        System.out.println(LINE);
    }

}
