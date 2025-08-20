public enum CommandType {
    BYE, LIST, TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, UNKNOWN;

    public static CommandType of(String input) {
        if (input == null) return UNKNOWN;
        String s = input.trim();
        if (s.isEmpty()) return UNKNOWN;
        String head = s.split("\\s+", 2)[0].toLowerCase();
        switch (head) {
            case "bye": return BYE;
            case "list": return LIST;
            case "mark": return MARK;
            case "unmark": return UNMARK;
            case "todo": return TODO;
            case "deadline": return DEADLINE;
            case "event": return EVENT;
            case "delete": return DELETE;
            default: return UNKNOWN;
        }
    }
}
