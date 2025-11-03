package seedu.busybreak;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import seedu.busybreak.activity.Activity;

public class UndoTest {

    @BeforeEach
    void cleanDataDir() throws Exception {
        Path data = Paths.get("data");
        if (Files.exists(data)) {
            List<Path> paths;
            try (var s = Files.walk(data)) {
                paths = s.sorted(Comparator.reverseOrder()).toList();
            }
            for (Path p : paths) {
                try {
                    Files.deleteIfExists(p);
                } catch (Exception e) {
                    assertTrue(true);
                }
            }
        }
        Files.createDirectories(data);

        BusyBreak.list.clear();
        BusyBreak.trips.clear();
        BusyBreak.budgetPlan = new seedu.busybreak.activity.BudgetPlan();
        BusyBreak.getStorage().saveActivities();
        BusyBreak.getStorage().saveBudgets();
        BusyBreak.getStorage().saveTrips();
    }

    @Test
    void undoRestoresPreviousSnapshot_afterDelete() {
        BusyBreak.list.add(new Activity("2025-01-01", "10:00", "A", "10"));
        BusyBreak.list.add(new Activity("2025-01-01", "11:00", "B", "20"));
        BusyBreak.getStorage().saveActivities();

        seedu.busybreak.storage.History.checkpointWithSave(BusyBreak.getStorage());

        BusyBreak.list.remove(1);
        BusyBreak.getStorage().saveActivities();

        assertEquals(1, BusyBreak.list.size());

        seedu.busybreak.command.Undo.undoInput(new String[] { "undo" });

        assertEquals(2, BusyBreak.list.size());
    }
}



