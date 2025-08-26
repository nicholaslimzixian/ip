package mininic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the Parser class.
 */
public class ParserTest {

    @Test
    void parseDeadline() {
        Parser.ParsedCommand pc = Parser.parse("deadline return book /by 2019-12-02");
        assertEquals(CommandType.DEADLINE, pc.type);
        assertEquals("return book /by 2019-12-02", pc.arg);
    }

    @Test
    void parseEvent() {
        Parser.ParsedCommand pc = Parser.parse("event meeting /from 2019-12-02 1400 /to 2019-12-02 1600");
        assertEquals(CommandType.EVENT, pc.type);
        assertEquals("meeting /from 2019-12-02 1400 /to 2019-12-02 1600", pc.arg);
    }

    @Test
    void parseUnknown() {
        Parser.ParsedCommand pc = Parser.parse("SKIYAAAAAAAAAAA");
        assertEquals(CommandType.UNKNOWN, pc.type);
        assertEquals("", pc.arg);
    }
}
