package mininic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy");
    private final LocalDate byDate;


    public Deadline(String name, LocalDate byDate) {
        super(name);
        this.byDate = byDate;
    }

    @Override public String toStorageString() {
        String by = byDate.toString();
        return "D | " + (isDone ? "1" : "0") + " | " + name + " | " + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byDate.format(FORMATTER) + ")";
    }
}
