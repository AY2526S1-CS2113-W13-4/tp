package seedu.busybreak.activity;
import seedu.busybreak.Activity;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BudgetPlan {
    private static final Logger logger = Logger.getLogger(BudgetPlan.class.getName());
    private static final String LINE = "______________________________________________________________________";

    public final ArrayList<String> names = new ArrayList<>();
    public final ArrayList<Double> amounts = new ArrayList<>();
    public final ArrayList<String> categories = new ArrayList<>();

    private double totalBudget = 0;

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

    private boolean isActivityExpenseAt(int idx) {
        return idx >= 0 && idx < categories.size() && "Activity".equalsIgnoreCase(categories.get(idx));
    }

    public boolean isActivityCategory(String category) {
        return "Activity".equalsIgnoreCase(normalizeCategory(category));
    }


    public void addActivityExpense(String description, String costString) {
        assert description != null && !description.isBlank();
        assert costString != null && !costString.isBlank();
        double amount = parseAmount(costString);
        names.add(description.trim());
        amounts.add(amount);
        categories.add("Activity");
        logger.log(Level.INFO, "Activity expense added: {0} (${1})", new Object[]{description, amount});
    }

    public boolean removeActivityExpense(String description, String costString) {
        assert description != null && !description.isBlank();
        assert costString != null && !costString.isBlank();
        double amt = parseAmount(costString);
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(description) && Math.abs(amounts.get(i) - amt) < 1e-9) {
                String oldCat = categories.get(i);
                names.remove(i);
                amounts.remove(i);
                categories.remove(i);
                logger.log(Level.INFO, "Activity expense removed: {0} (${1}) [{2}]",
                        new Object[]{description, amt, oldCat});
                return true;
            }
        }
        logger.log(Level.WARNING, "Activity expense to remove not found: {0} (${1})",
                new Object[]{description, amt});
        return false;
    }


    public void updateActivityExpense(String oldDesc, String oldCostStr, String newDesc, String newCostStr) {
        double oldCost = parseAmount(oldCostStr);
        double newCost = parseAmount(newCostStr);

        for (int i = 0; i < names.size(); i++) {
            if ("Activity".equalsIgnoreCase(categories.get(i))
                    && names.get(i).equals(oldDesc)
                    && Math.abs(amounts.get(i) - oldCost) < 1e-9) {
                names.set(i, newDesc);
                amounts.set(i, newCost);
                return;
            }
        }
    }

    public boolean hasExpense(String name, String cost, String category) {
        double amt = parseAmount(cost);
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(name)
                    && Math.abs(amounts.get(i) - amt) < 1e-9
                    && categories.get(i).equals(category)) {
                return true;
            }
        }
        return false;
    }

    public void syncFromActivities(java.util.List<Activity> activities) {
        if (activities == null) {
            return;
        }
        for (Activity a : activities) {
            if (a == null) {
                continue;
            }
            String desc = a.getDescription();
            String cost = a.getCost();
            if (!hasExpense(desc, cost, "Activity")) {
                addActivityExpense(desc, cost);
            }
        }
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
        if (isActivityExpenseAt(idx)) {
            printBox("This expense is linked to an Activity. " + "Please edit/delete it via the Activity commands.");
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


    public void setExpenseCategory(int oneBasedIndex, String newCategory) {
        assert oneBasedIndex > 0 : "Index must be positive";
        int idx = oneBasedIndex - 1;

        if (idx < 0 || idx >= names.size()) {
            printBox("Invalid index. No category updated.");
            return;
        }
        if (isActivityExpenseAt(idx)) {
            printBox("Cannot change the category of an Activity-linked expense. " + "Edit the Activity instead.");
            return;
        }

        String oldCat = categories.get(idx);
        String cat = normalizeCategory(newCategory);

        categories.set(idx, cat);

        logger.log(Level.INFO, "Updated category for \"{0}\": {1} -> {2}",
                new Object[]{names.get(idx), oldCat, cat});

        printBox(String.format(Locale.US,
                "Updated category for \"%s\": %s -> %s",
                names.get(idx), oldCat, cat));
    }

    private String normalizeCategory(String category) {
        if (category == null || category.isBlank()) {
            return "Uncategorized";
        }
        String c = category.trim().toLowerCase();

        if (c.equals("meal") || c.equals("meals") || c.equals("snack") || c.equals("snacks")
                || c.equals("breakfast") || c.equals("lunch") || c.equals("dinner")) {
            return "Food";
        }
        if (c.equals("taxi") || c.equals("uber") || c.equals("grab") || c.equals("bus") || c.equals("train")) {
            return "Transport";
        }
        if (c.equals("hotel") || c.equals("hostel") || c.equals("rental") || c.equals("accommodation")) {
            return "Lodging";
        }
        if (c.equals("activities") || c.equals("tour") || c.equals("ticket") || c.equals("museum")) {
            return "Activity";
        }

        String raw = category.trim();
        return Character.toUpperCase(raw.charAt(0)) + raw.substring(1);
    }

    private static int findIndex(java.util.ArrayList<String> list, String target) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(target)) {
                return i;
            }
        }
        return -1;
    }

    private void buildCategoryTotals(java.util.ArrayList<String> cats, java.util.ArrayList<Double> totals) {
        for (int i = 0; i < categories.size(); i++) {
            String cat = categories.get(i);
            double amt = amounts.get(i);
            int idx = findIndex(cats, cat);
            if (idx == -1) {
                cats.add(cat);
                totals.add(amt);
            } else {
                totals.set(idx, totals.get(idx) + amt);
            }
        }
    }

    private static void sortByTotalsDesc(java.util.ArrayList<String> cats,  java.util.ArrayList<Double> totals) {
        for (int i = 0; i < totals.size(); i++) {
            int maxIdx = i;
            for (int j = i + 1; j < totals.size(); j++) {
                if (totals.get(j) > totals.get(maxIdx)) {
                    maxIdx = j;
                }
            }
            if (maxIdx != i) {
                double t = totals.get(i);
                totals.set(i, totals.get(maxIdx));
                totals.set(maxIdx, t);
                String c = cats.get(i);
                cats.set(i, cats.get(maxIdx));
                cats.set(maxIdx, c);
            }
        }
    }


    public void listByCategory() {
        java.util.ArrayList<String> cats = new java.util.ArrayList<>();
        java.util.ArrayList<Double> totals = new java.util.ArrayList<>();
        buildCategoryTotals(cats, totals);
        sortByTotalsDesc(cats, totals);

        System.out.println(LINE);
        if (cats.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            System.out.println(LINE);
            return;
        }
        System.out.println("Spending by category:");
        for (int i = 0; i < cats.size(); i++) {
            System.out.printf(java.util.Locale.US, " - %s: $%.2f%n", cats.get(i), totals.get(i));
        }
        System.out.printf(java.util.Locale.US,
                "Total Spent: $%.2f | Remaining Budget: $%.2f%n",
                getTotalSpent(), getRemainingBudget());
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
