package os;

import com.github.javafaker.Faker;
import ui.Terminal;

public class CommandHandler {

    public static void handleCD(String... args) {
        if(args.length == 1) {
            OS.FS.changeDirectory(args[0]);
        }
    }

    public static void handleLS() {
        OS.FS.getDirectoryContent().forEach(s -> Terminal.out.println(s));
    }

    public static void handleCONVO() {
        Terminal.out.println("> Hey, my name is Peter, I'm the hsOS assistent.");
        Terminal.out.println("> How can I help you?");

        Faker faker = new Faker();
        String inputLine = Terminal.in.readLine();
        while (!inputLine.equals("stop")) {
            Terminal.out.println("> " + faker.yoda().quote());

            inputLine = Terminal.in.readLine();
        }

        Terminal.out.println("> Have a good day!");
    }

}
