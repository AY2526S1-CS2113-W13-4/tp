package seedu.busybreak.command;

import seedu.busybreak.activity.Activity;
import seedu.busybreak.BusyBreak;
import seedu.busybreak.parser.Parser;
import seedu.busybreak.Ui;

/**
 * Handles the addition of activities to itinerary.
 * Parses user input and validates activity data.
 * Updates the budget plan.
 * Saves changes to local storage.
 */
public class Add {

    /**
     * Adds an activity to itinerary.
     * Parses the user input array to extract itinerary details.
     * If parsing fails, display error message and does not add activity.
     *
     * @param userInputArray Array of strings containing user input
     */
    public static void addActivityDataToList(String[] userInputArray) {
        Parser.ParseActivityData activityData = Parser.getParseActivityData(userInputArray);
        if (activityData == null) {
            return;
        }

        try {
            Activity a = new Activity(activityData.date(), activityData.time(),
                    activityData.description(), activityData.cost());

            BusyBreak.budgetPlan.addActivityExpense(a.getDescription(), a.getCost());
            BusyBreak.getStorage().saveBudgets();
            BusyBreak.list.add(a);
            Ui.printAddedItem(activityData);
            BusyBreak.getStorage().saveActivities();
        } catch (IllegalArgumentException | AssertionError e) {
            Ui.showLine();
            System.out.println("Invalid User Input! " + e.getMessage());
            Ui.showLine();
        }
    }

}
