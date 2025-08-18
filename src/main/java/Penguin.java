import java.util.Scanner;
import java.util.Arrays;

public class Penguin {
    public static void main(String[] args) {
        int curr = 0;
        String[] lst = new String[100];
        Boolean[] statuses = new Boolean[100];
        Arrays.fill(statuses, false);
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
                    String status = statuses[curr] ? "X" : " ";
                    System.out.println(String.format("%d.[%s] %s", i + 1, status, lst[i]));
                }
                System.out.println("____________________________________________________________");
            } else {
                lst[curr] = str;
                curr++;
                System.out.println("____________________________________________________________");
                System.out.println("added: " + str);
                System.out.println("____________________________________________________________");
            }
        }

        scanner.close();
    }
}
