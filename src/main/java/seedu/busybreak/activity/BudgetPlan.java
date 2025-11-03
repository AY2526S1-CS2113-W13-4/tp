package seedu.busybreak.activity;

import seedu.busybreak.BusyBreak;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;


//@@author samika2005
/**
 * Represents the user's overall budget plan.
 *
 * <p>Handles expense entries, budget limits, and category breakdowns.
 * This class also synchronizes "Activity" expenses with the {@link seedu.busybreak.activity.Activity} module
 * to ensure consistency between user-added activities and their corresponding budget entries.</p>
 */

public class BudgetPlan {
    private static final Logger logger = Logger.getLogger(BudgetPlan.class.getName());
    private static final String LINE = "______________________________________________________________________";

    public final ArrayList<String> names = new ArrayList<>();
    public final ArrayList<Double> amounts = new ArrayList<>();
    public final ArrayList<String> categories = new ArrayList<>();

    private double totalBudget = 0;

    /**
     * Sets the total available budget.
     *
     * <p>If a negative value is provided, the budget remains unchanged
     * and an error message is displayed.</p>
     *
     * @param amount The total budget amount to set.
     */

    public void setBudget(double amount) {
        if (amount < 0) {
            System.out.println("Budget cannot be negative. Budget will stay unchanged.");
            return;
        }
        this.totalBudget = amount;
        logger.log(Level.INFO, "Budget set to {0}", amount);
    }

    /**
     * Returns the total budget amount.
     *
     * @return The user's total budget.
     */

    public double getTotalBudget() {
        return totalBudget;
    }

    /**
     * Calculates the total amount spent across all expenses.
     *
     * @return Total spent amount.
     */

    public double getTotalSpent() {
        double sum = 0.0;
        for (double a : amounts) {
            sum += a;
        }
        return sum;
    }

    /**
     * Returns the remaining budget after deducting total expenses.
     *
     * @return Remaining amount in the budget.
     */

    public double getRemainingBudget() {
        return totalBudget - getTotalSpent();
    }

    /**
     * Checks if an expense at the given index belongs to the Activity category.
     *
     * @param idx Zero-based index to check.
     * @return {@code true} if it is an Activity expense; {@code false} otherwise.
     */

    private boolean isActivityExpenseAt(int idx) {
        return idx >= 0 && idx < categories.size() && "Activity".equalsIgnoreCase(categories.get(idx));
    }

    /**
     * Determines whether the provided category string refers to an Activity category.
     *
     * @param category The category to check.
     * @return {@code true} if the category maps to "Activity"; {@code false} otherwise.
    */

    public boolean isActivityCategory(String category) {
        return "Activity".equalsIgnoreCase(normalizeCategory(category));
    }

    /**
     * Adds a new expense linked to an Activity.
     *
     * @param description Description of the expense.
     * @param costString Cost of the expense as a string.
     * @return {@code true} if successfully added; {@code false} if cost is invalid.
     */
    public boolean addActivityExpense(String description, String costString) {
        assert description != null && !description.isBlank();
        assert costString != null && !costString.isBlank();
        double amount = parseAmount(costString);
        if (amount < 0) {
            return false;
        }
        names.add(description.trim());
        amounts.add(amount);
        categories.add("Activity");
        logger.log(Level.INFO, "Activity expense added: {0} (${1})", new Object[]{description, amount});
        return true;
    }


    /**
     * Removes an Activity-linked expense that matches both description and cost.
     *
     * @param description The expense description.
     * @param costString The expense cost.
     * @return {@code true} if the expense was found and removed; {@code false} otherwise.
     */

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


    /**
     * Updates an Activity-linked expense when a corresponding activity is edited.
     *
     * @param oldDesc Previous description.
     * @param oldCostStr Previous cost string.
     * @param newDesc New description to replace the old one.
     * @param newCostStr New cost string.
     */

