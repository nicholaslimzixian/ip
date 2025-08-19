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

            } else if (!input.isEmpty()){
                Task newTask = new Task(input);
                tasks.add(newTask);
                box("Task added: " + newTask.toString());

            } else {
                box("Input is empty........");
            }
        }
    }
}
