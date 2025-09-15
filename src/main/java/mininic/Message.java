package mininic;

/**
 * Message constants and functions for user feedback.
 */
public final class Message {

    public static final String LS = System.lineSeparator();
    public static final String GREETING = "Hello! I'm Mininic. Your wish is my command!";
    public static final String BYE = "Bye...:(";
    public static final String TODO_HELP = "Usage: todo <description>";
    public static final String DEADLINE_HELP = "Usage: deadline <description> /by yyyy-mm-dd";
    public static final String EVENT_HELP = "Usage: event <description> /from yyyy-mm-dd HHmm /to yyyy-mm-dd HHmm";
    public static final String DATE_HELP = "Please enter the date in the format of yyyy-mm-dd";
    public static final String DATE_TIME_HELP = "Please enter the date in the format of yyyy-mm-dd or yyyy-mm-dd HHmm";
    public static final String FIND_HELP = "Usage: find <keyword>";
    public static final String NO_MATCHING_TASKS = "No matching tasks found.";
    public static final String EMPTY_INPUT = "Input is empty...";
    public static final String SAVE_ERROR = "Please try again. An error occurred while saving: ";
    public static final String HELP = String.join(LS,
        "Enter a valid command! Try:",
        "1. todo <desc>",
        "2. deadline <desc> /by <time>",
        "3. event <desc> /from <start> /to <end>",
        "4. list",
        "5. mark <N>, unmark <N>",
        "6. delete <N>",
        "7. find <keyword>",
        "8. routine <desc> /every <day> /at <time>",
        "9. bye"
    );
    public static final String INVALID_TASK_NUMBER = "The task number is invalid!";
    public static final String ROUTINE_HELP = "Usage: routine <description> /every <day> /at <time>";
    public static final String INVALID_ROUTINE = "Enter a valid day (e.g., MONDAY, etc.) and time (e.g., 1408).";

    /**
     * Returns a message to indicate that a task has been added.
     * @param t The task that was added.
     * @param count The total number of tasks after addition.
     * @return The formatted message.
     */
    public static String showAdded(Task t, int count) {
        return String.join(LS,
            "Added a new task:",
            " " + t,
            "There are " + count + " tasks in total.");
    }

    /**
     * Returns a message to indicate that a task has been removed.
     * @param t The task that was removed.
     * @param count The total number of tasks after removal.
     * @return The formatted message.
     */
    public static String showDeleted(Task t, int count) {
        return String.join(LS,
            "This task has been removed:",
            " " + t,
            "There are " + count + " tasks in total.");
    }

    /**
     * Returns a message to indicate that a task has been marked.
     * @param t The task that was marked.
     * @return The formatted message.
     */
    public static String showMarked(Task t) {
        return String.join(LS,
            "One task down, many more to go...:" + " " + t.toString());
    }

    /**
     * Returns a message to indicate that a task has been unmarked.
     * @param t The task that was unmarked.
     * @return The formatted message.
     */
    public static String showUnmarked(Task t) {
        return String.join(LS,
            "Why did you even mark this task in the first place?:" + " " + t.toString());
    }
}
