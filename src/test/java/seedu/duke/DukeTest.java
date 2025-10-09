package seedu.duke;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DukeTest {
    @Test
    public void addActivityDataToList() {
        Duke.list = new ArrayList<>();
        Activity a = new Activity("2025-10-09", "09:00", "visit museum", "67");
        Duke.list.add(a);
        assertEquals(1, Duke.list.size());
        assertTrue(Duke.list.contains(a));
    }
}
