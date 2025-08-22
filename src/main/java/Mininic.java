import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Mininic {
    private static final String LINE = "____________________________________________________________";

    private static void box(String... lines) {
        System.out.println(LINE);
        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println(LINE + "\n");
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

    public static void main(String[] args) {
        box("Hello! I'm Mininic", "Your wish is my command!");

        Storage storage = new Storage("data/tasks.txt");

        List<Task> tasks = storage.load();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine().trim();
            String[] parts = input.isEmpty() 
                ? new String[]{""} 
                : input.split("\\s+", 2);
            String arg = parts.length > 1 
                ? parts[1] 
                : "";

            try {
                switch (CommandType.of(input)) {
                    case BYE:
                        box("Bye... :'(");
                        sc.close();
                        return;

                    case LIST: {
                        List<String> lines = new ArrayList<>();
                        for (int i = 0; i < tasks.size(); i++) {
                            lines.add((i + 1) + ". " + tasks.get(i).toString());
                        }
                        box(lines.toArray(new String[0]));
                        break;
                    }

                    case MARK: {
                        int idx = parseIndex(input.substring(4), tasks.size());
                        Task t = tasks.get(idx);
                        t.mark();
                        storage.save(tasks);
                        box("One task down, many more to go...:", " " + t.toString());
                        break;
                    }

                    case UNMARK: {
                        int idx = parseIndex(input.substring(6), tasks.size());
                        Task t = tasks.get(idx);
                        t.unmark();
                        storage.save(tasks);
                        box("Why did you even mark this task in the first place?:",
                            " " + t.toString());
                        break;
                    }

                    case TODO: {
                        String name = requireNonEmpty(arg, "Usage: todo <description>");
                        Task t = new Todo(name);
                        tasks.add(t);
                        storage.save(tasks);
                        box("Added a new task:",
                            " " + t.toString(),
                            "There are " + tasks.size() + " tasks in total.");
                        break;
                    }

                    case DEADLINE: {
                        String taskBy = requireNonEmpty(arg, "Usage: deadline <description> /by yyyy-mm-dd");
                        int byIdx = taskBy.indexOf("/by");
                        if (byIdx < 0) {
                            throw new InvalidCommandException("Usage: deadline <description> /by yyyy-mm-dd");
                        }
                        String name = requireNonEmpty(taskBy.substring(0, byIdx), "Usage: deadline <description> /by yyyy-mm-dd");
                        String by = requireNonEmpty(taskBy.substring(byIdx + 3), "Usage: deadline <description> /by yyyy-mm-dd");

                        LocalDate byDate;
                        try {
                            byDate = LocalDate.parse(by);
                        } catch (DateTimeParseException e) {
                            throw new InvalidCommandException("Please enter the date in the format of yyyy-mm-dd");
                        }
                        Task t = new Deadline(name, byDate);
                        tasks.add(t);
                        storage.save(tasks);
                        box("Added a new task:",
                            " " + t.toString(),
                            "There are " + tasks.size() + " tasks in total.");
                        break;
                    }

                    case EVENT: {
                        String taskFromTo = requireNonEmpty(arg, "Usage: event <description> /from yyyy-mm-dd HHmm /to yyyy-mm-dd HHmm");
                        int fromIdx = taskFromTo.indexOf("/from");
                        int toIdx   = taskFromTo.indexOf("/to");
                        if (fromIdx < 0 || toIdx < 0 || toIdx < fromIdx) {
                            throw new InvalidCommandException("Usage: event <description> /from yyyy-mm-dd HHmm /to yyyy-mm-dd HHmm");
                        }
                        String name = requireNonEmpty(taskFromTo.substring(0, fromIdx), "Usage: event <description> /from yyyy-mm-dd HHmm /to yyyy-mm-dd HHmm");
                        String from = requireNonEmpty(taskFromTo.substring(fromIdx + 5, toIdx), "Usage: event <description> /from yyyy-mm-dd HHmm /to yyyy-mm-dd HHmm");
                        String to = requireNonEmpty(taskFromTo.substring(toIdx + 3), "Usage: event <description> /from yyyy-mm-dd HHmm /to yyyy-mm-dd HHmm");

                        Task t;
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

                        try {
                            LocalDateTime fromDt = LocalDateTime.parse(from, dtf);
                            LocalDateTime toDt = LocalDateTime.parse(to, dtf);
                            t = new Event(name, fromDt, toDt);
                        } catch (DateTimeParseException dtFailure) {
                            try {
                                LocalDate fromD = LocalDate.parse(from);
                                LocalDate toD = LocalDate.parse(to);
                                t = new Event(name, fromD, toD);
                            } catch (Exception e) {
                                throw new InvalidCommandException("Please enter the date in the format of yyyy-mm-dd or yyyy-m-dd HHmm");
                            }
                        }


                        tasks.add(t);
                        storage.save(tasks);
                        box("Added a new task:",
                            " " + t.toString(),
                            "There are " + tasks.size() + " tasks in total.");
                        break;
                    }

                    case DELETE: {
                        int idx = parseIndex(input.substring(6), tasks.size());
                        Task removed = tasks.remove(idx);
                        storage.save(tasks);
                        box("This task has been removed:",
                            " " + removed.toString(),
                            "There are " + tasks.size() + " tasks in total.");
                        break;
                    }

                    case UNKNOWN: {
                        if (!input.isEmpty()) {
                            throw new UnknownCommandException(
                                    """
                                    Enter a valid command!. Try:
                                    1. todo <desc>
                                    2. deadline <desc> /by <time>
                                    3. event <desc> /from <start> /to <end>
                                    4. list
                                    5. mark <N>, unmark <N>
                                    6. delete <N>
                                    7. bye""");

                        } else {
                            throw new InvalidCommandException("Input is empty........");
                        }
                    }
                } 
            } catch (EmptyDescriptionException | InvalidCommandException | UnknownCommandException e) {
                box(e.getMessage());
            } catch (java.io.IOException e) {
                box("Please try again. An error occurred while saving: " + e.getMessage());
            }
        }
    }
}
