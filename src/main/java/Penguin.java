import java.util.Scanner;

public class Penguin {
    public static void main(String[] args) {
        int curr = 0;
        Task[] lst = new Task[100];
        Scanner scanner = new Scanner(System.in);
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Penguin");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");

        while (true) {
            String str = scanner.nextLine();
            if (str.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (str.equals("list")) {
                System.out.println("____________________________________________________________");
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < curr; i++) {
                    Task task = lst[i];
                    System.out.println(String.format("%d.%s", i + 1, task));
                }
                System.out.println("____________________________________________________________");
            } else if (str.contains("mark") || str.contains("unmark")) {
                String[] split = str.split(" ");
                int idx = Integer.parseInt(split[1]) - 1;
                Task task = lst[idx];

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
                System.out.println(String.format("[%s] %s", task.getStatusIcon(), task.getDescription()));
                System.out.println("____________________________________________________________");
            } else {
                String[] split = str.split(" ", 2);
                if (split[0].equals("todo")) {
                    lst[curr] = new Todo(split[1]);
                } else if (split[0].equals("deadline")) {
                    String[] split2 = split[1].split("/by");
                    lst[curr] = new Deadline(split2[0], split2[1]);
                } else if (split[0].equals("event")) {
                    String[] split2 = split[1].split("/from");
                    String[] split3 = split2[1].split("/to");
                    lst[curr] = new Event(split2[0], split3[0], split3[1]);
                }
                System.out.println("____________________________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println(lst[curr]);
                curr++;
                System.out.println(String.format("Now you have %d tasks in the list.", curr));
                System.out.println("____________________________________________________________");
            }
        }

        scanner.close();
    }
}
