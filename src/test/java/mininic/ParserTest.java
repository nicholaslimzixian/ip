package mininic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    void of_parse_deadline_hasTypeAndArg() {
        Parser.ParsedCommand pc = Parser.parse("deadline return book /by 2019-12-02");
        assertEquals(CommandType.DEADLINE, pc.type);
        assertEquals("return book /by 2019-12-02", pc.arg);
    }

    @Test
    void of_parse_event_withTimes_hasTypeAndArg() {
        Parser.ParsedCommand pc = Parser.parse("event meeting /from 2019-12-02 1400 /to 2019-12-02 1600");
        assertEquals(CommandType.EVENT, pc.type);
        assertEquals("meeting /from 2019-12-02 1400 /to 2019-12-02 1600", pc.arg);
    }

    @Test
    void of_parse_unknown_isUNKNOWN() {
        Parser.ParsedCommand pc = Parser.parse("SKIYAAAAAAAAAAA");
        assertEquals(CommandType.UNKNOWN, pc.type);
        assertEquals("", pc.arg);
    }
}
