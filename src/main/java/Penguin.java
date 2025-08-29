import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class Penguin {
    private static TaskList tasks;
    private static Ui ui = new Ui();
    private static Storage storage = new Storage();

    public static void main(String[] args) {
        try {
            tasks = new TaskList(storage.load());
        } catch (FileNotFoundException e) {
            storage.createFolder();
            try {
                storage.createFile();
                tasks = new TaskList(new ArrayList<>());
            } catch (IOException io) {
                ui.printErrorMessage("Unable to create file: " + io.getMessage());
                tasks = new TaskList(new ArrayList<>());
            }
        }

        ui.sayWelcome();

        boolean isOngoing = true;
        while (isOngoing) {
            String str = ui.readLine();
            isOngoing = Parser.parse(str, tasks, ui, storage);
        }
        ui.close();
    }
}
