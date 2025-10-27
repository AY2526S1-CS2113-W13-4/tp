package seedu.busybreak;

import seedu.busybreak.activity.Trip;
import seedu.busybreak.command.TripCommand;
import seedu.busybreak.activity.BudgetPlan;
import seedu.busybreak.command.Add;
import seedu.busybreak.command.Budget;
import seedu.busybreak.command.List;
import seedu.busybreak.command.Schedule;
import seedu.busybreak.storage.Storage;
import seedu.busybreak.storage.Load;
import seedu.busybreak.command.Clear;
import seedu.busybreak.command.Check;
import seedu.busybreak.command.Delete;
import seedu.busybreak.command.Edit;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Handler;

public class BusyBreak {

    public static final String LINE = "______________________________________________________________________";
    public static ArrayList<Activity> list = new ArrayList<>();
    public static ArrayList<Trip> trips = new ArrayList<>();
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
            if (userInput.length > 1 && "trip".equals(userInput[1].toLowerCase())) {
                Schedule.sortTrips();
            } else {
                Schedule.setByTime();
            }
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
        case "trip":
            TripCommand.handleTripCommand(userInput);
            break;
        case "clear":
            Clear.handleClearCommand(userInput);
            break;
        case "check":
            Check.handleCheckCommand(userInput);
            break;
        default:
            Ui.invalidInput();
        }
        return parsedCommand.userInput();
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
        budgetPlan.syncFromActivities(list);
        storage.saveBudgets();
        loader.loadTrips();
        while (true) {
            String userInput = handleUserInput();
            //detect EOF
            if (userInput == null) {
                break;
            }
        }
    }

}

