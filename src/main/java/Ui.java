import java.util.List;
import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }

    public void printLine() {
        System.out.println("____________________________________________________________");
    }

    public void sayWelcome() {
        printLine();
        System.out.println(" Hello! I'm Penguin");
        System.out.println(" What can I do for you?");
        printLine();
    }

    public void sayGoodbye() {
        printLine();
        System.out.println(" Bye. Hope to see you again soon!");
        printLine();
    }

    public void printList(List<Task> tasks) {
        printLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.println(String.format("%d.%s", i + 1, task));
        }
        printLine();
    }

    public void markTask(Task task) {
        printLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(String.format("%s %s", task.getStatusIcon(), task.getDescription()));
        printLine();
    }

    public void unmarkTask(Task task) {
        printLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(String.format("%s %s", task.getStatusIcon(), task.getDescription()));
        printLine();
    }

    public void deleteTask(int totalTasks, Task task) {
        printLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        printLine();
    }

    public void addTask(int totalTasks, Task task) {
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        printLine();
    }

    public void printErrorMessage(String msg) {
        System.out.println(msg);
    }
}
