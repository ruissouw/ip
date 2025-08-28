import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;


public class Penguin {
    private static final String FILE_PATH = "./data/penguin.txt";
    private static List<Task> tasks = new ArrayList<>();

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

    public static void main(String[] args) {
        try {
            readFileContents(FILE_PATH);
        } catch (FileNotFoundException e) {
            new File("./data").mkdirs();
            try {
                new File(FILE_PATH).createNewFile();
            } catch (IOException io) {
                System.out.println("Unable to create file: " + io.getMessage());
            }
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Penguin");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");

        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            if (str.equals("bye")) {
                try {
                    writeAllTasks(FILE_PATH, tasks);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (str.equals("list")) {
                System.out.println("____________________________________________________________");
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    Task task = tasks.get(i);
                    System.out.println(String.format("%d.%s", i + 1, task));
                }
                System.out.println("____________________________________________________________");
            } else if (str.contains("mark") || str.contains("unmark")) {
                String[] split = str.split(" ");
                int idx = Integer.parseInt(split[1]) - 1;
                Task task = tasks.get(idx);

                String msg = "";
                if (split[0].equals("mark")) {
                    task.markAsDone();
                    msg = "Nice! I've marked this task as done:";
                } else if (split[0].equals("unmark")) {
                    task.markAsUndone();
                    msg = "OK, I've marked this task as not done yet:";
                }

                System.out.println("____________________________________________________________");
                System.out.println(msg);
                System.out.println(String.format("%s %s", task.getStatusIcon(), task.getDescription()));
                System.out.println("____________________________________________________________");
            } else if (str.contains("delete")) {
                String[] split = str.split(" ");
                int idx = Integer.parseInt(split[1]) - 1;
                Task task = tasks.remove(idx);

                System.out.println("____________________________________________________________");
                System.out.println("Noted. I've removed this task:");
                System.out.println(task);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                System.out.println("____________________________________________________________");
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

                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println(tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");

                } catch (PenguinException pe) {
                    System.out.println(pe);
                }
            }
        }
        scanner.close();
    }
}
