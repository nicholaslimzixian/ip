package mininic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests the TaskList class.
 */
public class TaskListTest {

    @TempDir Path tempDir;

    private TaskList newTaskList() {
        Path file = tempDir.resolve("tasks.txt");
        Storage storage = new Storage(file.toString());
        return new TaskList(new ArrayList<Task>(), storage);
    }

    @Test
    void removeTask() throws IOException {
        Path data = tempDir.resolve("tasks.txt");
        Storage storage = new Storage(data.toString());

        TaskList tl = new TaskList(new ArrayList<>(), storage);
        tl.add(new Todo("read book"));
        tl.add(new Deadline("return book", LocalDate.parse("2019-12-02")));

        Task removed = tl.delete(0);

        assertTrue(removed.toString().contains("read book"));
        assertEquals(1, tl.size());
        assertTrue(tl.asLines().get(0).contains("return book"));
        assertTrue(java.nio.file.Files.exists(data));
    }
}
