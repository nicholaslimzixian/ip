package mininic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy");
    private final LocalDate byDate;


    /**
     * Creates a new Deadline task.
     * @param name
     * @param byDate
     */
    public Deadline(String name, LocalDate byDate) {
        super(name);
        assert name != null && !name.isBlank() : "Task name is blank!";
        assert byDate != null : "By date is null!";
        this.byDate = byDate;
    }

    /**
     * Converts the task to a format suitable for storage.
     */
    @Override public String toStorageString() {
        String by = byDate.toString();
        return "D | " + (isDone ? "1" : "0") + " | " + name + " | " + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byDate.format(FORMATTER) + ")";
    }
}
