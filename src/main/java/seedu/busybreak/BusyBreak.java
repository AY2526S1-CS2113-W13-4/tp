package seedu.busybreak;

import seedu.busybreak.activity.BudgetPlan;
import seedu.busybreak.command.Add;
import seedu.busybreak.command.Budget;
import seedu.busybreak.command.List;
import seedu.busybreak.command.Schedule;
import seedu.busybreak.storage.Storage;
import seedu.busybreak.storage.Load;
import seedu.busybreak.command.Clear;
import seedu.busybreak.command.Delete;
import seedu.busybreak.command.Edit;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Handler;

public class BusyBreak {

    public static final String LINE = "______________________________________________________________________";
    public static ArrayList<Activity> list = new ArrayList<>();
    public static BudgetPlan budgetPlan = new BudgetPlan();
    public static Ui ui = new Ui();
    private static Logger logger = Logger.getLogger(BusyBreak.class.getName());
    private static Storage storage = new Storage();

    public static Storage getStorage() {
        return storage;
    }

    public static String handleUserInput() {
        Parser.GetCommand parsedCommand = Parser.parseUserInput();
        if (parsedCommand == null) {
            return null;
        }
        String[] userInput = parsedCommand.userInputArray();
        switch (parsedCommand.command()) {
        case "exit":
            ui.showTerminateProgram();
            System.exit(0);
            break;
        case "list": //list out all items
            List.listItems();
            break;
        case "add": //add itinerary entry
            Add.addActivityDataToList(userInput);
            break;
        case "schedule":
            Schedule.setByTime();
            break;
        case "view":
            view(userInput);
            break;
        case "delete":
            Delete.deleteActivityDataFromList(userInput);
            break;
        case "edit":
            Edit.editActivityDataInList(userInput);
            break;
        case "budget":
            Budget.handleBudget(userInput);
            break;
        case "breakdown":
            budgetPlan.listByCategory();
            break;
        case "clear":
            Clear.handleClearCommand(userInput);
            break;
        default:
            Ui.invalidInput();
        }
        return parsedCommand.userInput();
    }

    private static void editActivityDataInList(String[] userInputArray) {
        logger.log(Level.INFO, "Editing Activity " + userInputArray[0]);
        String[] parsedEditedInputArray = Arrays.copyOfRange(userInputArray, 1, userInputArray.length);

        try {
            int index = Integer.parseInt(parsedEditedInputArray[0]) - 1;
            assert parsedEditedInputArray.length == userInputArray.length - 1 :
                    "Parsed input must not have the command";
            assert index >= 0 && index < list.size() : "Index out of bounds";

            if (index < 0 || index >= list.size()) {
                System.out.println(LINE);
                System.out.println("Invalid index. Please provide a valid activity number.");
                System.out.println(LINE);
                return;
            }

            Parser.ParseActivityData editedActivityData = Parser.getParseActivityData(parsedEditedInputArray);
            assert editedActivityData != null : "Parsed activity data cannot be null";

            list.set(index, new Activity(editedActivityData.date(), editedActivityData.time(),
                    editedActivityData.description(), editedActivityData.cost()));

            Activity updatedActivity = list.get(index);
            assert updatedActivity.getDate().equals(editedActivityData.date()) : "Date must match edited date";
            assert updatedActivity.getTime().equals(editedActivityData.time()) : "Time must match edited time";
            assert updatedActivity.getDescription().equals(editedActivityData.description()) : "Description " +
                    "must match edited description";
            assert updatedActivity.getCost().equals(editedActivityData.cost()) : "Date must match edited date";

            System.out.println(LINE);
            System.out.println("Activity " + parsedEditedInputArray[0]
                    + " has been edited with the following details:");
            System.out.print("Date: " + editedActivityData.date() + "|");
            System.out.print("Time: " + editedActivityData.time() + "|");
            System.out.print("Description: " + editedActivityData.description() + "|");
            System.out.println("Cost: $" + editedActivityData.cost());
            System.out.println(LINE);

            storage.saveActivities();

        } catch (NumberFormatException e) {
            System.out.println(LINE);
            System.out.println("Invalid index format. Please provide a valid number.");
            System.out.println(LINE);
        }
    }

    private static void deleteActivityDataFromList(String[] userInputArray) {
        assert userInputArray.length >= 2 : "User input array must have at least 2 elements";

        try {
            int index = Integer.parseInt(userInputArray[1]) - 1;

            logger.log(Level.INFO, "Deleting Activity " + userInputArray[0] + " from the list.");
            assert index >= 0 && index < list.size() : "Index out of bounds";
            if (index < 0 || index >= list.size()) {
                System.out.println(LINE);
                System.out.println("Invalid index. Please provide a valid activity number.");
                System.out.println(LINE);
                return;
            }

            Activity deletedActivity = list.get(index);
            assert deletedActivity != null : "Activity " + userInputArray[0] + " cannot be null";

            int originalSize = list.size();
            budgetPlan.removeActivityExpense(deletedActivity.getDescription(), deletedActivity.getCost());
            list.remove(index);
            assert list.size() == originalSize - 1 : "The list size should decrease by 1 after deletion";

            System.out.println(LINE);
            System.out.println("Deleted activity from Itinerary: ");
            System.out.println((index + 1) + ". " + deletedActivity.getDescription());
            System.out.println(LINE);

            storage.saveActivities();
            storage.saveBudgets();

        } catch (NumberFormatException e) {
            System.out.println(LINE);
            System.out.println("Invalid index format. Please provide a valid number.");
            System.out.println(LINE);
        }
    }

    private static void view(String[] userInputArray) {
        assert userInputArray != null : "Input cannot be null";
        if (userInputArray.length != 2) {
            logger.log(Level.WARNING, "Invalid command format for view");
            ui.showInvalidViewFormat();
            return;
        }
        String date = userInputArray[1].trim();
        assert !date.isEmpty() : "Date cannot be empty";
        logger.log(Level.INFO, "Date: " + date);

        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            logger.log(Level.WARNING, "Invalid date: " + date);
            ui.showInvalidDate(date);
            return;
        }
        if (list.isEmpty()) {
            logger.log(Level.INFO, "Itinerary is currently empty");
            ui.showEmptyItinerary();
            return;
        }
        ArrayList<Activity> matches = new ArrayList<>();
        for (Activity a : list) {
            if (date.equals(a.getDate())) {
                matches.add(a);
            }
        }
        if (matches.isEmpty()) {
            logger.log(Level.INFO, "No activities for " + date);
            ui.showNoActivitiesFor(date);
            return;
        }
        ui.showItineraryFor(date, matches);
    }

    public static void main(String[] args) {
        // Configure the log level to WARNING to output only warnings and errors
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.WARNING);
        for (Handler handler : rootLogger.getHandlers()) {
            handler.setLevel(Level.WARNING);
        }

        ui.showWelcome();
        Load loader = new Load();
        loader.loadActivities();
        loader.loadBudgets();
        while (true) {
            String userInput = handleUserInput();
            //detect EOF
            if (userInput == null) {
                break;
            }
        }
    }

}

