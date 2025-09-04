package penguin.app;

import java.util.ArrayList;
import java.util.List;

import penguin.tasks.Task;

/**
 * The class that keeps track of the tasks in the list and performs operations
 */
public class TaskList {
    private List<Task> tasks;

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Useful for pretty printing all tasks in the list
     * @return all tasks in the list
     */
    public List<Task> getTasks() {
        return this.tasks;
    }

    /**
     * @return Number of tasks in the list
     */
    public int getNumOfTasks() {
        return this.tasks.size();
    }

    /**
     * @param idx index of the task in the list
     * @return task at specified index
     */
    public Task getTask(int idx) {
        return this.tasks.get(idx);
    }

    /**
     * adds task to the list
     * @param task task to be added to the list
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * deletes task at specified index
     * @param idx index of task to be deleted to the list
     */
    public Task deleteTask(int idx) {
        return tasks.remove(idx);
    }

    /**
     * marks task at specified index as done
     * @param idx index of task in list
     */
    public void markAsDone(int idx) {
        tasks.get(idx).markAsDone();
    }

    /**
     * marks task at specified index as undone
     * @param idx index of task in list
     */
    public void markAsUndone(int idx) {
        tasks.get(idx).markAsUndone();
    }

    /**
     * @param keyword keyword to search for
     * @return list of tasks containing keyword
     */
    public List<Task> findTasks(String keyword) {
        ArrayList<Task> filtered = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                filtered.add(task);
            }
        }
        return filtered;
    }
}
