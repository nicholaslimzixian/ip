package mininic;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        String message = "The task number is invalid!";
        try {
            int n = Integer.parseInt(input.trim());
            if (n <= 0) {
                throw new InvalidCommandException(message);
            }
            int idx = n - 1;
            if (idx >= size) {
                throw new InvalidCommandException(message);
            }
            return idx;
        } catch (NumberFormatException e) {
            throw new InvalidCommandException(message);
        }
    }

    private static String requireNonEmpty(String s, String message) {
        if (s == null || s.trim().isEmpty()) {
            throw new EmptyDescriptionException(message);
        }
        return s.trim();
    }

    public String getResponse(String input) {
        return "Mininic heard: " + input;
    }

    /**
    public String getResponse(String input) {
        Parser.ParsedCommand command = Parser.parse(input);
        String arg = command.arg;

        try {
            switch (command.type) {
            case BYE:
                return "Bye... :'(";

            case LIST: {
                return String.join("\n", taskList.asLines());
            }

            case MARK: {
                int idx = parseIndex(input.substring(4), taskList.size());
                Task t = taskList.mark(idx);
                return "One task down, many more to go...:" + " " + t.toString();
            }

            case UNMARK: {
                int idx = parseIndex(input.substring(6), taskList.size());
                Task t = taskList.unmark(idx);
                return "Why did you even mark this task in the first place?:"
                     + " " + t.toString();
            }

            case TODO: {
                String name = requireNonEmpty(arg, "Usage: todo <description>");
                Task t = taskList.add(new Todo(name));
                return "Added a new task:" + " " + t.toString() + "\n"
                     + "There are " + taskList.size() + " tasks in total.";
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
                return "Added a new task:" + " " + t.toString() + "\n"
                     + "There are " + taskList.size() + " tasks in total.";
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
                return "Added a new task:" + " " + t.toString() + "\n"
                     + "There are " + taskList.size() + " tasks in total.";
            }

            case DELETE: {
                int idx = parseIndex(input.substring(6), taskList.size());
                Task t = taskList.delete(idx);
                return "This task has been removed:" + " " + t.toString() + "\n"
                     + "There are " + taskList.size() + " tasks in total.";
            }

            case FIND: {
                String usage = "Usage: find <keyword>";
                if (arg == null || arg.trim().isEmpty()) {
                    throw new EmptyDescriptionException(usage);
                }

                var hits = taskList.find(arg.trim());
                if (hits.isEmpty()) {
                    return "No matching tasks found.";
                } else {
                    java.util.List<String> lines = new java.util.ArrayList<>();
                    for (int i = 0; i < hits.size(); i++) {
                        lines.add((i + 1) + ". " + hits.get(i).toString());
                    }
                    return String.join("/n", lines);
                }
            }

            case UNKNOWN: {
                if (!input.trim().isEmpty()) {
                    return """
                           Enter a valid command!. Try:
                           1. todo <desc>
                           2. deadline <desc> /by yyyy-mm-dd
                           3. event <desc> /from yyyy-mm-dd HHmm /to yyyy-mm-dd HHmm
                           4. list
                           5. mark <N>, unmark <N>
                           6. delete <N>
                           7. find <keyword>
                           8. bye""";
                } else {
                    return "Input is empty...";
                }
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
        return "";
    }
    */

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
