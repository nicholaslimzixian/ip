package mininic;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class RoutineTest {

    @Test
    void rejectInvalidDay() {
        String bad = "R | 0 | skrriya | Mon | 09:00";
        assertThrows(IllegalArgumentException.class, () -> Routine.fromStorageString(bad));
    }
}
