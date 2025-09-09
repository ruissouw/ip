package penguin.app;

import java.io.IOException;
import java.util.List;

import penguin.tasks.Deadline;
import penguin.tasks.Event;
import penguin.tasks.Task;
import penguin.tasks.Todo;

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
        final String BYE_COMMAND = "bye";
        final String LIST_COMMAND = "list";
        final String MARK_COMMAND = "mark";
        final String UNMARK_COMMAND = "unmark";
        final String DELETE_COMMAND = "delete";
        final String FIND_COMMAND = "find";
        final String PRINT_ALL_TASKS_MESSAGE = "Here are the tasks in your list:";
        final String PRINT_MATCHING_TASKS_MESSAGE = "Here are the matching tasks in your list:";
        final String TODO = "todo";
        final String DEADLINE = "deadline";
        final String EVENT = "event";
        final String UNKNOWN_TASK_TYPE = "Unknown task type: ";
        final String DONT_UNDERSTAND = "i dont understand ur input";

        if (str.equals(BYE_COMMAND)) {
            try {
                storage.writeAllTasks(tasks.getTasks());
            } catch (IOException e) {
                return ui.printErrorMessage(e.getMessage());
            }
            return ui.sayGoodbye();
        } else if (str.equals(LIST_COMMAND)) {
            return ui.printList(tasks.getTasks(), PRINT_ALL_TASKS_MESSAGE);
        } else if (str.contains(MARK_COMMAND) || str.contains(UNMARK_COMMAND)) {
            String[] split = str.split(" ");
            String command = split[0];
            int idx = Integer.parseInt(split[1]) - 1;
            assert idx > 0 : "idx cannot be less than 0";

            String msg = "";
            if (command.equals(MARK_COMMAND)) {
                tasks.markAsDone(idx);
                return ui.markTask(tasks.getTask(idx));
            } else if (command.equals(UNMARK_COMMAND)) {
                tasks.markAsUndone(idx);
                return ui.unmarkTask(tasks.getTask(idx));
            }
        } else if (str.contains(DELETE_COMMAND)) {
            String[] split = str.split(" ");
            int idx = Integer.parseInt(split[1]) - 1;
            assert idx > 0 : "idx cannot be less than 0";
            Task task = tasks.deleteTask(idx);
            return ui.deleteTask(tasks.getNumOfTasks(), task);
        } else if (str.contains(FIND_COMMAND)) {
            String[] split = str.split(" ");
            List<Task> filtered = tasks.findTasks(split[1]);
            return ui.printList(filtered, PRINT_MATCHING_TASKS_MESSAGE);
        } else {
            try {
                String[] split = str.split(" ", 2);
                TaskType taskType = TaskType.convert(split[0]);
                String taskName = split[1].trim();

                switch (taskType) {
                case TODO:
                    if (split.length == 1 || taskName.isEmpty()) {
                        throw new PenguinException(TODO);
                    }
                    tasks.addTask(new Todo(taskName));
                    break;

                case DEADLINE:
                    if (split.length == 1 || taskName.isEmpty()) {
                        throw new PenguinException(DEADLINE);
                    }
                    String[] split2 = taskName.split("/by", 2);
                    String description = split2[0].trim();
                    String deadline = split2[1].trim();
                    tasks.addTask(new Deadline(description, deadline));
                    break;

                case EVENT:
                    if (split.length == 1 || taskName.isEmpty()) {
                        throw new PenguinException(EVENT);
                    }
                    String[] nameAndDates = taskName.split("/from", 2);
                    String name = nameAndDates[0].trim();
                    String[] toAndFrom = nameAndDates[1].split("/to", 2);
                    String to = toAndFrom[0].trim();
                    String from = toAndFrom[1].trim();
                    tasks.addTask(new Event(name, to, from));
                    break;

                default:
                    throw new PenguinException(UNKNOWN_TASK_TYPE + taskType);
                }
                return ui.addTask(tasks.getNumOfTasks(), tasks.getTask(tasks.getNumOfTasks() - 1));
            } catch (PenguinException pe) {
                return ui.printErrorMessage(pe.toString());
            }
        }
        return DONT_UNDERSTAND;
    }
}
