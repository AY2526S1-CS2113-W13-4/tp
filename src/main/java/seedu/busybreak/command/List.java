package seedu.busybreak.command;

import seedu.busybreak.Ui;

/**
 * Handles listing of all activities in the itinerary.
 * Displays all stored activities to user with their date,time,description and cost.
 */
public class List {
    /**
     * List all activities in itinerary.
     * Prints all activities, or displays appropriate message
     * if itinerary is empty.
     */
    public static void listItems() {
        Ui.printListItems();
    }
}
