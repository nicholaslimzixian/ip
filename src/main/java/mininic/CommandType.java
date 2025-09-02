package mininic;

/**
 * Enumeration of command types.
 */
public enum CommandType {
    BYE, LIST, TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, FIND, UNKNOWN;

    /**
     * Returns the CommandType corresponding to the given input string.
     */
    public static CommandType of(String input) {
        assert (input != null) : "Input should not be null";
        switch (input) {
        case "bye": return BYE;
        case "list": return LIST;
        case "mark": return MARK;
        case "unmark": return UNMARK;
        case "todo": return TODO;
        case "deadline": return DEADLINE;
        case "event": return EVENT;
        case "delete": return DELETE;
        case "find": return FIND;
        default: return UNKNOWN;
        }
    }
}