    public void updateActivityExpense(String oldDesc, String oldCostStr, String newDesc, String newCostStr) {
        double oldCost = parseAmount(oldCostStr);
        double newCost = parseAmount(newCostStr);
        if (newCost < 0) {
            return;
        }

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

    /**
     * Checks whether a given expense already exists in the plan.
     *
     * @param name Expense name or description.
     * @param cost Expense cost string.
     * @param category Expense category.
     * @return {@code true} if an identical expense exists; {@code false} otherwise.
     */

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


    /**
     * Synchronizes the budget plan with all recorded activities.
     *
     * <p>Ensures that every activity entry has a matching Activity expense.</p>
     *
     * @param activities List of Activity objects to synchronize from.
     */

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


    /**
     * Adds a general expense with name, cost, and category.
     *
     * @param name Expense name.
     * @param cost Expense cost as a string.
     * @param category Expense category (defaults to "Uncategorized" if empty).
     * @return {@code true} if successfully added; {@code false} otherwise.
     */

    public boolean addExpense(String name, String cost, String category) {
        assert name != null && !name.isBlank() : "Expense name cannot be empty";
        assert cost != null && !cost.isBlank() : "Expense cost cannot be empty";
        double amount = parseAmount(cost);
        if (category == null || category.isBlank()){
            category = "Uncategorized";
        }

        if (amount < 0) {
            return false;
        }
        names.add(name.trim());
        amounts.add(amount);
        categories.add(normalizeCategory(category.trim()));

        logger.log(Level.INFO, "Added expense: {0} (${1}) [{2}]",
                new Object[]{name, String.format(Locale.US, "%.2f", amount), category});
        return true;
    }


    /**
     * Deletes an expense by its one-based index.
     *
     * @param oneBasedIndex The index of the expense to delete (starting from 1).
     */

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

    /**
     * Displays all recorded expenses with total spent and remaining budget.
     */

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

    /**
     * Updates the category of an existing expense.
     *
     * @param oneBasedIndex Index of the expense (starting from 1).
     * @param newCategory New category name.
     */

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

    /**
     * Converts raw category text into standard categories such as
     * Food, Transport, Lodging, or Activity.
     *
     * @param category Raw input category.
     * @return Normalized category name.
     */

    private String normalizeCategory(String category) {
        if (category == null || category.isBlank()) {
            return "Uncategorized";
        }
        String c = category.trim().toLowerCase();

        if (c.equals("meal") || c.equals("meals") || c.equals("snack") || c.equals("snacks")
                || c.equals("breakfast") || c.equals("lunch") || c.equals("dinner")) {
            return "Food";
        }
        if (c.equals("taxi") || c.equals("uber") || c.equals("grab") || c.equals("bus") || c.equals("flight")
                || c.equals("flights") || c.equals("train")) {
            return "Transport";
        }
        if (c.equals("hotel") || c.equals("hostel") || c.equals("resort") || c.equals("rental")
                || c.equals("accommodation")) {
            return "Lodging";
        }
        if (c.equals("activities") || c.equals("tour") || c.equals("ticket") || c.equals("museum")) {
            return "Activity";
        }

        String raw = category.trim();
        return Character.toUpperCase(raw.charAt(0)) + raw.substring(1);
    }

    /**
     * Finds the index of a string in a given list.
     *
     * @param list The list to search.
     * @param target The target string to find.
     * @return Index of the string, or -1 if not found.
     */

    private static int findIndex(java.util.ArrayList<String> list, String target) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(target)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * calculates total spending per category.
     *
     * @param cats List to store category names.
     * @param totals List to store corresponding total amounts.
     */

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

    /**
     * Sorts categories in descending order of spending totals.
     *
     * @param cats List of category names.
     * @param totals List of totals aligned with {@code cats}.
     */

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

    /**
     * Prints all categories with total spending in descending order.
     */

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

    /**
     * Parses a cost string into a numeric value.
     *
     * <p>Rejects invalid, negative, or excessively large inputs (≥ 1,000,000).</p>
     *
     * @param raw The raw cost string to parse.
     * @return Parsed value, or -1 if invalid.
     */

    private static double parseAmount(String raw) {
        String cleaned = raw.trim().replace("$", "").replace(",", "");
        double val;
        double skip = -1;
        try {
            val = Double.parseDouble(cleaned);
            assert val >= 0 : "Expense amount cannot be negative";
        } catch (NumberFormatException e) {
            System.out.println(BusyBreak.LINE);
            System.out.println("Invalid cost format: " + raw);
            System.out.println(BusyBreak.LINE);
            return skip;
        }

        if (val < 0) {
            System.out.println(BusyBreak.LINE);
            System.out.println("Expense amount cannot be negative");
            System.out.println(BusyBreak.LINE);
            return skip;
        }
        final double maxAmount = 1_000_000d;
        if (val >= maxAmount) {
            System.out.println(BusyBreak.LINE);
            System.out.println("Amount is too large, maybe try NOT millions?");
            System.out.println(BusyBreak.LINE);
            return skip;
        }
        return val;
    }

    private static void printBox(String msg) {
        System.out.println(LINE);
        System.out.println(msg);
        System.out.println(LINE);
    }


}
