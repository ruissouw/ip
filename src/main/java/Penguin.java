import java.util.Scanner;

public class Penguin {
    public static void main(String[] args) {
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
            } else {
                System.out.println("____________________________________________________________");
                System.out.println(" " + str);
                System.out.println("____________________________________________________________");
            }
        }

        scanner.close();
    }
}
