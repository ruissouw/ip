import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private static final String FILE_PATH = "./data/penguin.txt";

    public void createFolder() {
        new File("./data").mkdirs();
    }

    public void createFile() throws IOException {
        new File(FILE_PATH).createNewFile();
    }

    public void readFileContents(List<Task> tasks) throws FileNotFoundException {
        File file = new File(FILE_PATH);
        Scanner s = new Scanner(file);

        while (s.hasNext()) {
            String[] split = s.nextLine().split(" \\| ");
            Task task = null;
            if (split[0].equals("T")) {
                task = new Todo(split[2]);
            } else if (split[0].equals("D")) {
                task = new Deadline(split[2], split[3]);
            } else if (split[0].equals("E")) {
                task = new Event(split[2], split[3], split[4]);
            }

            if (split[1].equals("[X]") && task != null) {
                task.markAsDone();
            }
            tasks.add(task);
        }
    }

    public void writeAllTasks(List<Task> tasks) throws IOException {
        FileWriter fw = new FileWriter(FILE_PATH);
        for (Task task : tasks) {
            String line = null;
            if (task instanceof Todo) {
                line = "T | " + task.getStatusIcon() + " | " + task.getDescription();
            } else if (task instanceof Deadline) {
                line = "D | " + task.getStatusIcon() + " | " + task.getDescription()
                        + " | " + ((Deadline) task).getDeadline();
            } else if (task instanceof Event) {
                line = "E | " + task.getStatusIcon() + " | " + task.getDescription()
                        + " | " + ((Event) task).getStart() + " | " + ((Event) task).getEnd();
            }
            fw.write(line + System.lineSeparator());
        }
        fw.close();
    }
}
