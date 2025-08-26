package mininic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    private final List<Task> tasks;
    private final Storage storage;

    /**
     * Creates a new TaskList instance.
     * @param initial
     * @param storage
     */
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

    /**
     * Converts the task list to a list of strings, each representing a task.
     * @return A list of strings representing the tasks.
     */
    public List<String> asLines() {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            lines.add((i + 1) + ". " + tasks.get(i).toString());
        }
        return lines;
    }

    /**
     * Adds the task into the task list.
     * @param t
     * @return A Task object representing the added task.
     * @throws IOException
     */
    public Task add(Task t) throws IOException {
        tasks.add(t);
        storage.save(tasks);
        return t;
    }

    /**
     * Marks the task at the specified index as done.
     * @param idx The index of the task to mark.
     * @return A Task object representing the marked task.
     * @throws IOException
     */
    public Task mark(int idx) throws IOException {
        Task t = tasks.get(idx);
        t.mark();
        storage.save(tasks);
        return t;
    }

    /**
     * Unmarks the task at the specified index.
     * @param idx The index of the task to unmark.
     * @return A Task object representing the unmarked task.
     * @throws IOException
     */
    public Task unmark(int idx) throws IOException {
        Task t = tasks.get(idx);
        t.unmark();
        storage.save(tasks);
        return t;
    }

    /**
     * Deletes the task from the task list.
     * @param idx The index of the task to delete.
     * @return A Task object representing the deleted task.
     * @throws IOException
     */
    public Task delete(int idx) throws IOException {
        Task t = tasks.remove(idx);
        storage.save(tasks);
        return t;
    }

    /**
     * Finds tasks whose names contain the given keyword.
     * @param keyword
     * @return A list of matching tasks.
     */
    public List<Task> find(String keyword) {
        List<Task> results = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(t);
            }
        }
        return results;
    }
}
