package seedu.busybreak.command;

import seedu.busybreak.Activity;
import seedu.busybreak.BusyBreak;
import seedu.busybreak.activity.Trip;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

//@@author msc-123456
public class Check {
    private static final String LINE = BusyBreak.LINE;
    private static final String CORRECT_FORMAT = "Invalid check command. Please use:\n"
            + "check from/<yyyy-MM-dd> to/<yyyy-MM-dd>\n"
            + "to check activities and trips between two dates (inclusive)";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void handleCheckCommand(String[] userInputArray) {
        if (userInputArray.length != 3) {
            printInvalidFormat();
            return;
        }

        String fromStr = extractDate(userInputArray[1], "from/");
        String toStr = extractDate(userInputArray[2], "to/");

        if (fromStr == null || toStr == null) {
            printInvalidFormat();
            return;
        }

        try {
            LocalDate fromDate = LocalDate.parse(fromStr, DATE_FORMATTER);
            LocalDate toDate = LocalDate.parse(toStr, DATE_FORMATTER);

            if (fromDate.isAfter(toDate)) {
                System.out.println(LINE);
                System.out.println("Start date cannot be later than end date.");
                System.out.println(LINE);
                return;
            }

            List<Activity> filteredActivities = filterActivities(fromDate, toDate);
            List<Trip> filteredTrips = filterTrips(fromDate, toDate);

            printResults(filteredActivities, filteredTrips, fromStr, toStr);

        } catch (DateTimeParseException e) {
            System.out.println(LINE);
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            System.out.println(LINE);
        }
    }

    private static String extractDate(String input, String prefix) {
        if (input.startsWith(prefix)) {
            return input.substring(prefix.length()).trim();
        }
        return null;
    }

    private static List<Activity> filterActivities(LocalDate fromDate, LocalDate toDate) {
        List<Activity> result = new ArrayList<>();
        for (Activity activity : BusyBreak.list) {
            LocalDate activityDate = LocalDate.parse(activity.getDate(), DATE_FORMATTER);
            if (!activityDate.isBefore(fromDate) && !activityDate.isAfter(toDate)) {
                result.add(activity);
            }
        }
        return result;
    }

    private static List<Trip> filterTrips(LocalDate fromDate, LocalDate toDate) {
        List<Trip> result = new ArrayList<>();
        for (Trip trip : BusyBreak.trips) {
            LocalDate tripStartDate = LocalDate.parse(trip.getStartDate(), DATE_FORMATTER);
            if (!tripStartDate.isBefore(fromDate) && !tripStartDate.isAfter(toDate)) {
                result.add(trip);
            }
        }
        return result;
    }

    private static void printResults(List<Activity> activities, List<Trip> trips, String fromStr, String toStr) {
        System.out.println(LINE);
        System.out.printf("Results between %s and %s (inclusive):%n%n", fromStr, toStr);

        System.out.println("Activities:");
        if (activities.isEmpty()) {
            System.out.println("No activities found.");
        } else {
            for (int i = 0; i < activities.size(); i++) {
                Activity a = activities.get(i);
                System.out.printf("%d. Date: %s | Time: %s | Description: %s | Cost: $%s%n",
                        i + 1, a.getDate(), a.getTime(), a.getDescription(), a.getCost());
            }
        }

        System.out.println("\nTrips:");
        if (trips.isEmpty()) {
            System.out.println("No trips found.");
        } else {
            for (int i = 0; i < trips.size(); i++) {
                Trip t = trips.get(i);
                System.out.printf("%d. Start: %s %s | End: %s %s | Transport: %s%n",
                        i + 1, t.getStartDate(), t.getStartTime(),
                        t.getEndDate(), t.getEndTime(), t.getTransport());
            }
        }
        System.out.println(LINE);
    }

    private static void printInvalidFormat() {
        System.out.println(LINE);
        System.out.println(CORRECT_FORMAT);
        System.out.println(LINE);
    }
}
