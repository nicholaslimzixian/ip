public class Task {
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

    @Override
    public String toString() {
        return "[" + getStatus() + "] " + name;
    }
}
