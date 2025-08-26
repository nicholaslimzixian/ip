package mininic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests the find functionality of the TaskList class.
 */
public class FindTest {

    @TempDir Path tempDir;

    private TaskList newTaskList() {
        Path file = tempDir.resolve("tasks.txt");
        Storage storage = new Storage(file.toString());
        return new TaskList(new ArrayList<Task>(), storage);
    }

    @Test
    void findCaseInsensitive() throws IOException {
        TaskList tl = newTaskList();
        tl.add(new Todo("read book"));
        tl.add(new Todo("Return BOOK to library"));
        tl.add(new Todo("buy bread"));

        List<Task> hits = tl.find("book");
        assertEquals(2, hits.size());
        assertTrue(hits.get(0).toString().contains("read book"));
        assertTrue(hits.get(1).toString().toLowerCase().contains("return book to library"));
    }

    @Test
    void findNoMatches() throws IOException {
        TaskList tl = newTaskList();
        tl.add(new Todo("alpha"));
        tl.add(new Todo("beta"));

        List<Task> hits = tl.find("gamma");
        assertTrue(hits.isEmpty());
    }

    @Test
    void findEmptyKeyword() throws IOException {
        TaskList tl = newTaskList();
        tl.add(new Todo("anything"));
        assertThrows(NullPointerException.class, () -> tl.find(null));
    }
}
