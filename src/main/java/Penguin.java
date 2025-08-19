import java.util.ArrayList;
import java.util.Scanner;

public class Penguin {
    public static void main(String[] args) {
        int curr = 0;
        ArrayList<Task> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Penguin");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");

        while (scanner.hasNextLine()) {
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
            } else {
                try {
                    String[] split = str.split(" ", 2);

                    if (!split[0].equals("todo") && !split[0].equals("deadline") && !split[0].equals("event")) {
                        throw new PenguinException(split[0]);
                    }

                    if (split[0].equals("todo")) {
                        if (split.length == 1 || split[1].trim().isEmpty()) {
                            throw new PenguinException("todo");
                        }
                        tasks.add(new Todo(split[1]));
                    } else if (split[0].equals("deadline")) {
                        if (split.length == 1 || split[1].trim().isEmpty()) {
                            throw new PenguinException("deadline");
                        }
                        String[] split2 = split[1].split("/by");
                        tasks.add(new Deadline(split2[0].trim(), split2[1].trim()));
                    } else if (split[0].equals("event")) {
                        if (split.length == 1 || split[1].trim().isEmpty()) {
                            throw new PenguinException("event");
                        }
                        String[] split2 = split[1].split("/from");
                        String[] split3 = split2[1].split("/to");
                        tasks.add(new Event(split2[0].trim(), split3[0].trim(), split3[1].trim()));
                    }

                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println(tasks.get(curr));
                    curr++;
                    System.out.println("Now you have " + curr + " tasks in the list.");
                    System.out.println("____________________________________________________________");

                } catch (PenguinException pe) {
                    System.out.println(pe);
                }
            }
        }
        scanner.close();
    }
}
