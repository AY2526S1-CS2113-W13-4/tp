package seedu.busybreak;

import seedu.busybreak.activity.Activity;
import seedu.busybreak.command.Add;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AddTest {
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
    }


    @Test
    public void addActivityDataToList_validActivity() throws Exception {
        String[] input = {"add", "d/2025-10-31", "t/13:00", "desc/eat lunch", "c/15"};
        Add.addActivityDataToList(input);

        Field listField = BusyBreak.class.getDeclaredField("list");
        listField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Activity> list = (ArrayList<Activity>) listField.get(null);
        assertFalse(list.isEmpty());
        assertEquals("eat lunch", list.get(0).getDescription());
        assertEquals("15", list.get(0).getCost());
        assertEquals("2025-10-31", list.get(0).getDate());
        assertEquals("13:00", list.get(0).getTime());
    }

    @Test
    public void addActivityDataToList_multipleActivities() throws Exception {
        String[] input1 = {"add", "d/2025-10-31", "t/13:00", "desc/eat lunch", "c/15"};
        String[] input2 = {"add", "d/2025-10-31", "t/18:00", "desc/eat dinner", "c/15"};

        Add.addActivityDataToList(input1);
        Add.addActivityDataToList(input2);
        Field listField = BusyBreak.class.getDeclaredField("list");
        listField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Activity> list = (ArrayList<Activity>) listField.get(null);
        assertEquals(2, list.size());
    }

    @Test
    public void addActivityDataToList_negativeCost() throws Exception {
        String[] input = {"add", "d/2025-10-31", "t/13:00", "desc/eat lunch", "c/-15"};
        Add.addActivityDataToList(input);
        Field listField = BusyBreak.class.getDeclaredField("list");
        listField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Activity> list = (ArrayList<Activity>) listField.get(null);
        assertTrue(list.isEmpty());
        String output = outputStream.toString();
        assertTrue(output.contains("Invalid"));
    }

    @Test
    public void addActivityDataToList_invalidDescription() throws Exception {
        String[] input = {"add", "d/2025-10-31", "t/13:00", "desc/eat|lunch", "c/15"};
        Add.addActivityDataToList(input);
        Field listField = BusyBreak.class.getDeclaredField("list");
        listField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Activity> list = (ArrayList<Activity>) listField.get(null);
        assertTrue(list.isEmpty());
        String output = outputStream.toString();
        assertTrue(output.contains("Description cannot contain the '|' character"));
    }

    @Test
    public void addActivityDataToList_invalidDate() throws Exception {
        String[] input = {"add", "d/2021-13-32", "t/13:00", "desc/thing", "c/15"};
        Add.addActivityDataToList(input);
        Field listField = BusyBreak.class.getDeclaredField("list");
        listField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Activity> list = (ArrayList<Activity>) listField.get(null);
        assertTrue(list.isEmpty());
        String output = outputStream.toString();
        assertTrue(output.contains("Invalid date"));
    }

    @Test
    public void addActivityDataToList_emptyDescription() throws Exception {
        String[] input = {"add", "d/2025-10-31", "t/13:00", "desc/", "c/15"};
        Add.addActivityDataToList(input);
        Field listField = BusyBreak.class.getDeclaredField("list");
        listField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Activity> list = (ArrayList<Activity>) listField.get(null);
        assertTrue(list.isEmpty());
        String output = outputStream.toString();
        assertTrue(output.contains("is empty"));
    }

    @Test
    public void addActivityDataToList_missingDate() throws Exception {
        String[] input = {"add", "t/13:00", "desc/eat lunch", "c/15"};
        Add.addActivityDataToList(input);
        Field listField = BusyBreak.class.getDeclaredField("list");
        listField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Activity> list = (ArrayList<Activity>) listField.get(null);
        assertTrue(list.isEmpty());
        String output = outputStream.toString();
        assertTrue(output.contains("Missing date"));
    }

    @Test
    public void addActivityDataToList_missingTime() throws Exception {
        String[] input = {"add", "d/2025-01-01", "desc/eat lunch", "c/15"};
        Add.addActivityDataToList(input);
        Field listField = BusyBreak.class.getDeclaredField("list");
        listField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Activity> list = (ArrayList<Activity>) listField.get(null);
        assertTrue(list.isEmpty());
        String output = outputStream.toString();
        assertTrue(output.contains("Missing time"));
    }

    @Test
    public void addActivityDataToList_missingDescription() throws Exception {
        String[] input = {"add", "d/2025-01-01", "t/13:00", "c/15"};
        Add.addActivityDataToList(input);
        Field listField = BusyBreak.class.getDeclaredField("list");
        listField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Activity> list = (ArrayList<Activity>) listField.get(null);
        assertTrue(list.isEmpty());
        String output = outputStream.toString();
        assertTrue(output.contains("Missing description"));
    }

    @Test
    public void addActivityDataToList_missingCost() throws Exception {
        String[] input = {"add", "d/2025-01-01", "t/13:00", "desc/eat lunch"};
        Add.addActivityDataToList(input);
        Field listField = BusyBreak.class.getDeclaredField("list");
        listField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<Activity> list = (ArrayList<Activity>) listField.get(null);
        assertTrue(list.isEmpty());
        String output = outputStream.toString();
        assertTrue(output.contains("Missing cost"));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }
}
