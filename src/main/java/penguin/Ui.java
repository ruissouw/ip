package penguin;

import java.util.List;
import java.util.Scanner;

/**
 * The class in charge of dealing with user interactions
 */
public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads the next line of user input
     * @return User Input
     */
    public String readLine() {
        return scanner.nextLine();
    }

    /**
     * Closes the scanner
     */
    public void close() {
        scanner.close();
    }

    /**
     * Pretty prints the border lines
     */
    public void printLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Greets the user when the app starts
     */
    public void sayWelcome() {
        printLine();
        System.out.println(" Hello! I'm Penguin");
        System.out.println(" What can I do for you?");
        printLine();
    }

    /**
     * Says goodbye to the user when the app closes
     */
    public void sayGoodbye() {
        printLine();
        System.out.println(" Bye. Hope to see you again soon!");
        printLine();
    }

    public void printList(List<Task> tasks, String msg) {
        printLine();
        System.out.println(msg);
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.println(String.format("%d.%s", i + 1, task));
        }
        printLine();
    }

    /**
     * Notifies the user that the task has been marked
     * @param task Task to be marked
     */
    public void markTask(Task task) {
        printLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(String.format("%s %s", task.getStatusIcon(), task.getDescription()));
        printLine();
    }

    /**
     * Notifies the user that the task has been unmarked
     * @param task Task to be unmarked
     */
    public void unmarkTask(Task task) {
        printLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(String.format("%s %s", task.getStatusIcon(), task.getDescription()));
        printLine();
    }

    /**
     * Notifies the user that the task has been deleted
     * @param task Task to be deleted
     * @param totalTasks Number of tasks left in the list
     */
    public void deleteTask(int totalTasks, Task task) {
        printLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        printLine();
    }

    /**
     * Notifies user that the task has been added
     * @param totalTasks New number of tasks in the list
     * @param task Task to be added
     */
    public void addTask(int totalTasks, Task task) {
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        printLine();
    }

    /**
     * Notifies user of error
     * @param msg Error messsage to be printed
     */
    public void printErrorMessage(String msg) {
        System.out.println(msg);
    }
}
