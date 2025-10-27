package seedu.busybreak.storage;

import seedu.busybreak.Activity;
import seedu.busybreak.BusyBreak;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

//@@author msc-123456
public class Load {
    private static final Logger logger = Logger.getLogger(Load.class.getName());
    private static final String ACTIVITIES_FILE = "data/activities.txt";
    private static final String BUDGETS_FILE = "data/budgets.txt";
    private static final String TRIPS_FILE = "data/trips.txt";
    private int invalidActivityCount = 0;
    private int invalidBudgetCount = 0;
    private int invalidTripCount = 0;

    public void loadActivities() {
        File file = new File(ACTIVITIES_FILE);
        if (!file.exists()) {
            logger.log(Level.INFO, "No activities file found, starting with empty list");
            return;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(ACTIVITIES_FILE));
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|", -1);
                if (parts.length != 4) {
                    invalidActivityCount++;
                    continue;
                }

                try {
                    BusyBreak.list.add(new Activity(parts[0], parts[1], parts[2], parts[3]));
                } catch (Exception e) {
                    invalidActivityCount++;
                }
            }
            logger.log(Level.INFO, "Loaded " + BusyBreak.list.size() + " activities");
            if (invalidActivityCount > 0) {
                System.out.println("Warning: Removed " + invalidActivityCount + " invalid activities");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load activities", e);
        }
    }

    public void loadBudgets() {
        File file = new File(BUDGETS_FILE);
        if (!file.exists()) {
            logger.log(Level.INFO, "No budgets file found, starting with empty budget");
            return;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(BUDGETS_FILE));
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|", -1);
                if (parts.length < 1) {
                    invalidBudgetCount++;
                    continue;
                }

                try {
                    if (parts[0].equals("BUDGET") && parts.length == 2) {
                        BusyBreak.budgetPlan.setBudget(Double.parseDouble(parts[1]));
                    } else if (parts[0].equals("EXPENSE") && parts.length == 4) {
                        BusyBreak.budgetPlan.addExpense(parts[1], parts[2], parts[3]);
                    } else {
                        invalidBudgetCount++;
                    }
                } catch (Exception e) {
                    invalidBudgetCount++;
                }
            }
            logger.log(Level.INFO, "Loaded budget and expenses");
            if (invalidBudgetCount > 0) {
                System.out.println("Warning: Removed " + invalidBudgetCount + " invalid budget entries");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load budgets", e);
        }
    }

    public void loadTrips() {
        File file = new File(TRIPS_FILE);
        if (!file.exists()) {
            logger.log(Level.INFO, "No trips file found, starting with empty trips list");
            return;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(TRIPS_FILE));
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|", -1);
                if (parts.length != 5) {
                    invalidTripCount++;
                    continue;
                }

                try {
                    BusyBreak.trips.add(new seedu.busybreak.activity.Trip(
                            parts[0], parts[1], parts[2], parts[3], parts[4]));
                } catch (Exception e) {
                    invalidTripCount++;
                }
            }
            logger.log(Level.INFO, "Loaded " + BusyBreak.trips.size() + " trips");
            if (invalidTripCount > 0) {
                System.out.println("Warning: Removed " + invalidTripCount + " invalid trips");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load trips", e);
        }
    }
}
