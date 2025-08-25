package mininic;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class EventTest {

    @Test
    void of_event_toString_containsFromAndTo() {
        Event e = new Event(
                "project meeting",
                LocalDateTime.parse("2019-12-02T14:00"),
                LocalDateTime.parse("2019-12-02T16:00"));
        String s = e.toString();
        assertTrue(s.startsWith("[E][ ] project meeting"), "prefix wrong: " + s);
        assertTrue(s.contains("(from:"), "missing 'from:' part: " + s);
        assertTrue(s.contains("to:"), "missing 'to:' part: " + s);
        assertTrue(s.contains("2019"), "should include year: " + s);
    }
}
