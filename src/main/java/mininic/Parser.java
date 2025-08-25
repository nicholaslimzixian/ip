package mininic;

public class Parser {
    public static class ParsedCommand {
        public final CommandType type;
        public final String arg;
        /**
         * Creates a new ParsedCommand instance.
         * @param type
         * @param arg
         */
        public ParsedCommand(CommandType type, String arg) {
            this.type = type;
            this.arg = arg;
        }
    }

    /**
     * Parses the user input into a command.
     * @param input
     * @return ParsedCommand
     */
    public static ParsedCommand parse(String input) {
        String command;
        if (input == null) {
            command = "";
        } else {
            command = input.trim();
        }

        if (command.isEmpty()) {
            return new ParsedCommand(CommandType.UNKNOWN, "");
        } else {
            String[] parts = command.split("\\s+", 2);
            String arg = parts.length > 1 ? parts[1] : "";
            return new ParsedCommand(CommandType.of(parts[0]), arg);
        }
    }
}
