package seedu.busybreak;

import seedu.busybreak.activity.Activity;
import seedu.busybreak.command.Add;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.busybreak.command.Find;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindTest {
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
    public void searchByKeyword_findSuccess(){
        String[] input = {"add", "d/2025-01-01", "t/13:00", "desc/eat lunch", "c/15"};
        Add.addActivityDataToList(input);

        String[] findInput = {"find", "lunch"};
        Find.searchByKeyword(findInput);
        String output = outputStream.toString();
        assertTrue(output.contains("eat lunch"));
    }

    @Test
    public void searchByKeyword_noItemsFound(){
        String[] input = {"add", "d/2025-01-01", "t/13:00", "desc/eat lunch", "c/15"};
        Add.addActivityDataToList(input);
        String[] findInput = {"find", "museum"};
        Find.searchByKeyword(findInput);
        String output = outputStream.toString();
        assertTrue(output.contains("No items found!"));
    }

    @Test
    public void searchByKeyword_emptyItinerary(){
        String[] findInput = {"find", "lunch"};
        Find.searchByKeyword(findInput);
        String output = outputStream.toString();
        assertTrue(output.contains("is empty"));
    }

    @Test
    public void searchByKeyword_findsAllMatches(){
        String[] input1 = {"add", "d/2025-01-01", "t/13:00", "desc/eat lunch", "c/15"};
        Add.addActivityDataToList(input1);
        String[] input2 = {"add", "d/2025-01-01", "t/13:00", "desc/eat breakfast", "c/15"};
        Add.addActivityDataToList(input2);
        String[] input3 = {"add", "d/2025-01-01", "t/13:00", "desc/eat dinner", "c/15"};
        Add.addActivityDataToList(input3);

        String[] findInput = {"find", "eat"};
        Find.searchByKeyword(findInput);
        String output = outputStream.toString();
        assertTrue(output.contains("eat lunch"));
        assertTrue(output.contains("eat dinner"));
        assertTrue(output.contains("eat breakfast"));
    }

    @Test
    public void searchByKeyword_notCaseSensitive(){
        String[] input = {"add", "d/2025-01-01", "t/13:00", "desc/eat lunch", "c/15"};
        Add.addActivityDataToList(input);

        String[] findInput = {"find", "LUNCH"};
        Find.searchByKeyword(findInput);
        String output = outputStream.toString();
        assertTrue(output.contains("lunch"));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }
}
