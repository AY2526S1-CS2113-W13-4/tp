package seedu.busybreak.command;

import seedu.busybreak.BusyBreak;
import seedu.busybreak.activity.Trip;
import seedu.busybreak.storage.History;
import java.util.Arrays;

//@@author msc-123456
public class TripCommand {
    private static final String LINE = BusyBreak.LINE;
    private static final String CORRECT_FORMAT = "Invalid trip command. Please use:\n"
            + "trip add sd/<startDate> st/<startTime> ed/<endDate> et/<endTime> by/<transport>\n"
            + "trip list - List all trips\n"
            + "trip delete <index> - Delete trip by index";

    public static void handleTripCommand(String[] userInputArray) {
        if (userInputArray.length < 2) {
            printInvalidFormat();
            return;
        }

        String action = userInputArray[1].toLowerCase();

        switch (action) {
        case "add":
            String[] parts = Arrays.copyOfRange(userInputArray, 2, userInputArray.length);
            addTrip(parts);
            break;

        case "list":
            listTrips();
            break;

        case "delete":
            if (userInputArray.length >= 3) {
                deleteTrip(userInputArray[2]);
            } else {
                System.out.println(LINE);
                System.out.println("Please specify an index to delete");
                System.out.println(LINE);
            }
            break;

        default:
            printInvalidFormat();
        }
    }

    private static void addTrip(String[] parts) {
        try {
            History.checkpointWithSave(BusyBreak.getStorage());

            String input = String.join(" ", parts);
            String startDate = extractField(input, "sd/");
            String startTime = extractField(input, "st/");
            String endDate = extractField(input, "ed/");
            String endTime = extractField(input, "et/");
            String transport = extractField(input, "by/");

            if (startDate == null || startDate.trim().isEmpty() ||
                    startTime == null || startTime.trim().isEmpty() ||
                    endDate == null || endDate.trim().isEmpty() ||
                    endTime == null || endTime.trim().isEmpty() ||
                    transport == null || transport.trim().isEmpty()) {
                throw new IllegalArgumentException("Missing or empty fields. Required: sd/, st/, ed/, et/, by/ (all must have values)");
            }

            Trip trip = new Trip(startDate, startTime, endDate, endTime, transport);
            BusyBreak.trips.add(trip);
            BusyBreak.getStorage().saveTrips();

            System.out.println(LINE);
            System.out.println("Added Trip:");
            System.out.printf("Start: %s %s | End: %s %s | Transport: %s%n",
                    trip.getStartDate(), trip.getStartTime(), trip.getEndDate(), trip.getEndTime(),
                    trip.getTransport());
            System.out.println(LINE);
        } catch (IllegalArgumentException e) {
            System.out.println(LINE);
            System.out.println("Error: " + e.getMessage());
            System.out.println(LINE);
        }
    }

    private static String extractField(String input, String prefix) {
        String[] tokens = input.split("\\s+");
        for (String token : tokens) {
            if (token.startsWith(prefix)) {
                return token.substring(prefix.length());
            }
        }
        return null;
    }

    public static void listTrips() {
        System.out.println(LINE);
        if (BusyBreak.trips.isEmpty()) {
            System.out.println("No trips recorded yet.");
        } else {
            for (int i = 0; i < BusyBreak.trips.size(); i++) {
                Trip trip = BusyBreak.trips.get(i);
                System.out.printf("%d. Start: %s %s | End: %s %s | Transport: %s%n",
                        i + 1,
                        trip.getStartDate(), trip.getStartTime(),
                        trip.getEndDate(), trip.getEndTime(),
                        trip.getTransport());
            }
        }
        System.out.println(LINE);
    }

    private static void deleteTrip(String indexStr) {
        try {
            History.checkpointWithSave(BusyBreak.getStorage());

            int index = Integer.parseInt(indexStr) - 1;
            if (index < 0 || index >= BusyBreak.trips.size()) {
                System.out.println(LINE);
                System.out.println("Invalid index");
                System.out.println(LINE);
                return;
            }

            Trip removed = BusyBreak.trips.remove(index);
            BusyBreak.getStorage().saveTrips();

            System.out.println(LINE);
            System.out.println("Deleted Trip:");
            System.out.printf("Start: %s %s | End: %s %s | Transport: %s%n",
                    removed.getStartDate(), removed.getStartTime(),
                    removed.getEndDate(), removed.getEndTime(),
                    removed.getTransport());
            System.out.println(LINE);

        } catch (NumberFormatException e) {
            System.out.println(LINE);
            System.out.println("Invalid index format. Please use a number.");
            System.out.println(LINE);
        }
    }

    private static void printInvalidFormat() {
        System.out.println(LINE);
        System.out.println(CORRECT_FORMAT);
        System.out.println(LINE);
    }
}

