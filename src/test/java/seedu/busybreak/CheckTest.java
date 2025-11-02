package seedu.busybreak;

import seedu.busybreak.activity.Activity;
import seedu.busybreak.activity.Trip;
import seedu.busybreak.command.Check;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckTest {
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
    public void handleCheckCommand_validRange_withResults() throws Exception {
        Field listField = BusyBreak.class.getDeclaredField("list");
        listField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Activity> list = (ArrayList<Activity>) listField.get(null);
        list.add(new Activity("2025-01-02", "10:00",
                "Test Activity", "50"));


        Field tripsField = BusyBreak.class.getDeclaredField("trips");
        tripsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Trip> trips = (ArrayList<Trip>) tripsField.get(null);
        trips.add(new Trip("2025-01-02", "08:00",
                "2025-01-02", "18:00", "car"));

        String[] input = {"check", "from/2025-01-01", "to/2025-01-03"};
        Check.handleCheckCommand(input);

        String output = outputStream.toString();
        assertTrue(output.contains("Results between 2025-01-01" +
                " and 2025-01-03 (inclusive):"));
        assertTrue(output.contains("Test Activity"));
        assertTrue(output.contains("car"));
    }

    @Test
    public void handleCheckCommand_validRange_noResults() {
        String[] input = {"check", "from/2025-01-01", "to/2025-01-03"};
        Check.handleCheckCommand(input);

        String output = outputStream.toString();
        assertTrue(output.contains("No activities found."));
        assertTrue(output.contains("No trips found."));
    }

    @Test
    public void handleCheckCommand_invalidDateOrder_failure() {
        String[] input = {"check", "from/2025-01-05", "to/2025-01-03"};
        Check.handleCheckCommand(input);
        assertTrue(outputStream.toString().contains("Start date" +
                " cannot be later than end date."));
    }

    @Test
    public void handleCheckCommand_invalidFormat_failure() {
        String[] input = {"check", "invalid"};
        Check.handleCheckCommand(input);
        assertTrue(outputStream.toString().contains("Invalid check command. " +
                "Please use:"));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }
}
