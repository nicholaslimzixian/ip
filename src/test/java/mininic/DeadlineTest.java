package mininic;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    void deadlineDate() {
        Deadline d = new Deadline("return book", LocalDate.parse("2019-12-02"));
        String s = d.toString();
        assertTrue(s.startsWith("[D][ ] return book"), "prefix wrong: " + s);
        assertTrue(s.contains("(by:"), "missing '(by:' part: " + s);
        assertTrue(s.contains("2019"), "should include year: " + s);
    }
}
