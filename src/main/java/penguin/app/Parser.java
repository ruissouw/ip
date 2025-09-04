package penguin.app;

import penguin.Tasks.Deadline;
import penguin.Tasks.Event;
import penguin.Tasks.Task;
import penguin.Tasks.Todo;

import java.io.IOException;
import java.util.List;

/**
 * Interprets and processes user commands
 */
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

    /**
     * Renders different actions depending on user command
     * <p>
     * User commands:
     * <ul>
     *     <li>Bye: exits the program and in doing so save all tasks in storage and say goodbye</li>
     *     <li>List: prints list of tasks</li>
     *     <li>mark/unmark: mark/unmark the task</li>
     *     <li>delete: delete the task</li>
     *     <li>
     *         rest: if name of task specified, create the corresponding task
     *         else, print error message
     *     </li>
     * </ul>
     * @param str User command
     * @param tasks TaskList
     * @param ui the UI object
     * @param storage the Storage object
     * @return whether to exit the program
     */
    public static String parse(String str, TaskList tasks, Ui ui, Storage storage) {
        if (str.equals("bye")) {
            try {
                storage.writeAllTasks(tasks.getTasks());
            } catch (IOException e) {
                return ui.printErrorMessage(e.getMessage());
            }
            return ui.sayGoodbye();
        } else if (str.equals("list")) {
            return ui.printList(tasks.getTasks(), "Here are the tasks in your list:");
        } else if (str.contains("mark") || str.contains("unmark")) {
            String[] split = str.split(" ");
            int idx = Integer.parseInt(split[1]) - 1;

            String msg = "";
            if (split[0].equals("mark")) {
                tasks.markAsDone(idx);
                return ui.markTask(tasks.getTask(idx));
            } else if (split[0].equals("unmark")) {
                tasks.markAsUndone(idx);
                return ui.unmarkTask(tasks.getTask(idx));
            }
        } else if (str.contains("delete")) {
            String[] split = str.split(" ");
            int idx = Integer.parseInt(split[1]) - 1;
            Task task = tasks.deleteTask(idx);
            return ui.deleteTask(tasks.getNumOfTasks(), task);
        } else if (str.contains("find")) {
            String[] split = str.split(" ");
            List<Task> filtered = tasks.findTasks(split[1]);
            return ui.printList(filtered, "Here are the matching tasks in your list:");
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
                return ui.addTask(tasks.getNumOfTasks(), tasks.getTask(tasks.getNumOfTasks() - 1));
            } catch (PenguinException pe) {
                return ui.printErrorMessage(pe.toString());
            }
        }
        return "i dont understand ur input";
    }
}
