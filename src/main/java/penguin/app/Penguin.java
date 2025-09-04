package penguin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


/**
 * The {@code Penguin} program is a chatbot that keeps track of your tasks.
 * <p>
 * The chatbot listens to user input and does/says something accordingly.
 */
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

    public String setUp() {
        return ui.sayWelcome();
    }

    public String getResponse(String input) {
        return Parser.parse(input, tasks, ui, storage);
    }
}
