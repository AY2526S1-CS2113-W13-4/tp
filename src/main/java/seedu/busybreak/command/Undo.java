package seedu.busybreak.command;

import seedu.busybreak.BusyBreak;
import seedu.busybreak.Ui;
import seedu.busybreak.activity.BudgetPlan;
import seedu.busybreak.storage.History;
import seedu.busybreak.storage.Load;

import java.nio.file.Path;
import java.util.Optional;

/**
 * Handles the undo command.
 * Restores the previous application state from a saved snapshot.
 */

public class Undo {

    /**
     * Validates the command format, checks that a snapshot exists,
     * restores the latest snapshot, resets in-memory state, reloads data,
     * and reports the outcome via Ui
     *
     * @param args tokenised input; must contain only {@code "undo"} (length 1)
     */
    public static void undoInput(String[] args) {
        if (args == null || args.length != 1) {
            Ui.showInvalidUndoFormat();
            return;
        }
        if (!History.hasSnapshots()) {
            Ui.showNothingToUndo();
            return;
        }
        Optional<Path> snap = History.restoreLatest();
        if (snap.isEmpty()) {
            Ui.showNothingToUndo();
            return;
        }
        try {
            BusyBreak.list.clear();
            BusyBreak.trips.clear();
            BusyBreak.budgetPlan = new BudgetPlan();

            Load loader = new Load();
            loader.loadActivities();
            loader.loadBudgets();
            BusyBreak.budgetPlan.syncFromActivities(BusyBreak.list);
            loader.loadTrips();

            Ui.showUndoSuccess();
        } catch (Exception e) {
            Ui.showError("Undo failed: " + e.getMessage());
        }
    }
}


