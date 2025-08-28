import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;


public class Penguin {
    private static List<Task> tasks = new ArrayList<>();
    private static Ui ui = new Ui();
    private static Storage storage = new Storage();

    public enum TaskType {
        TODO,
        DEADLINE,
        EVENT;

        public static TaskType convert(String str) throws PenguinException {
            switch (str.toLowerCase()) {
                case "todo": return TODO;
                case "deadline": return DEADLINE;
                case "event": return EVENT;
                default: throw new PenguinException(str);
            }
        }
    }

    /*
    private static void readFileContents(String filePath) throws FileNotFoundException {
        File f = new File(filePath);
        Scanner s = new Scanner(f);
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

            if (split[1].equals("[X]")) {
                task.markAsDone();
            }
            tasks.add(task);
        }
    }

    private static void writeAllTasks(String filePath, List<Task> tasks) throws IOException {
        FileWriter fw = new FileWriter(filePath);
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
     */

    public static void main(String[] args) {
        try {
            storage.readFileContents(tasks);
        } catch (FileNotFoundException e) {
            // new File("./data").mkdirs();
            storage.createFolder();
            try {
                // new File(FILE_PATH).createNewFile();
                storage.createFile();
            } catch (IOException io) {
                ui.printErrorMessage("Unable to create file: " + io.getMessage());
            }
        }

        ui.sayWelcome();

        while (true) {
            String str = ui.readLine();
            if (str.equals("bye")) {
                try {
                    storage.writeAllTasks(tasks);
                } catch (IOException e) {
                    ui.printErrorMessage(e.getMessage());
                }
                ui.sayGoodbye();
                break;
            } else if (str.equals("list")) {
                ui.printList(tasks);
            } else if (str.contains("mark") || str.contains("unmark")) {
                String[] split = str.split(" ");
                int idx = Integer.parseInt(split[1]) - 1;
                Task task = tasks.get(idx);

                String msg = "";
                if (split[0].equals("mark")) {
                    task.markAsDone();
                    ui.markTask(task);
                } else if (split[0].equals("unmark")) {
                    task.markAsUndone();
                    ui.unmarkTask(task);
                }
            } else if (str.contains("delete")) {
                String[] split = str.split(" ");
                int idx = Integer.parseInt(split[1]) - 1;
                Task task = tasks.remove(idx);
                ui.deleteTask(tasks.size(), task);
            } else {
                try {
                    String[] split = str.split(" ", 2);
                    TaskType taskType = TaskType.convert(split[0]);

                    switch (taskType) {
                    case TODO:
                        if (split.length == 1 || split[1].trim().isEmpty()) {
                            throw new PenguinException("todo");
                        }
                        tasks.add(new Todo(split[1].trim()));
                        break;

                    case DEADLINE:
                        if (split.length == 1 || split[1].trim().isEmpty()) {
                            throw new PenguinException("deadline");
                        }
                        String[] split2 = split[1].split("/by", 2);
                        tasks.add(new Deadline(split2[0].trim(), split2[1].trim()));
                        break;

                    case EVENT:
                        if (split.length == 1 || split[1].trim().isEmpty()) {
                            throw new PenguinException("event");
                        }
                        String[] split3 = split[1].split("/from", 2);
                        String[] split4 = split3[1].split("/to", 2);
                        tasks.add(new Event(split3[0].trim(), split4[0].trim(), split4[1].trim()));
                        break;
                    }
                    ui.addTask(tasks.size(), tasks.get(tasks.size() - 1));
                } catch (PenguinException pe) {
                    ui.printErrorMessage(pe.toString());
                }
            }
        }
        ui.close();
    }
}
