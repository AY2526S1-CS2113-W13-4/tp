package seedu.busybreak.command;

import seedu.busybreak.activity.Activity;
import seedu.busybreak.BusyBreak;
import seedu.busybreak.parser.Parser;
import seedu.busybreak.Ui;

public class Add {
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
        } catch (IllegalArgumentException e) {
            Ui.showLine();
            System.out.println("Invalid User Input! " + e.getMessage());
            Ui.showLine();
        }
    }

}
