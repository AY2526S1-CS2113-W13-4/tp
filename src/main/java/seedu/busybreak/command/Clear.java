package seedu.busybreak.command;

import seedu.busybreak.BusyBreak;
import seedu.busybreak.activity.Activity;
import seedu.busybreak.storage.Storage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

//@@author msc-123456
/**
 * Handles data clearing commands,
 * supporting clearing activities, budgets, trips, or by date range.
 */
public class Clear {
    private static final String LINE = BusyBreak.LINE;
    private static final String CORRECT_FORMAT = "Invalid clear command. Please use:\n"
            + "clear - Clear all activities\n"
            + "clear budget - Clear all budget entries\n"
            + "clear trip - Clear all trips\n"
            + "clear all - Clear all activities, budget and trips\n"
            + "clear before yyyy-MM-dd - Clear activities before the specified date (inclusive)";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Storage storage = BusyBreak.getStorage();

    /**
     * Handles user input for clear commands,
     * executing corresponding clearing operations based on command type.
     * @param userInputArray Split user input array
     */
    public static void handleClearCommand(String[] userInputArray) {
        if (userInputArray.length == 1) {
            clearActivities(true);
        } else if (userInputArray.length == 2) {
            switch (userInputArray[1].toLowerCase()) {
            case "budget":
                clearBudget(true);
                break;
            case "trip":
                clearTrips(true);
                break;
            case "all":
                clearAll();
                break;
            default:
                printInvalidFormat();
            }
        } else if (userInputArray.length == 3 && "before".equals(userInputArray[1].toLowerCase())) {
            clearBeforeDate(userInputArray[2]);
        } else {
            printInvalidFormat();
        }
    }

    private static void clearActivities(boolean printMessage) {
        List<Activity> activitiesToRemove = new ArrayList<>(BusyBreak.list);
        for (Activity activity : activitiesToRemove) {
            BusyBreak.budgetPlan.removeActivityExpense(activity.getDescription(), activity.getCost());
        }

        BusyBreak.list.clear();
        storage.saveActivities();
        storage.saveBudgets();
        if (printMessage) {
            System.out.println(LINE);
            System.out.println("All activities have been cleared.");
            System.out.println(LINE);
        }
    }

    private static void clearBudget(boolean printMessage) {
        BusyBreak.budgetPlan.names.clear();
        BusyBreak.budgetPlan.amounts.clear();
        BusyBreak.budgetPlan.categories.clear();
        BusyBreak.budgetPlan.setBudget(0);
        storage.saveBudgets();
        if (printMessage) {
            System.out.println(LINE);
            System.out.println("All budget entries have been cleared.");
            System.out.println(LINE);
        }
    }

    private static void clearTrips(boolean printMessage) {
        BusyBreak.trips.clear();
        storage.saveTrips();
        if (printMessage) {
            System.out.println(LINE);
            System.out.println("All trips have been cleared.");
            System.out.println(LINE);
        }
    }

    private static void clearAll() {
        clearActivities(false);
        clearBudget(false);
        clearTrips(false);
        System.out.println(LINE);
        System.out.println("All activities, budget entries and trips have been cleared.");
        System.out.println(LINE);
    }

    private static void clearBeforeDate(String dateStr) {
        try {
            LocalDate targetDate = LocalDate.parse(dateStr, DATE_FORMATTER);
            int initialActivitySize = BusyBreak.list.size();
            int initialTripSize = BusyBreak.trips.size();

            List<Activity> activitiesToRemove = new ArrayList<>();
            for (Activity activity : BusyBreak.list) {
                LocalDate activityDate = activity.getDateTimeObject().getDate();
                if (!activityDate.isAfter(targetDate)) {
                    activitiesToRemove.add(activity);
                }
            }

            for (Activity activity : activitiesToRemove) {
                BusyBreak.budgetPlan.removeActivityExpense(activity.getDescription(), activity.getCost());
            }

            BusyBreak.list.removeAll(activitiesToRemove);

            BusyBreak.trips.removeIf(trip -> {
                LocalDate tripStartDate = trip.getStartDateTime().getDate();
                return !tripStartDate.isAfter(targetDate);
            });

            int removedActivities = initialActivitySize - BusyBreak.list.size();
            int removedTrips = initialTripSize - BusyBreak.trips.size();
            storage.saveActivities();
            storage.saveBudgets();
            storage.saveTrips();

            System.out.println(LINE);
            System.out.printf("Cleared %d activities and %d trips before or on %s.%n",
                    removedActivities, removedTrips, dateStr);
            System.out.println(LINE);
        } catch (DateTimeParseException e) {
            System.out.println(LINE);
            System.out.println("Invalid date. Please use valid date in format yyyy-MM-dd.");
            System.out.println(LINE);
        }
    }

    private static void printInvalidFormat() {
        System.out.println(LINE);
        System.out.println(CORRECT_FORMAT);
        System.out.println(LINE);
    }
}
