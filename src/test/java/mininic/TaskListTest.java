package mininic;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class TaskListTest {

    @TempDir Path tempDir;

    @Test
    void of_delete_removesCorrectTask_andShrinksList_andPersists() throws IOException {
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
