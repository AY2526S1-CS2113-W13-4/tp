package seedu.busybreak.storage;

import seedu.busybreak.BusyBreak;
import seedu.busybreak.activity.Activity;
import seedu.busybreak.activity.BudgetPlan;
import seedu.busybreak.activity.Trip;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Load {
    private static final Logger logger = Logger.getLogger(Load.class.getName());
    private static final String ACTIVITIES_FILE = "data/activities.txt";
    private static final String BUDGETS_FILE = "data/budgets.txt";
    private static final String TRIPS_FILE = "data/trips.txt";

    public void loadActivities() {
        BusyBreak.list.clear();
        int invalid = 0;

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
                String[] p = line.split("\\|", -1);
                if (p.length != 4) {
                    invalid++;
                    continue;
                }
                try {
                    BusyBreak.list.add(new Activity(p[0], p[1], p[2], p[3]));
                } catch (Exception e) {
                    invalid++;
                }
            }
            logger.log(Level.INFO, "Loaded " + BusyBreak.list.size() + " activities");
            if (invalid > 0) {
                System.out.println("Warning: Removed " + invalid + " invalid activities");
                BusyBreak.getStorage().saveActivities();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load activities", e);
        }
    }

    public void loadBudgets() {
        BusyBreak.budgetPlan = new BudgetPlan();
        int invalid = 0;

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
                String[] p = line.split("\\|", -1);
                if (p.length < 1) {
                    invalid++;
                    continue;
                }
                try {
                    if ("BUDGET".equals(p[0]) && p.length == 2) {
                        BusyBreak.budgetPlan.setBudget(Double.parseDouble(p[1]));
                    } else if ("EXPENSE".equals(p[0]) && p.length == 4) {
                        BusyBreak.budgetPlan.addExpense(p[1], p[2], p[3]);
                    } else {
                        invalid++;
                    }
                } catch (Exception e) {
                    invalid++;
                }
            }
            logger.log(Level.INFO, "Loaded budget and expenses");
            if (invalid > 0) {
                System.out.println("Warning: Removed " + invalid + " invalid budget entries");
                BusyBreak.getStorage().saveBudgets();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load budgets", e);
        }
    }

    public void loadTrips() {
        BusyBreak.trips.clear();
        int invalid = 0;

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
                String[] p = line.split("\\|", -1);
                if (p.length != 5) {
                    invalid++;
                    continue;
                }
                try {
                    BusyBreak.trips.add(new Trip(p[0], p[1], p[2], p[3], p[4]));
                } catch (Exception e) {
                    invalid++;
                }
            }
            logger.log(Level.INFO, "Loaded " + BusyBreak.trips.size() + " trips");
            if (invalid > 0) {
                System.out.println("Warning: Removed " + invalid + " invalid trips");
                BusyBreak.getStorage().saveTrips();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load trips", e);
        }
    }
}

