package seedu.busybreak.command;

import seedu.busybreak.BusyBreak;
import seedu.busybreak.Ui;
import java.util.Comparator;

//@@author msc-123456
public class Schedule {
    public static void setByTime() {
        if (BusyBreak.list.isEmpty()) {
            System.out.println(BusyBreak.LINE);
            System.out.println("Itinerary is Empty! Nothing to sort.");
            System.out.println(BusyBreak.LINE);
            return;
        }

        BusyBreak.list.sort(Comparator.comparing(a -> a.getDateTimeObject().getDateTime()));

        System.out.println(BusyBreak.LINE);
        System.out.println("Your Activities are sorted by time now!");
        Ui.printListItems();

        BusyBreak.getStorage().saveActivities();
    }
}
