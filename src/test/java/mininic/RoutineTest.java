package mininic;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

class RoutineTest {

    @Test
    void rejectInvalidDay() {
        String bad = "R | 0 | skrriya | Mon | 09:00";
        assertThrows(IllegalArgumentException.class, () -> Routine.fromStorageString(bad));
    }

    @Test
    void routineFromTo() {
        Routine r = new Routine(
                "routine task",
                DayOfWeek.MONDAY,
                LocalTime.parse("16:00"));
        String s = r.toString();
        assertTrue(s.startsWith("[R][ ] routine task"), "prefix wrong: " + s);
        assertTrue(s.contains("(every:"), "missing 'every:' part: " + s);
        assertTrue(s.contains("16:00"), "should include time: " + s);
    }
}
