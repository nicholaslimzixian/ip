public class Deadline extends Task {
    protected final String by;

    public Deadline(String name, String by) {
        super(name);
        this.by = by;
    }

    @Override public String toStorageString() {
        return "D | " + (isDone ? "1" : "0") + " | " + name + " | " + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
