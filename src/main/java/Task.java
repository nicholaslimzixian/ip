public abstract class Task {
    protected final String name;
    protected boolean isDone;

    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public String getStatus() {
        return isDone ? "X" : " ";
    }

    public abstract String toStorageString();

    public static Task fromStorageString(String s) {
        if (s == null || s.isBlank()) {
            return null;
        }
        String[] parts = s.split(" \\| ", -1);
        if (parts.length < 3) return null;

        String type = parts[0];
        boolean done = "1".equals(parts[1]);
        String desc = parts[2];

        switch (type) {
            case "T": {
                Todo t = new Todo(desc);
                if (done) {
                    t.mark();
                }
                return t;
            }
            case "D": {
                String by = parts[3];
                Deadline d = new Deadline(desc, by);
                if (done) {
                    d.mark();
                }
                return d;
            }
            case "E": {
                String from = parts[3];
                String to = parts[4];
                Event e = new Event(desc, from, to);
                if (done) {
                    e.mark();
                }
                return e;
            }
            default:
                return null;
        }
    }
    @Override
    public String toString() {
        return "[" + getStatus() + "] " + name;
    }
}
