package seedu.busybreak.command;

import seedu.busybreak.activity.Activity;
import seedu.busybreak.BusyBreak;
import seedu.busybreak.parser.Parser;
import seedu.busybreak.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Edit {
    private static Logger logger = Logger.getLogger(BusyBreak.class.getName());

    public static void editActivityDataInList(String[] userInputArray) {
        try {
            logger.log(Level.INFO, "Editing Activity " +  userInputArray[1]);
            int index = Parser.parseActivityIndex(userInputArray[1]);
            assert index >= 0 && index < BusyBreak.list.size() : "Index out of bounds";

            if (index < 0 || index >= BusyBreak.list.size()) {
                Ui.showInvalidIndexMessage();
                return;
            }

            Activity editedActivity = BusyBreak.list.get(index);
            Parser.ParseEditDetails editDetails = Parser.parseEditActivityDetails(userInputArray);

            if (editDetails.hasInvalidDetail()){
                Ui.showInvalidDetailMessage();
                return;
            }

            String oldDesc = editedActivity.getDescription();
            String oldCost = editedActivity.getCost();

            applyEditsToActivity(editedActivity, editDetails);
            Ui.showEditedActivity(userInputArray[1],  editedActivity);
            BusyBreak.budgetPlan.updateActivityExpense(oldDesc, oldCost, editedActivity.getDescription(),
                    editedActivity.getCost());

            BusyBreak.getStorage().saveActivities();
            BusyBreak.getStorage().saveBudgets();

        } catch (NumberFormatException e) {
            Ui.showInvalidIndexFormatMessage();
        }
    }

    private static void applyEditsToActivity(Activity editedActivity, Parser.ParseEditDetails editDetails) {
        if (editDetails.cost() != null){
            editedActivity.setCost(editDetails.cost());
            assert editedActivity.getCost().equals(editDetails.cost()) : "Cost must match edited cost";
        }

        if (editDetails.description() != null){
            editedActivity.setDescription(editDetails.description());
            assert editedActivity.getDescription().equals(editDetails.description()) : "Description " +
                    "must match edited description";
        }

        if (editDetails.time() != null){
            editedActivity.setTime(editDetails.time());
            assert editedActivity.getTime().equals(editDetails.time()) : "Time must match edited time";
        }

        if (editDetails.date() != null){
            editedActivity.setDate(editDetails.date());
            assert editedActivity.getDate().equals(editDetails.date()) : "Date must match edited date";
        }
    }
}
