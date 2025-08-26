package mininic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task.
 */
public class Event extends Task {
    private static final DateTimeFormatter FORMATTER_DT = DateTimeFormatter.ofPattern("MMM d yyyy h:mma");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy");

    private final LocalDateTime fromDt;
    private final LocalDateTime toDt;
    private final LocalDate fromD;
    private final LocalDate toD;

    /**
     * Creates a new Event task with time.
     * @param name
     * @param from
     * @param to
     */
    public Event(String name, LocalDateTime from, LocalDateTime to) {
        super(name);
        this.fromDt = from;
        this.toDt = to;
        this.fromD = null;
        this.toD = null;
    }

    /**
     * Creates a new Event task without time.
     * @param name
     * @param from
     * @param to
     */
    public Event(String name, LocalDate from, LocalDate to) {
        super(name);
        this.fromDt = null;
        this.toDt = null;
        this.fromD = from;
        this.toD = to;
    }

    @Override public String toStorageString() {
        String from;
        String to;
        if (fromD != null && toD != null) {
            from = fromD.toString();
            to = toD.toString();
        } else {
            from = fromDt.toString();
            to = toDt.toString();
        }
        return "E | " + (isDone ? "1" : "0") + " | " + name + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        String time;
        if (fromD != null && toD != null) {
            time = "(from: " + fromD.format(FORMATTER) + " to: " + toD.format(FORMATTER) + ")";
        } else {
            time = "(from: " + fromDt.format(FORMATTER_DT) + " to: " + toDt.format(FORMATTER_DT) + ")";
        }
        return "[E]" + super.toString() + " " + time;
    }
}
