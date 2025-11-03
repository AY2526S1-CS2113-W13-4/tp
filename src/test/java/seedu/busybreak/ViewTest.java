package seedu.busybreak;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.busybreak.activity.Activity;
import seedu.busybreak.command.Add;
import seedu.busybreak.command.View;

public class ViewTest {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() throws Exception {
        originalOut = System.out;
        System.setOut(new PrintStream(output));

        Field listField = BusyBreak.class.getDeclaredField("list");
        listField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Activity> list = (ArrayList<Activity>) listField.get(null);
        list.clear();
        output.reset();
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void view_invalidFormat_noDate() {
        String[] input = {"view"};
        View.viewInput(input);
        String out = output.toString();
        assertTrue(out.contains("Invalid command format"));
        assertTrue(out.contains("view YYYY-MM-DD"));
    }

    @Test
    public void view_emptyItinerary_showsEmptyMessage() throws Exception {
        String[] input = {"view", "2025-10-31"};
        View.viewInput(input);
        String out = output.toString();
        assertTrue(out.contains("Itinerary is Empty!"));
    }

    @Test
    public void view_validDate_noMatches() throws Exception {
        String[] add = {"add", "d/2025-10-31", "t/13:00", "desc/eat lunch", "c/15"};
        Add.addActivityDataToList(add);

        output.reset();
        String[] view = {"view", "2025-10-30"};
        View.viewInput(view);

        String out = output.toString();
        assertTrue(out.contains("No activities found for 2025-10-30"));
    }

    @Test
    public void view_validDate_withMatches() throws Exception {
        String[] a1 = {"add", "d/2025-10-31", "t/13:00", "desc/eat lunch", "c/15"};
        String[] a2 = {"add", "d/2025-10-31", "t/18:00", "desc/eat dinner", "c/20"};
        Add.addActivityDataToList(a1);
        Add.addActivityDataToList(a2);

        output.reset();
        String[] view = {"view", "2025-10-31"};
        View.viewInput(view);

        String out = output.toString();
        assertTrue(out.contains("Itinerary for 2025-10-31:"));
        assertTrue(out.contains("1. "));
        assertTrue(out.contains("Date: 2025-10-31"));
        assertTrue(out.contains("Time: 13:00"));
        assertTrue(out.contains("Description: eat lunch"));
        assertTrue(out.contains("Cost: $15"));

        assertTrue(out.contains("2. "));
        assertTrue(out.contains("Time: 18:00"));
        assertTrue(out.contains("Description: eat dinner"));
        assertTrue(out.contains("Cost: $20"));
    }

    @Test
    public void view_filtersOutOtherDates() throws Exception {
        String[] a1 = {"add", "d/2025-10-31", "t/09:00", "desc/breakfast", "c/7"};
        String[] a2 = {"add", "d/2025-11-01", "t/10:00", "desc/brunch", "c/12"};
        Add.addActivityDataToList(a1);
        Add.addActivityDataToList(a2);

        output.reset();
        String[] view = {"view", "2025-10-31"};
        View.viewInput(view);

        String out = output.toString();
        assertTrue(out.contains("Itinerary for 2025-10-31:"));
        assertTrue(out.contains("breakfast"));
        assertFalse(out.contains("brunch"));
        assertFalse(out.contains("2025-11-01"));
    }
}
