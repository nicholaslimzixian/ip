package mininic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class StorageTest {

    @TempDir Path tmp;

    @Test
    void loadMissingFile() throws IOException {
        Path p = tmp.resolve("data").resolve("tasks.txt"); // non-existent yet
        Storage s = new Storage(p.toString());
        List<Task> tasks = s.load(); // expect no throw
        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }

    @Test
    void saveAndLoad() throws IOException {
        Path p = tmp.resolve("data").resolve("tasks.txt");
        Storage s = new Storage(p.toString());
        List<Task> toSave = new ArrayList<>();
        toSave.add(new Todo("read book"));
        toSave.add(new Deadline("return book", LocalDate.parse("2019-12-02")));
        s.save(toSave);

        List<Task> loaded = s.load();
        assertEquals(2, loaded.size());
        assertTrue(loaded.get(0).toString().contains("read book"));
        assertTrue(loaded.get(1).toString().contains("return book"));
    }
}
