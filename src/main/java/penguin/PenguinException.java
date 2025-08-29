package penguin;

public class PenguinException extends Exception {
    private final String errorType;

    public PenguinException(String errorType) {
        this.errorType = errorType;
    }

    @Override
    public String toString() {
        if (errorType.equals("todo") || errorType.equals("deadline") || errorType.equals("event")) {
            return "____________________________________________________________\n"
                    + errorType + " is fine but what do you want to do?\n"
                    + "____________________________________________________________";
        }
        return "____________________________________________________________\n"
                + "i'm smart but even i do not know what that means\n"
                + "____________________________________________________________";
    }
}

