import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class Penguin {
    private static TaskList tasks;
    private static Ui ui;
    private static Storage storage;

    public Penguin() {
        ui = new Ui();
        storage = new Storage();

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
    }

    public void run() {
        ui.sayWelcome();

        boolean isOngoing = true;
        while (isOngoing) {
            String str = ui.readLine();
            isOngoing = Parser.parse(str, tasks, ui, storage);
        }
        ui.close();
    }

    public static void main(String[] args) {
        new Penguin().run();
    }
}
