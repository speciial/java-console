package os;

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

    }

}
