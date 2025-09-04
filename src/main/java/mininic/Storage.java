package mininic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles storage of tasks in the file system.
 */
public class Storage {
    private final Path file;

    public Storage(String relativePath) {
        this.file = Path.of(relativePath);
    }

    /**
     * Loads the tasks from the storage file.
     * @return A list of tasks loaded from the storage file.
     */
    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        try {
            if (!Files.exists(file)) {
                if (file.getParent() != null) {
                    Files.createDirectories(file.getParent());
                }
                Files.createFile(file);
                return tasks;
            }
            try (BufferedReader fileReader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                String line;
                while ((line = fileReader.readLine()) != null) {
                    Task t = Task.fromStorageString(line);
                    if (t != null) {
                        tasks.add(t);
                    }
                }
            }
        } catch (IOException ignored) {
            //ignore read errors and return an empty list
        }
        assert tasks != null : "Loaded list must not be null";
        return tasks;
    }

    /**
     * Saves the tasks to the storage file.
     * @param tasks
     * @throws IOException
     */
    public void save(List<Task> tasks) throws IOException {
        if (file.getParent() != null && !Files.exists(file.getParent())) {
            Files.createDirectories(file.getParent());
        }
        try (BufferedWriter fileWriter = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            for (Task t : tasks) {
                fileWriter.write(t.toStorageString());
                fileWriter.newLine();
            }
        }
    }
}

