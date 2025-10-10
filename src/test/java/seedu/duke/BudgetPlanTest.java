package seedu.duke;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}
