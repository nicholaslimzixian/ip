package mininic;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Main entry point for the Mininic application.
 */
public class Mininic {

    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;

    /**
     * Creates a new Mininic instance.
     * @param filePath
     */
    public Mininic(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.taskList = new TaskList(storage.load(), storage);
    }

    private static int parseIndex(String input, int size) {
        try {
            int n = Integer.parseInt(input.trim());
            if (n <= 0) {
                throw new InvalidCommandException(Message.INVALID_TASK_NUMBER);
            }
            int idx = n - 1;
            if (idx >= size) {
                throw new InvalidCommandException(Message.INVALID_TASK_NUMBER);
            }
            return idx;
        } catch (NumberFormatException e) {
            throw new InvalidCommandException(Message.INVALID_TASK_NUMBER);
        }
    }

    private static String requireNonEmpty(String s, String message) {
        if (s == null || s.trim().isEmpty()) {
            throw new EmptyDescriptionException(message);
        }
        return s.trim();
    }

    /**
     * Generates a response based on user input for GUI.
     * @param input
     * @return String response
     */
    public String getResponse(String input) {
        Parser.ParsedCommand command = Parser.parse(input);
        String arg = command.arg;

        try {
            switch (command.type) {
            case BYE:
                System.exit(0);
                break;
            case LIST: {
                return String.join("\n", taskList.asLines());
            }

            case MARK: {
                int idx = parseIndex(input.substring(4), taskList.size());
                Task t = taskList.mark(idx);
                return Message.showMarked(t);
            }

            case UNMARK: {
                int idx = parseIndex(input.substring(6), taskList.size());
                Task t = taskList.unmark(idx);
                return Message.showUnmarked(t);
            }

            case TODO: {
                String name = requireNonEmpty(arg, Message.TODO_HELP);
                Task t = taskList.add(new Todo(name));
                return Message.showAdded(t, taskList.size());
            }

            case DEADLINE: {
                String taskBy = requireNonEmpty(arg, Message.DEADLINE_HELP);
                int byIdx = taskBy.indexOf("/by");
                if (byIdx < 0) {
                    throw new InvalidCommandException(Message.DEADLINE_HELP);
                }
                String name = requireNonEmpty(taskBy.substring(0, byIdx), Message.DEADLINE_HELP);
                String by = requireNonEmpty(taskBy.substring(byIdx + 3), Message.DEADLINE_HELP);

                LocalDate byDate;
                try {
                    byDate = LocalDate.parse(by);
                } catch (DateTimeParseException e) {
                    throw new InvalidCommandException(Message.DATE_HELP);
                }
                Task t = taskList.add(new Deadline(name, byDate));
                return Message.showAdded(t, taskList.size());
            }

            case EVENT: {
                String taskFromTo = requireNonEmpty(arg, Message.EVENT_HELP);
                int fromIdx = taskFromTo.indexOf("/from");
                int toIdx = taskFromTo.indexOf("/to");
                if (fromIdx < 0 || toIdx < 0 || toIdx < fromIdx) {
                    throw new InvalidCommandException(Message.EVENT_HELP);
                }
                String name = requireNonEmpty(taskFromTo.substring(0, fromIdx), Message.EVENT_HELP);
                String from = requireNonEmpty(taskFromTo.substring(fromIdx + 5, toIdx), Message.EVENT_HELP);
                String to = requireNonEmpty(taskFromTo.substring(toIdx + 3), Message.EVENT_HELP);

                Task t;
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

                try {
                    LocalDateTime fromDt = LocalDateTime.parse(from, dtf);
                    LocalDateTime toDt = LocalDateTime.parse(to, dtf);
                    t = taskList.add(new Event(name, fromDt, toDt));
                } catch (DateTimeParseException dtFailure) {
                    try {
                        LocalDate fromD = LocalDate.parse(from);
                        LocalDate toD = LocalDate.parse(to);
                        t = taskList.add(new Event(name, fromD, toD));
                    } catch (DateTimeParseException e) {
                        throw new InvalidCommandException(Message.DATE_TIME_HELP);
                    }
                }
                return Message.showAdded(t, taskList.size());
            }

            case DELETE: {
                int idx = parseIndex(input.substring(6), taskList.size());
                Task t = taskList.delete(idx);
                return Message.showDeleted(t, taskList.size());
            }

            case FIND: {
                if (arg == null || arg.trim().isEmpty()) {
                    throw new EmptyDescriptionException(Message.FIND_HELP);
                }

                var hits = taskList.find(arg.trim());
                if (hits.isEmpty()) {
                    return Message.NO_MATCHING_TASKS;
                } else {
                    java.util.List<String> lines = new java.util.ArrayList<>();
                    for (int i = 0; i < hits.size(); i++) {
                        lines.add((i + 1) + ". " + hits.get(i).toString());
                    }
                    return String.join(System.lineSeparator(), lines);
                }
            }

            case ROUTINE: {
                String taskDayTime = requireNonEmpty(arg, Message.ROUTINE_HELP);
                int everyIdx = taskDayTime.indexOf("/every");
                int atIdx = taskDayTime.indexOf("/at");
                if (everyIdx < 0 || atIdx < 0 || atIdx < everyIdx) {
                    throw new InvalidCommandException(Message.ROUTINE_HELP);
                }
                String name = requireNonEmpty(taskDayTime.substring(0, everyIdx), Message.ROUTINE_HELP);
                String day = requireNonEmpty(taskDayTime.substring(everyIdx + 6, atIdx), Message.ROUTINE_HELP);
                String time = requireNonEmpty(taskDayTime.substring(atIdx + 3), Message.ROUTINE_HELP);

                Task t;
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmm");

                try {
                    DayOfWeek dayOfWeek = DayOfWeek.valueOf(day.toUpperCase());
                    LocalTime localTime = LocalTime.parse(time, dtf);
                    t = taskList.add(new Routine(name, dayOfWeek, localTime));
                } catch (DateTimeParseException | IllegalArgumentException dtFailure) {
                    throw new InvalidCommandException(Message.INVALID_ROUTINE);
                }
                return Message.showAdded(t, taskList.size());
            }

            case UNKNOWN: {
                if (!input.trim().isEmpty()) {
                    return Message.HELP;
                } else {
                    return Message.EMPTY_INPUT;
                }
            }
            default: {
                //should not reach default
            }
            }
        } catch (EmptyDescriptionException | InvalidCommandException | UnknownCommandException e) {
            return e.getMessage();
        } catch (java.io.IOException e) {
            return Message.SAVE_ERROR + e.getMessage();
        }
        return "";
    }

