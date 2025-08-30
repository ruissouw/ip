package penguin;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> tasks;

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public int getNumOfTasks() {
        return this.tasks.size();
    }

    public Task getTask(int idx) {
        return this.tasks.get(idx);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int idx) {
        return tasks.remove(idx);
    }

    public void markAsDone(int idx) {
        tasks.get(idx).markAsDone();
    }

    public void markAsUndone(int idx) {
        tasks.get(idx).markAsUndone();
    }

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
