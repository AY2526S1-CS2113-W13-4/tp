package seedu.busybreak.command;

import seedu.busybreak.activity.Activity;
import seedu.busybreak.BusyBreak;
import seedu.busybreak.parser.Parser;
import seedu.busybreak.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles deleting of an activity from the itinerary list.
 */
public class Delete {
    private static Logger logger = Logger.getLogger(BusyBreak.class.getName());

    /**
     * Deletes an activity from the activity list based on the user input.
     * Validates the input, removes the activity if the index is valid,
     * updates the budget plan, and saves the updated data to storage.
     *
     * @param userInputArray Tokenised user input containing the delete command and index.
     * @throws NumberFormatException If the index provided is not an integer.
     * @throws IllegalArgumentException If the input format is invalid.
     */
    public static void deleteActivityDataFromList(String[] userInputArray) {
        try {
            Parser.checkValidDeleteInput(userInputArray);
            assert userInputArray.length >= 2 : "User input array must have at least 2 elements";
            int index = Parser.parseActivityIndex(userInputArray[1]);
            logger.log(Level.INFO, "Deleting Activity " + userInputArray[1] + " from the list.");

            if (index < 0 || index >= BusyBreak.list.size()) {
                Ui.showInvalidIndexMessage();
                return;
            }
            assert index >= 0 && index < BusyBreak.list.size() : "Index out of bounds";

            Activity deletedActivity = BusyBreak.list.get(index);
            assert deletedActivity != null : "Activity " + userInputArray[1] + " cannot be null";

            int originalSize = BusyBreak.list.size();
            BusyBreak.list.remove(index);
            BusyBreak.budgetPlan.removeActivityExpense(deletedActivity.getDescription(), deletedActivity.getCost());
            assert BusyBreak.list.size() == originalSize - 1: "The list size should decrease by 1 after deletion";

            Ui.showDeletedActivity(userInputArray[1],  deletedActivity);

            BusyBreak.getStorage().saveActivities();
            BusyBreak.getStorage().saveBudgets();

        } catch (NumberFormatException e) {
            Ui.showInvalidIndexFormatMessage();
        } catch (IllegalArgumentException e) {
            Ui.showLine();
            System.out.println(e.getMessage());
            Ui.showLine();
        }
    }
}
