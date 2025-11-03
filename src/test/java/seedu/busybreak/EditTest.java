package seedu.busybreak;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.busybreak.activity.Activity;
import seedu.busybreak.command.Edit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EditTest {

    private static final PrintStream REAL_OUT = System.out;
    private static ByteArrayOutputStream out;

    @BeforeEach
    public void setUp() {
        BusyBreak.list =  new ArrayList<>();
        out  = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(REAL_OUT);
    }

    private String output() {
        return out.toString();
    }

    @Test
    void editActivityValidInputUpdatesAndPrints() {
        Activity a = new Activity("2025-10-10", "11:11", "film tiktok video", "69");
        BusyBreak.list.add(a);

        String[] input = {"edit", "1", "d/2001-09-11", "t/08:46", "desc/new description", "c/21"};
        Edit.editActivityDataInList(input);

        Activity edited = BusyBreak.list.get(0);
        assertEquals("2001-09-11", edited.getDate());
        assertEquals("08:46", edited.getTime());
        assertEquals("new description", edited.getDescription());
        assertEquals("21", edited.getCost());

        String printed = output();
        assertTrue(printed.contains("Activity 1 has been edited with the following details:"));
        assertTrue(printed.contains("Date: 2001-09-11"));
        assertTrue(printed.contains("Time: 08:46"));
        assertTrue(printed.contains("Description: new description"));
        assertTrue(printed.contains("Cost: $21"));
    }

    @Test
    void editActivityMultipleFieldsAnyOrder() {
        Activity a = new Activity("2025-11-11", "09:00", "test test", "67");
        BusyBreak.list.add(a);

        String[] input = {"edit", "1", "c/21", "d/2001-09-11", "t/08:46"};
        Edit.editActivityDataInList(input);

        Activity edited = BusyBreak.list.get(0);
        assertEquals("2001-09-11", edited.getDate());
        assertEquals("08:46", edited.getTime());
        assertEquals("test test", edited.getDescription());
        assertEquals("21", edited.getCost());
    }

    @Test
    void editActivityInvalidIndexPrintsErrorNoChanges() {
        Activity a = new Activity("2025-11-11", "09:00", "test test", "67");
        BusyBreak.list.add(a);

        String[] input = {"edit", "99", "c/21", "d/2001-09-11", "t/08:46"};
        Edit.editActivityDataInList(input);

        assertSame(a, BusyBreak.list.get(0));
        String printed = output();
        assertTrue(printed.contains("Invalid index. Please provide a valid activity number."));
        assertFalse(printed.contains("has been edited"));
    }

    @Test
    void editActivityInvalidIndexFormatPrintsErrorNoChanges() {
        Activity a = new Activity("2025-11-11", "09:00", "test test", "67");
        BusyBreak.list.add(a);

        String[] input = {"edit", "xxx", "c/21", "d/2001-09-11", "t/08:46"};
        Edit.editActivityDataInList(input);

        assertTrue(output().contains("Invalid index format. Please provide a valid number."));
    }

    @Test
    void editActivityUnknownFieldPrintsErrorNoChanges() {
        Activity a = new Activity("2025-11-11", "09:00", "test test", "67");
        BusyBreak.list.add(a);

        String[] input = {"edit", "1", "xxx/21"};
        Edit.editActivityDataInList(input);

        assertTrue(output().contains("Input must contain a valid index and valid fields to be edited."));
    }

    @Test
    void editActivity_NoFields_PrintsError() {
        BusyBreak.list.add(new Activity("2025-01-01", "12:00", "Test", "10"));

        String[] cmd = {"edit", "1"};
        Edit.editActivityDataInList(cmd);

        assertTrue(output().contains("Input must contain a valid index and valid fields to be edited."));
    }
}


















