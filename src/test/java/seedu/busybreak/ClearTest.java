package seedu.busybreak;

import seedu.busybreak.activity.Activity;
import seedu.busybreak.activity.Trip;
import seedu.busybreak.command.Clear;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClearTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() throws Exception {
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Field listField = BusyBreak.class.getDeclaredField("list");
        listField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Activity> list = (ArrayList<Activity>) listField.get(null);
        list.clear();

        Field tripsField = BusyBreak.class.getDeclaredField("trips");
        tripsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Trip> trips = (ArrayList<Trip>) tripsField.get(null);
        trips.clear();
    }

    @Test
    public void handleClearCommand_clearActivities_success() throws Exception {
        Field listField = BusyBreak.class.getDeclaredField("list");
        listField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Activity> list = (ArrayList<Activity>) listField.get(null);
        list.add(new Activity("2025-01-01", "10:00", "Test", "50"));

        String[] input = {"clear"};
        Clear.handleClearCommand(input);

        assertTrue(outputStream.toString().contains("All activities have been cleared."));
        assertEquals(0, list.size());
    }

    @Test
    public void handleClearCommand_clearTrips_success() throws Exception {
        Field tripsField = BusyBreak.class.getDeclaredField("trips");
        tripsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Trip> trips = (ArrayList<Trip>) tripsField.get(null);
        trips.add(new Trip("2025-01-01", "08:00",
                "2025-01-02", "18:00", "flight"));

        String[] input = {"clear", "trip"};
        Clear.handleClearCommand(input);

        assertTrue(outputStream.toString().contains("All trips have been cleared."));
        assertEquals(0, trips.size());
    }

    @Test
    public void handleClearCommand_clearBeforeDate_success() {
        String[] input = {"clear", "before", "2025-01-01"};
        Clear.handleClearCommand(input);
        assertTrue(outputStream.toString().contains("Cleared 0 activities" +
                " and 0 trips before or on 2025-01-01."));
    }

    @Test
    public void handleClearCommand_clearAll_success() {
        String[] input = {"clear", "all"};
        Clear.handleClearCommand(input);
        assertTrue(outputStream.toString().contains("All activities," +
                " budget entries and trips have been cleared."));
    }

    @Test
    public void handleClearCommand_invalidFormat_failure() {
        String[] input = {"clear", "invalid"};
        Clear.handleClearCommand(input);
        assertTrue(outputStream.toString().contains("Invalid clear command." +
                " Please use:"));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }
}
