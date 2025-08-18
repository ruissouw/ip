import java.util.Scanner;

public class Penguin {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Penguin \nWhat can I do for you?");
        System.out.println("Bye. Hope to see you again soon!");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();

        if (str != "bye") {
            System.out.println(str);
        } else if (str == "bye") {
            System.out.println("Bye. Hope to see you again soon!");
        }
    }
}
