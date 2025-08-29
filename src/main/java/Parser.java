import java.io.IOException;

public class Parser {
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

    public static boolean parse(String str, TaskList tasks, Ui ui, Storage storage) {
        if (str.equals("bye")) {
            try {
                storage.writeAllTasks(tasks.getTasks());
            } catch (IOException e) {
                ui.printErrorMessage(e.getMessage());
            }
            ui.sayGoodbye();
            return false;
        } else if (str.equals("list")) {
            ui.printList(tasks.getTasks());
        } else if (str.contains("mark") || str.contains("unmark")) {
            String[] split = str.split(" ");
            int idx = Integer.parseInt(split[1]) - 1;

            String msg = "";
            if (split[0].equals("mark")) {
                tasks.markAsDone(idx);
                ui.markTask(tasks.getTask(idx));
            } else if (split[0].equals("unmark")) {
                tasks.markAsUndone(idx);
                ui.unmarkTask(tasks.getTask(idx));
            }
        } else if (str.contains("delete")) {
            String[] split = str.split(" ");
            int idx = Integer.parseInt(split[1]) - 1;
            Task task = tasks.deleteTask(idx);
            ui.deleteTask(tasks.getNumOfTasks(), task);
        } else {
            try {
                String[] split = str.split(" ", 2);
                TaskType taskType = TaskType.convert(split[0]);

                switch (taskType) {
                case TODO:
                    if (split.length == 1 || split[1].trim().isEmpty()) {
                        throw new PenguinException("todo");
                    }
                    tasks.addTask(new Todo(split[1].trim()));
                    break;

                case DEADLINE:
                    if (split.length == 1 || split[1].trim().isEmpty()) {
                        throw new PenguinException("deadline");
                    }
                    String[] split2 = split[1].split("/by", 2);
                    tasks.addTask(new Deadline(split2[0].trim(), split2[1].trim()));
                    break;

                case EVENT:
                    if (split.length == 1 || split[1].trim().isEmpty()) {
                        throw new PenguinException("event");
                    }
                    String[] split3 = split[1].split("/from", 2);
                    String[] split4 = split3[1].split("/to", 2);
                    tasks.addTask(new Event(split3[0].trim(), split4[0].trim(), split4[1].trim()));
                    break;
                }
                ui.addTask(tasks.getNumOfTasks(), tasks.getTask(tasks.getNumOfTasks() - 1));
            } catch (PenguinException pe) {
                ui.printErrorMessage(pe.toString());
            }
        }
        return true;
    }
}
