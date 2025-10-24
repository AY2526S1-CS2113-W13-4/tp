package seedu.busybreak.command;

import seedu.busybreak.BusyBreak;
import seedu.busybreak.storage.Storage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//@@author msc-123456
public class Clear {
    private static final String LINE = BusyBreak.LINE;
    private static final String CORRECT_FORMAT = "Invalid clear command. Please use:\n"
            + "clear - Clear all activities\n"
            + "clear budget - Clear all budget entries\n"
            + "clear all - Clear all activities and budget\n"
            + "clear before yyyy-MM-dd - Clear activities before the specified date (inclusive)";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Storage storage = BusyBreak.getStorage();

    public static void handleClearCommand(String[] userInputArray) {
        if (userInputArray.length == 1) {
            clearActivities();
        } else if (userInputArray.length == 2) {
            switch (userInputArray[1].toLowerCase()) {
            case "budget":
                clearBudget();
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

    private static void clearActivities() {
        BusyBreak.list.clear();
        storage.saveActivities();
        System.out.println(LINE);
        System.out.println("All activities have been cleared.");
        System.out.println(LINE);
    }

    private static void clearBudget() {
        BusyBreak.budgetPlan.names.clear();
        BusyBreak.budgetPlan.amounts.clear();
        BusyBreak.budgetPlan.categories.clear();
        BusyBreak.budgetPlan.setBudget(0);
        storage.saveBudgets();
        System.out.println(LINE);
        System.out.println("All budget entries have been cleared.");
        System.out.println(LINE);
    }

    private static void clearAll() {
        clearActivities();
        clearBudget();
        System.out.println(LINE);
        System.out.println("All activities and budget entries have been cleared.");
        System.out.println(LINE);
    }

    private static void clearBeforeDate(String dateStr) {
        try {
            LocalDate targetDate = LocalDate.parse(dateStr, DATE_FORMATTER);
            int initialSize = BusyBreak.list.size();

            BusyBreak.list.removeIf(activity -> {
                LocalDate activityDate = activity.getDateTimeObject().getDate();
                return !activityDate.isAfter(targetDate);
            });

            int removedCount = initialSize - BusyBreak.list.size();
            storage.saveActivities();

            System.out.println(LINE);
            System.out.printf("Cleared %d activities before or on %s.%n", removedCount, dateStr);
            System.out.println(LINE);
        } catch (DateTimeParseException e) {
            System.out.println(LINE);
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            System.out.println(LINE);
        }
    }

    private static void printInvalidFormat() {
        System.out.println(LINE);
        System.out.println(CORRECT_FORMAT);
        System.out.println(LINE);
    }
}