    /**
     * Starts the Mininic CLI application.
     */
    public void run() {
        ui.welcomeMessage();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            Parser.ParsedCommand command = Parser.parse(input);
            String arg = command.arg;

            try {
                switch (command.type) {
                case BYE:
                    ui.byeMessage();
                    sc.close();
                    return;

                case LIST: {
                    ui.showTaskList(taskList.asLines());
                    break;
                }

                case MARK: {
                    int idx = parseIndex(input.substring(4), taskList.size());
                    Task t = taskList.mark(idx);
                    ui.showMarked(t);
                    break;
                }

                case UNMARK: {
                    int idx = parseIndex(input.substring(6), taskList.size());
                    Task t = taskList.unmark(idx);
                    ui.showUnmarked(t);
                    break;
                }

                case TODO: {
                    String name = requireNonEmpty(arg, "Usage: todo <description>");
                    Task t = taskList.add(new Todo(name));
                    ui.showAdded(t, taskList.size());
                    break;
                }

                case DEADLINE: {
                    String usage = "Usage: deadline <description> /by yyyy-mm-dd";
                    String taskBy = requireNonEmpty(arg, usage);
                    int byIdx = taskBy.indexOf("/by");
                    if (byIdx < 0) {
                        throw new InvalidCommandException(usage);
                    }
                    String name = requireNonEmpty(taskBy.substring(0, byIdx), usage);
                    String by = requireNonEmpty(taskBy.substring(byIdx + 3), usage);

                    LocalDate byDate;
                    try {
                        byDate = LocalDate.parse(by);
                    } catch (DateTimeParseException e) {
                        throw new InvalidCommandException("Please enter the date in the format of yyyy-mm-dd");
                    }
                    Task t = taskList.add(new Deadline(name, byDate));
                    ui.showAdded(t, taskList.size());
                    break;
                }

                case EVENT: {
                    String usage = "Usage: event <description> /from yyyy-mm-dd HHmm /to yyyy-mm-dd HHmm";
                    String taskFromTo = requireNonEmpty(arg, usage);
                    int fromIdx = taskFromTo.indexOf("/from");
                    int toIdx = taskFromTo.indexOf("/to");
                    if (fromIdx < 0 || toIdx < 0 || toIdx < fromIdx) {
                        throw new InvalidCommandException(usage);
                    }
                    String name = requireNonEmpty(taskFromTo.substring(0, fromIdx), usage);
                    String from = requireNonEmpty(taskFromTo.substring(fromIdx + 5, toIdx), usage);
                    String to = requireNonEmpty(taskFromTo.substring(toIdx + 3), usage);

                    Task t;
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

                    try {
                        LocalDateTime fromDt = LocalDateTime.parse(from, dtf);
                        LocalDateTime toDt = LocalDateTime.parse(to, dtf);
                        t = taskList.add(new Event(name, fromDt, toDt));
                    } catch (DateTimeParseException dtFailure) {
                        try {
                            LocalDate fromD = LocalDate.parse(from);
                            LocalDate toD = LocalDate.parse(to);
                            t = taskList.add(new Event(name, fromD, toD));
                        } catch (DateTimeParseException e) {
                            throw new InvalidCommandException(
                                "Please enter the date in the format of yyyy-mm-dd or yyyy-mm-dd HHmm");
                        }
                    }
                    ui.showAdded(t, taskList.size());
                    break;
                }

                case DELETE: {
                    int idx = parseIndex(input.substring(6), taskList.size());
                    Task t = taskList.delete(idx);
                    ui.showDeleted(t, taskList.size());
                    break;
                }

                case FIND: {
                    String usage = "Usage: find <keyword>";
                    if (arg == null || arg.trim().isEmpty()) {
                        throw new EmptyDescriptionException(usage);
                    }

                    var hits = taskList.find(arg.trim());
                    if (hits.isEmpty()) {
                        ui.showError("No matching tasks found.");
                    } else {
                        java.util.List<String> lines = new java.util.ArrayList<>();
                        for (int i = 0; i < hits.size(); i++) {
                            lines.add((i + 1) + ". " + hits.get(i).toString());
                        }
                        ui.showTaskList(lines);
                    }
                    break;
                }

                case UNKNOWN: {
                    if (!input.trim().isEmpty()) {
                        ui.showUnknownCommand();
                    } else {
                        ui.showError("Input is empty...");
                    }
                    break;
                }
                default: {
                    //should not reach default
                }
                }
            } catch (EmptyDescriptionException | InvalidCommandException | UnknownCommandException e) {
                ui.showError(e.getMessage());
            } catch (java.io.IOException e) {
                ui.showError("Please try again. An error occurred while saving: " + e.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        new Mininic("data/tasks.txt").run();
    }
}
