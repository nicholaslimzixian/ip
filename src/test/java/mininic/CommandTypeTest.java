package mininic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CommandTypeTest {

    @Test
    void of_todo_isTODO() {
        assertEquals(CommandType.TODO, CommandType.of("todo"));
    }

    @Test
    void of_mark_isMARK() {
        assertEquals(CommandType.MARK, CommandType.of("mark"));
    }

    @Test
    void of_unmark_isUNMARK() {
        assertEquals(CommandType.UNMARK, CommandType.of("unmark"));
    }

    @Test
    void of_delete_isDELETE() {
        assertEquals(CommandType.DELETE, CommandType.of("delete"));
    }

    @Test
    void of_list_isLIST() {
        assertEquals(CommandType.LIST, CommandType.of("list"));
    }

    @Test
    void of_bye_isBYE() {
        assertEquals(CommandType.BYE, CommandType.of("bye"));
    }

    @Test
    void of_whitespace_isUNKNOWN() {
        assertEquals(CommandType.UNKNOWN, CommandType.of("   "));
    }

    @Test
    void of_unknown_isUNKNOWN() {
        assertEquals(CommandType.UNKNOWN, CommandType.of("SKIYAAAAA"));
    }
}
