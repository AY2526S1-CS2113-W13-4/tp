package seedu.busybreak;
import org.junit.jupiter.api.Test;
import seedu.busybreak.activity.BudgetPlan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class BudgetPlanTest {
    @Test
    void setBudget_storesValue() {
        BudgetPlan p = new BudgetPlan();
        p.setBudget(123.45);
        assertEquals(123.45, p.getTotalBudget(), 1e-9);
    }

    @Test
    void negativeBudget_throws() {
        BudgetPlan p = new BudgetPlan();
        assertThrows(IllegalArgumentException.class, () -> p.setBudget(-1));
    }

    @Test
    void addExpense_storesValuesCorrectly() {
        BudgetPlan plan = new BudgetPlan();
        plan.setBudget(100);
        plan.addExpense("Lunch", "12.50", "Food");

        assertEquals(12.50, plan.getTotalSpent(), 1e-9);
        assertEquals(87.50, plan.getRemainingBudget(), 1e-9);
    }

    @Test
    void addMultipleExpenses_accumulatesTotalCorrectly() {
        BudgetPlan plan = new BudgetPlan();
        plan.setBudget(100);
        plan.addExpense("Lunch", "10", "Food");
        plan.addExpense("Taxi", "$5.50", "Transport");
        plan.addExpense("Souvenir", "2.50", "Shopping");

        assertEquals(18.0, plan.getTotalSpent(), 1e-9);
        assertEquals(82.0, plan.getRemainingBudget(), 1e-9);
    }

    @Test
    void deleteExpense_removesCorrectExpense() {
        BudgetPlan plan = new BudgetPlan();
        plan.setBudget(50);
        plan.addExpense("Coffee", "5", "Drink");
        plan.addExpense("Snack", "3", "Food");

        plan.deleteExpense(1);
        assertEquals(3.0, plan.getTotalSpent(), 1e-9);
        assertEquals(47.0, plan.getRemainingBudget(), 1e-9);
    }

    @Test
    void deleteExpense_invalidIndex_doesNotThrow() {
        BudgetPlan plan = new BudgetPlan();
        plan.setBudget(20);

        assertDoesNotThrow(() -> plan.deleteExpense(1));
    }

    @Test
    void parseAmount_handlesDollarSignsAndCommas() {
        BudgetPlan plan = new BudgetPlan();
        plan.setBudget(1000);
        plan.addExpense("Hotel", "$1,234.50", "Accommodation");

        assertEquals(1234.50, plan.getTotalSpent(), 1e-9);
    }

    @Test
    void parseAmount_invalidFormat_throws() {
        BudgetPlan plan = new BudgetPlan();
        assertThrows(IllegalArgumentException.class,
                () -> plan.addExpense("Test", "abc", "Invalid"),
                "Non-numeric amount should throw an exception.");
    }

    @Test
    void assertions_areEnabled() {
        boolean caught = false;
        try {
            assert false : "Assertions disabled!";
        } catch (AssertionError e) {
            caught = true;
        }
        assertTrue(caught,
                "Assertions should be enabled (-ea) so in-code assert statements are tested.");
    }

    @Test
    void setExpenseCategory_updatesCategory_only() {
        BudgetPlan plan = new BudgetPlan();
        plan.setBudget(100);
        plan.addExpense("Burger", "10", "Food");

        double spentBefore = plan.getTotalSpent();
        double remainingBefore = plan.getRemainingBudget();

        plan.setExpenseCategory(1, "Transport");

        assertEquals("Transport", plan.categories.get(0));
        assertEquals(spentBefore, plan.getTotalSpent(), 1e-9);
        assertEquals(remainingBefore, plan.getRemainingBudget(), 1e-9);
    }

    @Test
    void setExpenseCategory_invalidIndex_doesNothing() {
        BudgetPlan plan = new BudgetPlan();
        plan.setBudget(50);
        plan.addExpense("Snack", "5", "Food");

        double spentBefore = plan.getTotalSpent();
        double remainingBefore = plan.getRemainingBudget();
        String catBefore = plan.categories.get(0);

        plan.setExpenseCategory(2, "Transport");

        assertEquals(catBefore, plan.categories.get(0));
        assertEquals(spentBefore, plan.getTotalSpent(), 1e-9);
        assertEquals(remainingBefore, plan.getRemainingBudget(), 1e-9);
    }

    @Test
    void activityFlow_addEditDelete_affectsMathCorrectly() {
        BudgetPlan plan = new BudgetPlan();
        plan.setBudget(60);

        plan.addExpense("Visit Shrine", "4.5", "Activity");
        assertEquals(55.5, plan.getRemainingBudget(), 1e-9);

        plan.deleteExpense(1);
        plan.addExpense("Visit Shrine+", "5.0", "Activity");

        assertEquals(55.0, plan.getRemainingBudget(), 1e-9); // 60 - 5.0
        assertEquals("Activity", plan.categories.get(0));
        assertEquals("Visit Shrine+", plan.names.get(0));

        plan.deleteExpense(1);
        assertEquals(60.0, plan.getRemainingBudget(), 1e-9);
        assertTrue(plan.names.isEmpty());
    }

    private static String capturePrint(Runnable r) {
        java.io.PrintStream old = System.out;
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(baos));
        try {
            r.run();
        } finally {
            System.setOut(old);
        }
        return baos.toString();
    }

    @Test
    void listExpenses_printsItemsAndTotals() {
        BudgetPlan plan = new BudgetPlan();
        plan.setBudget(20);
        plan.addExpense("Water", "1.50", "Food");
        plan.addExpense("Temple", "3", "Activity");

        String out = capturePrint(plan::listExpenses);

        assertTrue(out.contains("Water"));
        assertTrue(out.contains("Temple"));
        assertTrue(out.contains("Total Spent"));
        assertTrue(out.contains("Remaining Budget"));
        assertEquals(4.50, plan.getTotalSpent(), 1e-9);
        assertEquals(15.50, plan.getRemainingBudget(), 1e-9);
    }


}
