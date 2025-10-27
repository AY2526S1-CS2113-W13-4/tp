package seedu.busybreak.storage;

import seedu.busybreak.activity.Activity;
import seedu.busybreak.BusyBreak;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.logging.Level;

//@@author msc-123456
public class Storage {
    private static final Logger logger = Logger.getLogger(Storage.class.getName());
    private static final String DATA_FOLDER = "data";
    private static final String ACTIVITIES_FILE = DATA_FOLDER + "/activities.txt";
    private static final String BUDGETS_FILE = DATA_FOLDER + "/budgets.txt";
    private static final String TRIPS_FILE = DATA_FOLDER + "/trips.txt";

    public Storage() {
        try {
            Path dataPath = Paths.get(DATA_FOLDER);
            if (!Files.exists(dataPath)) {
                Files.createDirectories(dataPath);
                logger.log(Level.INFO, "Created data directory");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to create data directory", e);
        }
    }

    public void saveActivities() {
        try (FileWriter writer = new FileWriter(ACTIVITIES_FILE)) {
            for (Activity activity : BusyBreak.list) {
                String line = String.join("|",
                        activity.getDate(),
                        activity.getTime(),
                        activity.getDescription(),
                        activity.getCost());
                writer.write(line + System.lineSeparator());
            }
            logger.log(Level.INFO, "Saved " + BusyBreak.list.size() + " activities");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save activities", e);
        }
    }

    public void saveBudgets() {
        try (FileWriter writer = new FileWriter(BUDGETS_FILE)) {
            writer.write("BUDGET|" + BusyBreak.budgetPlan.getTotalBudget() + System.lineSeparator());

            for (int i = 0; i < BusyBreak.budgetPlan.names.size(); i++) {
                String line = String.join("|",
                        "EXPENSE",
                        BusyBreak.budgetPlan.names.get(i),
                        BusyBreak.budgetPlan.amounts.get(i).toString(),
                        BusyBreak.budgetPlan.categories.get(i));
                writer.write(line + System.lineSeparator());
            }
            logger.log(Level.INFO, "Saved budget and expenses");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save budgets", e);
        }
    }

    public void saveTrips() {
        try (FileWriter writer = new FileWriter(TRIPS_FILE)) {
            for (seedu.busybreak.activity.Trip trip : BusyBreak.trips) {
                String line = String.join("|",
                        trip.getStartDate(),
                        trip.getStartTime(),
                        trip.getEndDate(),
                        trip.getEndTime(),
                        trip.getTransport());
                writer.write(line + System.lineSeparator());
            }
            logger.log(Level.INFO, "Saved " + BusyBreak.trips.size() + " trips");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save trips", e);
        }
    }
}
