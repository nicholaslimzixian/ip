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

    public static void main(String[] args) {
        box("Hello! I'm Mininic", "Your wish is my command!");

        List<Task> tasks = new ArrayList<>(100);

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine().trim();
            if (input.equals("bye")) {
                box("Bye... :'(");
                sc.close();
                break;

            } else if (input.equals("list")) {
                List<String> lines = new ArrayList<>();
                for (int i = 0; i < tasks.size(); i++) {
                    lines.add((i + 1) + ". " + tasks.get(i).toString());
                }
                box(lines.toArray(new String[0]));

            } else if (input.startsWith("mark ")) {
                try {
                    int idx = Integer.parseInt(input.substring(5).trim()) - 1;
                    Task t = tasks.get(idx);
                    t.mark();
                    box("One task down, many more to go...:",
                        " " + t.toString());
                } catch (Exception e) {
                    box("The task number is invalid!");
                }

            } else if (input.startsWith("unmark ")) {
                try {
                    int idx = Integer.parseInt(input.substring(7).trim()) - 1;
                    Task t = tasks.get(idx);
                    t.unmark();
                    box("Why did you even mark this task in the first place?:",
                        " " + t.toString());
                } catch (Exception e) {
                    box("The task number is invalid!");
                }

            } else if (input.startsWith("todo ")) {
                String name = input.substring(5).trim();
                if (name.isEmpty()) {
                    box("Usage: todo <description>");
                } else {
                    Task t = new Todo(name);
                    tasks.add(t);
                    box("Added a new task:", " " + t.toString(), "There are " + tasks.size() + " tasks in total.");
                }

            } else if (input.startsWith("deadline ")) {
                String taskBy = input.substring(9).trim();
                int byIdx = taskBy.indexOf("/by");
                if (byIdx < 0) {
                    box("Usage: deadline <description> /by <time>");
                    continue;
                }
                String name = taskBy.substring(0, byIdx).trim();
                String by = taskBy.substring(byIdx + 3).trim();
                if (name.isEmpty() || by.isEmpty()) {
                    box("Usage: deadline <description> /by <time>");
                } else {
                    Task t = new Deadline(name, by);
                    tasks.add(t);
                    box("Added a new task:", " " + t.toString(), "There are " + tasks.size() + " tasks in total.");
                }

            } else if (input.startsWith("event ")) {
                String taskFromTo = input.substring(6).trim();
                int fromIdx = taskFromTo.indexOf("/from");
                int toIdx = taskFromTo.indexOf("/to");
                if (toIdx < fromIdx || fromIdx < 0 || toIdx < 0) {
                    box("Usage: event <description> /from <time> /to <time>");
                    continue;
                }
                String name = taskFromTo.substring(0, fromIdx).trim();
                String from = taskFromTo.substring(fromIdx + 5, toIdx).trim();
                String to = taskFromTo.substring(toIdx + 4);
                if (name.isEmpty() || from.isEmpty() || to.isEmpty()) {
                    box("Usage: event <description> /from <time> /to <time>");
                } else {
                    Task t = new Event(name, from, to);
                    tasks.add(t);
                    box("Added a new task:", " " + t.toString(), "There are " + tasks.size() + " tasks in total.");
                }
                
            } else if (!input.isEmpty()){
                box("Choose a type of task/enter a valid command!. Try:",
                    " 1. todo <desc>",
                    " 2. deadline <desc> /by <time>",
                    " 3. event <desc> /from <start> /to <end>",
                    " 4. list, mark N, unmark N, bye");

            } else {
                box("Input is empty........");
            }
        }
    }
}
