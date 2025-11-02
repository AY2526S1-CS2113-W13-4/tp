package seedu.busybreak;

import seedu.busybreak.activity.Trip;
import seedu.busybreak.command.TripCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TripCommandTest {
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
    public void handleTripCommand_addValidTrip_success() {
        String[] input = {"trip", "add", "sd/2025-01-01", "st/08:00", "ed/2025-01-02", "et/18:00", "by/flight"};
        TripCommand.handleTripCommand(input);

        String output = outputStream.toString();
        assertTrue(output.contains("Added Trip:"));
        assertTrue(output.contains("Start: 2025-01-01 08:00 " +
                "| End: 2025-01-02 18:00 | Transport: flight"));
    }

    @Test
    public void handleTripCommand_addDuplicateTrip_failure() {
        String[] input1 = {"trip", "add", "sd/2025-01-01", "st/08:00", "ed/2025-01-02", "et/18:00", "by/flight"};
        TripCommand.handleTripCommand(input1);

        outputStream.reset();
        String[] input2 = {"trip", "add", "sd/2025-01-01", "st/08:00", "ed/2025-01-02", "et/18:00", "by/flight"};
        TripCommand.handleTripCommand(input2);

        assertTrue(outputStream.toString().contains("This trip already exists."));
    }

    @Test
    public void handleTripCommand_listTrips_emptyList() {
        String[] input = {"trip", "list"};
        TripCommand.handleTripCommand(input);
        assertTrue(outputStream.toString().contains("No trips recorded yet."));
    }

    @Test
    public void handleTripCommand_deleteInvalidIndex_failure() {
        String[] input = {"trip", "delete", "99"};
        TripCommand.handleTripCommand(input);
        assertTrue(outputStream.toString().contains("Invalid index"));
    }

    @Test
    public void handleTripCommand_invalidFormat_failure() {
        String[] input = {"trip", "invalid"};
        TripCommand.handleTripCommand(input);
        assertTrue(outputStream.toString().contains("Invalid trip command." +
                " Please use:"));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }
}
