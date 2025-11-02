package seedu.busybreak;

import seedu.busybreak.activity.Trip;
import seedu.busybreak.command.Schedule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleTripTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() throws Exception {
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Field tripsField = BusyBreak.class.getDeclaredField("trips");
        tripsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Trip> trips = (ArrayList<Trip>) tripsField.get(null);
        trips.clear();
    }

    @Test
    public void sortTrips_noConflicts_success() throws Exception {
        Field tripsField = BusyBreak.class.getDeclaredField("trips");
        tripsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Trip> trips = (ArrayList<Trip>) tripsField.get(null);

        trips.add(new Trip("2025-01-03", "08:00",
                "2025-01-03", "12:00", "train"));
        trips.add(new Trip("2025-01-01", "08:00",
                "2025-01-01", "18:00", "flight"));
        trips.add(new Trip("2025-01-02", "08:00",
                "2025-01-02", "10:00", "car"));

        Schedule.sortTrips();

        String output = outputStream.toString();
        assertTrue(output.contains("Your trips are sorted by time now!"));

        assertTrue(output.indexOf("2025-01-01") < output.indexOf("2025-01-02"));
        assertTrue(output.indexOf("2025-01-02") < output.indexOf("2025-01-03"));
    }

    @Test
    public void sortTrips_withConflicts_failure() throws Exception {
        Field tripsField = BusyBreak.class.getDeclaredField("trips");
        tripsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Trip> trips = (ArrayList<Trip>) tripsField.get(null);

        trips.add(new Trip("2025-01-01", "08:00",
                "2025-01-01", "18:00", "flight"));
        trips.add(new Trip("2025-01-01", "10:00",
                "2025-01-01", "12:00", "car"));

        Schedule.sortTrips();
        assertTrue(outputStream.toString().contains("Cannot sort trips:" +
                " Time conflicts detected between trips."));
    }

    @Test
    public void sortTrips_emptyList_noAction() {
        Schedule.sortTrips();
        assertTrue(outputStream.toString().contains("No trips to sort!"));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }
}
