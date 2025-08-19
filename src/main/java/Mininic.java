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

        String[] tasks = new String[100];
        int size = 0;

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine().trim();
            if (input.equals("bye")) {
                box("Bye... :'(");
                sc.close();
                break;
            } else if (input.equals("list")) {
                if (size == 0) {
                    box();
                } else {
                    String[] lines = new String[size];
                    for (int i = 0; i < size; i++) {
                        lines[i] = (i + 1) + ". " + tasks[i];
                    }
                    box(lines);
                }
            } else {
                if (size < tasks.length) {
                    tasks[size++] = input;
                    box("Task added: " + input);
                } else {
                    box("I'm very full... Clear some tasks to add more!");
                }
            }
        }
    }
}
