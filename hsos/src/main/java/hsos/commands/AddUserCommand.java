package hsos.commands;

import hsos.HSOS;

public class AddUserCommand implements BaseCommand {
    @Override
    public void execute(String[] args) {
        HSOS.out.println();
        if (args.length == 1) {
            if (HSOS.userSystem.addUser(args[0], "")) {
                HSOS.out.println("Nutzer " + args[0] + " wurde hinzugefügt.");
            }
        } else if (args.length == 2) {
            if (HSOS.userSystem.addUser(args[0], args[1])) {
                HSOS.out.println("Nutzer " + args[0] + " wurde hinzugefügt.");
            }
        } else {
            HSOS.out.println("Ungültige Parameter für adduser. Versuchen Sie folgendes:");
            HSOS.out.println("adduser NAME [PASSWORD]");
        }
    }

    @Override
    public String getDescription() {
        return "Fügt einen neuen Nutzer dem System hinzu.";
    }

    @Override
    public String getDetailedDescription() {
        // TODO(christian): write a detailed description!
        return "";
    }
}
