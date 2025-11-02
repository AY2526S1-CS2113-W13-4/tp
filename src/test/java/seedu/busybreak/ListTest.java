package seedu.busybreak;

import seedu.busybreak.activity.Activity;
import seedu.busybreak.command.Add;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.busybreak.command.List;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListTest {
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
    public void listItems_emptyItinerary() {
        List.listItems();
        String output = outputStream.toString();
        assertTrue(output.contains("Itinerary is Empty"));
    }

    @Test
    public void listItems_showsSingleActivity() {
        String[] input = {"add", "d/2025-01-01", "t/13:00", "desc/eat breakfast", "c/15"};
        Add.addActivityDataToList(input);
        List.listItems();
        String output = outputStream.toString();
        assertTrue(output.contains("eat breakfast"));
    }

    @Test
    public void listItems_showsAll() {
        String[] input1 = {"add", "d/2025-01-01", "t/13:00", "desc/eat breakfast", "c/15"};
        Add.addActivityDataToList(input1);
        String[] input2 = {"add", "d/2025-01-01", "t/13:00", "desc/eat lunch", "c/15"};
        Add.addActivityDataToList(input2);
        String[] input3 = {"add", "d/2025-01-01", "t/13:00", "desc/eat dinner", "c/15"};
        Add.addActivityDataToList(input3);


        List.listItems();
        String output = outputStream.toString();
        assertTrue(output.contains("eat lunch"));
        assertTrue(output.contains("eat dinner"));
        assertTrue(output.contains("eat breakfast"));
    }

    @Test
    public void listItems_preserveOrder() {
        String[] input1 = {"add", "d/2025-01-01", "t/13:00", "desc/activityA", "c/15"};
        Add.addActivityDataToList(input1);
        String[] input2 = {"add", "d/2025-01-01", "t/13:00", "desc/activityB", "c/15"};
        Add.addActivityDataToList(input2);
        String[] input3 = {"add", "d/2025-01-01", "t/13:00", "desc/activityC", "c/15"};
        Add.addActivityDataToList(input3);
        List.listItems();
        String output = outputStream.toString();
        int indexOfA = output.indexOf("activityA");
        int indexOfB = output.indexOf("activityB");
        int indexOfC = output.indexOf("activityC");

        assertTrue(output.contains("activityA"));
        assertTrue(output.contains("activityB"));
        assertTrue(output.contains("activityC"));
        assertTrue(indexOfA < indexOfB && indexOfB < indexOfC);
    }

    @Test
    public void listItems_displaysIndexes() {
        String[] input1 = {"add", "d/2025-01-01", "t/13:00", "desc/activityA", "c/15"};
        Add.addActivityDataToList(input1);
        String[] input2 = {"add", "d/2025-01-01", "t/13:00", "desc/activityB", "c/15"};
        Add.addActivityDataToList(input2);
        String[] input3 = {"add", "d/2025-01-01", "t/13:00", "desc/activityC", "c/15"};
        Add.addActivityDataToList(input3);
        List.listItems();
        String output = outputStream.toString();

        assertTrue(output.contains("1."));
        assertTrue(output.contains("2."));
        assertTrue(output.contains("3."));

    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }
}
