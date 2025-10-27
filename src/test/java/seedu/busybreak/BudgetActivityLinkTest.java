package seedu.busybreak;

import org.junit.jupiter.api.Test;
import seedu.busybreak.activity.BudgetPlan;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Tests focusing on the linkage between Activity and BudgetPlan.
 * Make sure your test path matches your project structure.
 */
class BudgetActivityLinkTest {
    private static Activity mkAct(String d, String t, String desc, String cost) {
        return new Activity(d, t, desc, cost);
    }

    private static String capturePrint(Runnable r) {
        PrintStream original = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        try {
            r.run();
        } finally {
            System.setOut(original);
        }
        return baos.toString();
    }

    @Test
    void syncFromActivities_addsMissingActivityExpenses_andIsIdempotent() {
        BudgetPlan p = new BudgetPlan();
        var acts = new ArrayList<Activity>();
        acts.add(mkAct("2025-01-01","08:00","Temple","3"));
        acts.add(mkAct("2025-01-02","10:00","Boat Tour","12"));

        p.syncFromActivities(acts);
        // run again to assert no duplicates
        p.syncFromActivities(acts);

        assertTrue(p.hasExpense("Temple","3","Activity"));
        assertTrue(p.hasExpense("Boat Tour","12","Activity"));
        assertEquals(15.0, p.getTotalSpent(), 1e-9);
    }

    @Test
    void addActivityExpense_createsLinkedEntry() {
        BudgetPlan p = new BudgetPlan();
        p.addActivityExpense("Shrine Visit", "4.50");
        assertTrue(p.hasExpense("Shrine Visit","4.50","Activity"));
        assertEquals(4.50, p.getTotalSpent(), 1e-9);
    }

    @Test
    void updateActivityExpense_updatesMatchingByDescAndCost() {
        BudgetPlan p = new BudgetPlan();
        p.addActivityExpense("Museum", "20");
        // change both desc and cost
        p.updateActivityExpense("Museum", "20", "Art Museum", "35");

        assertFalse(p.hasExpense("Museum","20","Activity"));
        assertTrue(p.hasExpense("Art Museum","35","Activity"));
        assertEquals(35.0, p.getTotalSpent(), 1e-9);
    }

    @Test
    void removeActivityExpense_removesOnlyExactMatch() {
        BudgetPlan p = new BudgetPlan();
        p.addActivityExpense("Kayak", "18");
        p.addActivityExpense("Kayak", "20"); // different cost

        assertTrue(p.removeActivityExpense("Kayak","18"));
        assertFalse(p.hasExpense("Kayak","18","Activity"));
        assertTrue(p.hasExpense("Kayak","20","Activity"));
        assertEquals(20.0, p.getTotalSpent(), 1e-9);
    }

    @Test
    void setcat_doesNotChangeCategoryForActivityLinkedItem() {
        BudgetPlan p = new BudgetPlan();
        p.addActivityExpense("Boat", "12");
        // Index 1 should be the Activity row
        String out = capturePrint(() -> p.setExpenseCategory(1, "Food"));

        // Model must remain Activity
        assertTrue(p.hasExpense("Boat","12","Activity"));

        // Optional: if your model prints a warning, assert it (lenient contains check)
        String lc = out.toLowerCase();
        // don't fail if empty (e.g., if blocking is in command layer)
        if (!lc.isBlank()) {
            assertTrue(lc.contains("activity") && (lc.contains("cannot") || lc.contains("linked")));
        }
    }

    @Test
    void deleteExpense_doesNotDeleteActivityLinkedItem() {
        BudgetPlan p = new BudgetPlan();
        p.addActivityExpense("Hike", "0"); // free but still Activity-linked
        // Attempt to delete first item
        String out = capturePrint(() -> p.deleteExpense(1));

        // The row should still exist
        assertTrue(p.hasExpense("Hike","0","Activity"));

        // warning check
        String lc = out.toLowerCase();
        if (!lc.isBlank()) {
            assertTrue(lc.contains("activity") || lc.contains("linked") || lc.contains("cannot"));
        }
    }

    @Test
    void listByCategory_showsTotalsAndRemainingBudget() {
        BudgetPlan p = new BudgetPlan();
        p.setBudget(100);
        p.addExpense("Water","1","Food");
        p.addActivityExpense("Gallery","10");
        p.addExpense("Taxi","5","Transport");

        String out = capturePrint(p::listByCategory);
        assertTrue(out.contains("Spending by category:"));
        assertTrue(out.contains("Food"));
        assertTrue(out.contains("Activity"));
        assertTrue(out.contains("Transport"));
        assertTrue(out.contains("Remaining Budget"));
        assertEquals(16.0, p.getTotalSpent(), 1e-9);
        assertEquals(84.0, p.getRemainingBudget(), 1e-9);
    }

    @Test
    void parseAmount_acceptsDollarAndCommas() {
        BudgetPlan p = new BudgetPlan();
        p.addExpense("Hotel", "$1,234.56", "Lodging");
        assertEquals(1234.56, p.getTotalSpent(), 1e-9);
    }

    @Test
    void syncFromActivities_toleratesNullsAndSkipsInvalids() {
        BudgetPlan p = new BudgetPlan();
        var acts = new ArrayList<Activity>();
        acts.add(null);
        acts.add(mkAct("2025-01-01","09:00","Walk","0"));
        acts.add(mkAct("2025-01-01","09:00","Walk","0")); // dup
        p.syncFromActivities(acts);

        assertTrue(p.hasExpense("Walk","0","Activity"));
        assertEquals(0.0, p.getTotalSpent(), 1e-9);
    }

    @Test
    void remainingBudget_consistentAfterActivityEdits() {
        BudgetPlan p = new BudgetPlan();
        p.setBudget(50);
        p.addActivityExpense("Skate","10");
        p.addExpense("Snack","5","Food");
        assertEquals(35.0, p.getRemainingBudget(), 1e-9);

        p.updateActivityExpense("Skate","10","Skate","12.5");
        assertEquals(32.5, p.getRemainingBudget(), 1e-9);

        p.removeActivityExpense("Skate","12.5");
        assertEquals(45.0, p.getRemainingBudget(), 1e-9);
    }
}

