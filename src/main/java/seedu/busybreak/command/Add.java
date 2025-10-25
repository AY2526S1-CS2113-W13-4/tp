package seedu.busybreak.command;

import seedu.busybreak.Activity;
import seedu.busybreak.BusyBreak;
import seedu.busybreak.Parser;
import seedu.busybreak.Ui;

public class Add {
    public static void addActivityDataToList(String[] userInputArray) {
        Parser.ParseActivityData activityData = Parser.getParseActivityData(userInputArray);
        if (activityData == null) {
            return;
        }

        BusyBreak.list.add(new Activity(activityData.date(), activityData.time(),
                activityData.description(), activityData.cost()));
        Ui.printAddedItem(activityData);
        BusyBreak.getStorage().saveActivities();
    }

}
