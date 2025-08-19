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

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            if ("bye".equals(input.trim())) {
                box("Bye... :'(");
                break;
            }
            box(input);
        }
    }
}
