package mininic;

import java.util.List;

public class Ui {
    private static final String LINE = "____________________________________________________________";

    public static void box(String... lines) {
        System.out.println(LINE);
        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println(LINE + "\n");
    }

    public void welcomeMessage() {
        box("Hello! I'm Mininic", "Your wish is my command!");
    }

    public void byeMessage() {
        box("Bye... :'(");
    }

    public void showTaskList(List<String> tasks) {
        box(tasks.toArray(new String[0]));
    }

    public void showAdded(Task t, int size) {
        box("Added a new task:",
            " " + t.toString(),
            "There are " + size + " tasks in total.");
    }

    public void showMarked(Task t) {
        box("One task down, many more to go...:", " " + t.toString());
    }

    public void showUnmarked(Task t) {
        box("Why did you even mark this task in the first place?:",
            " " + t.toString());
    }

    public void showDeleted(Task t, int size) {
        box("This task has been removed:", " " + t.toString(),
            "There are " + size + " tasks in total.");
    }

    public void showError(String message) {
        box(message);
    }

    public void showUnknownCommand() {
        box(
"Enter a valid command!. Try:",
          "1. todo <desc>",
          "2. deadline <desc> /by yyyy-mm-dd",
          "3. event <desc> /from yyyy-mm-dd HHmm /to yyyy-mm-dd HHmm",
          "4. list",
          "5. mark <N>, unmark <N>",
          "6. delete <N>",
          "7. bye"
          );
    }
}
