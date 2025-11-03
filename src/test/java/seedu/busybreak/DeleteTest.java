package seedu.busybreak;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.busybreak.activity.Activity;
import seedu.busybreak.command.Delete;
import seedu.busybreak.command.Edit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DeleteTest {

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
    public void deleteActivityDataFromList() {
        BusyBreak.list = new ArrayList<>();
        Activity one = new Activity("2025-10-10", "11:11", "film tiktok video", "69");
        Activity two = new Activity("2025-11-11", "11:11", "farm aura", "67");
        BusyBreak.list.add(one);
        BusyBreak.list.add(two);
        BusyBreak.list.remove(one);
        assertEquals(1, BusyBreak.list.size());
        assertTrue(BusyBreak.list.contains(two));
    }

    @Test
    public void deleteActivityInvalidIndexFormat() {
        Activity a = new Activity("2025-11-11", "09:00", "test test", "67");
        BusyBreak.list.add(a);

        String[] input = {"delete", "xxx"};
        Delete.deleteActivityDataFromList(input);

        assertTrue(output().contains("Invalid index format. Please provide a valid number."));
    }

    @Test
    void deleteActivityInvalidIndexPrintsErrorNoChanges() {
        Activity a = new Activity("2025-11-11", "09:00", "test test", "67");
        BusyBreak.list.add(a);

        String[] input = {"delete", "99"};
        Delete.deleteActivityDataFromList(input);

        assertSame(a, BusyBreak.list.get(0));
        String printed = output();
        assertTrue(printed.contains("Invalid index. Please provide a valid activity number."));
        assertFalse(printed.contains("Deleted"));
    }
}


















