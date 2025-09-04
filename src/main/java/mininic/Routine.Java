package mininic;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a routine task.
 */
public class Routine extends Task {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final DayOfWeek day;
    private final LocalTime time;

    /**
     * Creates a new Routine task with time.
     * @param name
     * @param day
     * @param time
     */
    public Routine(String name, DayOfWeek day, LocalTime time) {
        super(name);
        assert name != null && !name.isBlank() : "Task name is blank!";
        assert day != null : "Routine day is null!";
        assert time != null : "Routine time is null!";
        this.day = day;
        this.time = time;
    }

    @Override public String toStorageString() {
        return "R | " + (isDone ? "1" : "0") + " | " + name + " | " + day.name() + " | " + time.toString();
    }

    @Override
    public String toString() {
        return "[R]" + super.toString() + " (every: " + day + " " + time.format(FORMATTER) + ")";
    }
}
