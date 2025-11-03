package seedu.busybreak;

import seedu.busybreak.activity.Activity;
import seedu.busybreak.parser.Parser;

import java.util.ArrayList;


public class Ui {

    private static final String LINE = "______________________________________________________________________";

    /**
     * Prints invalid command error message.
     */
    static void invalidInput() {
        showLine();
        System.out.println("Invalid Command.");
        showLine();
    }

    /**
     * Prints all items in the itinerary.
     */
    public static void printListItems() {
        if (BusyBreak.list.isEmpty()) {
            showLine();
            System.out.println("Itinerary is Empty!");
            showLine();
            return;
        }
        showLine();
        for (int index = 0; index < BusyBreak.list.size(); index++) {
            printItems(index);
        }
    }

    /**
     * Prints a single item at specified index.
     *
     * @param index index to be printed
     */
    public static void printItems(int index) {
        System.out.println((index + 1) + ". ");
        System.out.println("Date: " + BusyBreak.list.get(index).getDate());
        System.out.println("Time: " + BusyBreak.list.get(index).getTime());
        System.out.println("Description: " + BusyBreak.list.get(index).getDescription());
        System.out.println("Cost: $" + BusyBreak.list.get(index).getCost());
        showLine();
    }

    /**
     * Displays confirmation message for item to be added to itinerary.
     *
     * @param activityData the parsed activity data with its relevant fields information.
     */
    public static void printAddedItem(Parser.ParseActivityData activityData) {
        showLine();
        System.out.print("Added Activity to Itinerary: ");
        System.out.print("Date: " + activityData.date() + " | ");
        System.out.print("Time: " + activityData.time() + " | ");
        System.out.print("Description: " + activityData.description() + " | ");
        System.out.println("Cost: $" + activityData.cost());
        showLine();
    }

    public static void showLine() {
        System.out.println(LINE);
    }

    /**
     * Prints header message before displaying results.
     */
    public static void printFindHeaderMessage() {
        showLine();
        System.out.println("Here are the activities matching your keyword");
        showLine();
    }

    /**
     * Prints message when no items with keyword found.
     */
    public static void printNoItemsFound() {
        System.out.println("No items found!");
        showLine();
    }

    /**
     * Prints when itinerary is empty.
     */
    public static void printItineraryEmpty() {
        showLine();
        System.out.println("Your itinerary is empty!");
        showLine();
    }

    public void showWelcome() {
        showLine();
        System.out.println("Welcome to BusyBreak, your helpful travel assistant! How may I assist you?");
        showLine();
    }

    public void showTerminateProgram() {
        showLine();
        System.out.println("Program Terminated.");
        showLine();
    }

    // view function
    public static void showInvalidDate(String date) {
        showLine();
        System.out.println("Invalid date: \"" + date + "\"");
        System.out.println("Please use the command in the following format: view YYYY-MM-DD");
        showLine();
    }

    // view function
    public static void showEmptyItinerary() {
        showLine();
        System.out.println("Itinerary is Empty!");
        showLine();
    }

    // view function
    public static void showInvalidViewFormat() {
        showLine();
        System.out.println("Invalid command format.");
        System.out.println("Please use the command in the following format: view YYYY-MM-DD");
        showLine();
    }

    // view function
    public static void showNoActivitiesFor(String date) {
        showLine();
        System.out.println("No activities found for " + date + ".");
        showLine();

    }

    // view function
    public static void showItineraryFor(String date, ArrayList<Activity> matches) {
        showLine();
        System.out.println("Itinerary for " + date + ":");
        for (int i = 0; i < matches.size(); i++) {
            Activity a = matches.get(i);
            System.out.println((i + 1) + ". ");
            System.out.println("Date: " + a.getDate());
            System.out.println("Time: " + a.getTime());
            System.out.println("Description: " + a.getDescription());
            System.out.println("Cost: $" + a.getCost());
            showLine();
        }
    }

    //edit function
    public static void showInvalidIndexMessage() {
        showLine();
        System.out.println("Invalid index. Please provide a valid activity number.");
        showLine();
    }

    //edit function
    public static void showInvalidIndexFormatMessage() {
        showLine();
        System.out.println("Invalid index format. Please provide a valid number.");
        showLine();
    }

    //edit function
    public static void showInvalidDetailMessage() {
        showLine();
        System.out.println("Invalid detail detected. Please ensure that the input is in the correct format, and" +
                " that cost is a non-negative number.");
        showLine();
    }

    public static void showEditedActivity(String activityNumber, Activity activity) {
        showLine();
        System.out.println("Activity " + activityNumber + " has been edited with the following details:");
        System.out.print("Date: " + activity.getDate() + " | ");
        System.out.print("Time: " + activity.getTime() + " | ");
        System.out.print("Description: " + activity.getDescription() + " | ");
        System.out.println("Cost: $" + activity.getCost());
        showLine();
    }

    public static void showDeletedActivity(String activityNumber, Activity activity) {
        showLine();
        System.out.println("Deleted activity from Itinerary: ");
        System.out.println(activityNumber + ". " + activity.getDescription());
        showLine();
    }

    // undo function
    public static void showInvalidUndoFormat() {
        showLine();
        System.out.println("Invalid command format. Usage: undo");
        showLine();
    }

    public static void showNothingToUndo() {
        showLine();
        System.out.println("Nothing to undo.");
        showLine();
    }

    public static void showUndoSuccess() {
        showLine();
        System.out.println("Undid the last change.");
        showLine();
    }

    public static void showError(String msg) {
        showLine();
        System.out.println(msg);
        showLine();
    }
}
