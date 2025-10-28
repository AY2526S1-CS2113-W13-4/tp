package seedu.busybreak.command;

import seedu.busybreak.BusyBreak;
import seedu.busybreak.Ui;
import seedu.busybreak.parser.Parser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Find {
    private static Logger logger = Logger.getLogger(Find.class.getName());

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
