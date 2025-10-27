package seedu.busybreak.command;

import seedu.busybreak.BusyBreak;
import seedu.busybreak.Ui;
import seedu.busybreak.activity.Trip;

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

    public static void sortTrips() {
        if (BusyBreak.trips.isEmpty()) {
            System.out.println(BusyBreak.LINE);
            System.out.println("No trips to sort!");
            System.out.println(BusyBreak.LINE);
            return;
        }

        if (hasTimeConflicts()) {
            System.out.println(BusyBreak.LINE);
            System.out.println("Cannot sort trips: Time conflicts detected between trips.");
            System.out.println(BusyBreak.LINE);
            return;
        }

        BusyBreak.trips.sort(Comparator.comparing(t -> t.getStartDateTime().getDateTime()));

        System.out.println(BusyBreak.LINE);
        System.out.println("Your trips are sorted by time now!");
        TripCommand.listTrips();
        BusyBreak.getStorage().saveTrips();
    }

    private static boolean hasTimeConflicts() {
        java.util.List<Trip> sortedTrips = new java.util.ArrayList<>(BusyBreak.trips);
        sortedTrips.sort(Comparator.comparing(t -> t.getStartDateTime().getDateTime()));

        for (int i = 0; i < sortedTrips.size() - 1; i++) {
            Trip current = sortedTrips.get(i);
            Trip next = sortedTrips.get(i + 1);

            if (next.getStartDateTime().getDateTime().isBefore(current.getEndDateTime().getDateTime())) {
                return true;
            }
        }
        return false;
    }
}
