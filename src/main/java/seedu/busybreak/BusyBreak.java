package seedu.busybreak;

import seedu.busybreak.activity.Activity;
import seedu.busybreak.activity.Trip;
import seedu.busybreak.command.Find;
import seedu.busybreak.command.TripCommand;
import seedu.busybreak.activity.BudgetPlan;
import seedu.busybreak.command.Add;
import seedu.busybreak.command.Budget;
import seedu.busybreak.command.List;
import seedu.busybreak.command.Schedule;
import seedu.busybreak.parser.Parser;
import seedu.busybreak.storage.Storage;
import seedu.busybreak.storage.Load;
import seedu.busybreak.command.Clear;
import seedu.busybreak.command.Check;
import seedu.busybreak.command.Delete;
import seedu.busybreak.command.Edit;
import seedu.busybreak.command.View; 

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
            View.viewInput(userInput);
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
        case "find":
            Find.searchByKeyword(userInput);
            break;
        default:
            Ui.invalidInput();
        }
        return parsedCommand.userInput();
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

