package seedu.busybreak.command;

import seedu.busybreak.BusyBreak;
import seedu.busybreak.Ui;
import seedu.busybreak.parser.Parser;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the searching of activities in itinerary by keyword.
 * Performs non-case-sensitive search on activity descriptions in itinerary.
 */

public class Find {
    private static Logger logger = Logger.getLogger(Find.class.getName());

    /**
     * Searches for activity in itinerary that match keyword.
     * Displays matching activities if found.
     * Displays specific message if none are found.
     *
     * @param userInput String containing command and keyword.
     */
    public static void searchByKeyword(String[] userInput) {
        assert userInput != null : "user input cannot be null";
        if (BusyBreak.list.isEmpty()) {
            Find.logger.log(Level.INFO, "Attempted search on empty itinerary");
            Ui.printItineraryEmpty();
            return;
        }
        boolean itemsFound = false;
        Ui.printFindHeaderMessage();
        itemsFound = isItemsFound(userInput, itemsFound);
        if (!itemsFound) {
            Find.logger.log(Level.INFO, "No activities found matching keyword");
            Ui.printNoItemsFound();
        }
    }

    /**
     * Checks if activity in itinerary matches keyword.
     * Prints all matching activities.
     *
     * @param userInput  String containing command and keyword.
     * @param itemsFound true if at least one matching activity found.
     * @return true if at least one item is found, false if none found.
     */
    public static boolean isItemsFound(String[] userInput, boolean itemsFound) {
        String parsedUserInput = Parser.parseFindInput(userInput);
        for (int index = 0; index < BusyBreak.list.size(); index++) {
            if (BusyBreak.list.get(index).getDescription().toLowerCase().contains(parsedUserInput.toLowerCase())) {
                Ui.printItems(index);
                itemsFound = true;
            }
        }
        return itemsFound;
    }

}
