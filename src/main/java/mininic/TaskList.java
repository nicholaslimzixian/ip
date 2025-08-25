package mininic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;
    private final Storage storage;

    public TaskList(List<Task> initial, Storage storage) {
        if (initial != null) {
            this.tasks = initial;
        } else {
            this.tasks = new ArrayList<>();
        }
        this.storage = storage;
    }

    public int size() {
        return tasks.size();
    }

    public List<String> asLines() {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            lines.add((i + 1) + ". " + tasks.get(i).toString());
        }
        return lines;
    }

    public Task add(Task t) throws IOException {
        tasks.add(t);
        storage.save(tasks);
        return t;
    }

    public Task mark(int idx) throws IOException {
        Task t = tasks.get(idx);
        t.mark();
        storage.save(tasks);
        return t;
    }

    public Task unmark(int idx) throws IOException {
        Task t = tasks.get(idx);
        t.unmark();
        storage.save(tasks);
        return t;
    }

    public Task delete(int idx) throws IOException {
        Task t = tasks.remove(idx);
        storage.save(tasks);
        return t;
    }
}
