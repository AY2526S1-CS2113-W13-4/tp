package seedu.busybreak.command;

import seedu.busybreak.activity.Activity;
import seedu.busybreak.BusyBreak;
import seedu.busybreak.parser.Parser;
import seedu.busybreak.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Delete {
    private static Logger logger = Logger.getLogger(BusyBreak.class.getName());

    public static void deleteActivityDataFromList(String[] userInputArray) {
        assert userInputArray.length >= 2 : "User input array must have at least 2 elements";

        try {
            int index = Parser.parseActivityIndex(userInputArray[1]);
            logger.log(Level.INFO, "Deleting Activity " + userInputArray[1] + " from the list.");
            assert index >= 0 && index < BusyBreak.list.size() : "Index out of bounds";

            if (index < 0 || index >= BusyBreak.list.size()) {
                Ui.showInvalidIndexMessage();
                return;
            }

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
        }
    }
}
