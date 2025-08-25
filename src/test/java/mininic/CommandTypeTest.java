package mininic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CommandTypeTest {

    @Test
    void isTodo() {
        assertEquals(CommandType.TODO, CommandType.of("todo"));
    }

    @Test
    void isMark() {
        assertEquals(CommandType.MARK, CommandType.of("mark"));
    }

    @Test
    void isUnmark() {
        assertEquals(CommandType.UNMARK, CommandType.of("unmark"));
    }

    @Test
    void isDelete() {
        assertEquals(CommandType.DELETE, CommandType.of("delete"));
    }

    @Test
    void isList() {
        assertEquals(CommandType.LIST, CommandType.of("list"));
    }

    @Test
    void isBye() {
        assertEquals(CommandType.BYE, CommandType.of("bye"));
    }

    @Test
    void isBlankUnknown() {
        assertEquals(CommandType.UNKNOWN, CommandType.of("   "));
    }

    @Test
    void isUnknownUnknown() {
        assertEquals(CommandType.UNKNOWN, CommandType.of("SKIYAAAAA"));
    }
}
