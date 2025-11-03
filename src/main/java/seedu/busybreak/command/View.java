package seedu.busybreak.command;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.busybreak.BusyBreak;
import seedu.busybreak.Ui;
import seedu.busybreak.activity.Activity;

/**
 * Handles the {@code view} command for listing activities on a date.
 */

public class View {

    public static Logger logger = Logger.getLogger(BusyBreak.class.getName());

    /**
     * Executes {@code view <YYYY-MM-DD>}.
     *
     * @param userInputArray tokens where index 1 is the date string.
     */

    public static void viewInput(String[] userInputArray) {
        assert userInputArray != null : "Input cannot be null";
        if (userInputArray.length != 2) {
            logger.log(Level.WARNING, "Invalid command format for view");
            Ui.showInvalidViewFormat();
            return;
        }
        String date = userInputArray[1].trim();
        assert !date.isEmpty() : "Date cannot be empty";
        logger.log(Level.INFO, "Date: " + date);

        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            logger.log(Level.WARNING, "Invalid date: " + date);
            Ui.showInvalidDate(date);
            return;
        }
        if (BusyBreak.list.isEmpty()) {
            logger.log(Level.INFO, "Itinerary is currently empty");
            Ui.showEmptyItinerary();
            return;
        }
        ArrayList<Activity> matches = new ArrayList<>();
        for (Activity a : BusyBreak.list) {
            if (date.equals(a.getDate())) {
                matches.add(a);
            }
        }
        if (matches.isEmpty()) {
            logger.log(Level.INFO, "No activities for " + date);
            Ui.showNoActivitiesFor(date);
            return;
        }
        Ui.showItineraryFor(date, matches);

    }

}
