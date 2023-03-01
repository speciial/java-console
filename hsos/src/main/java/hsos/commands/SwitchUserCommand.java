package hsos.commands;

import hsos.HSOS;

public class SwitchUserCommand implements BaseCommand {
    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            String user = args[0];
            String password = "";
            if (args.length == 2) {
                password = args[1];
            }

            if (HSOS.userSystem.switchUser(user, password)) {
                HSOS.out.println();
                HSOS.out.println("Nutzer zu " + user + " gewechselt.");
            } else {
                HSOS.out.println();
                HSOS.out.println("Fehler beim Wechseln des Nutzers.");
            }
        } else {
            HSOS.out.println();
            HSOS.out.println("Ungültige Parameter für switchuser. Versuchen Sie folgendes:");
            HSOS.out.println("switchuser NAME [PASSWORD]");
        }
    }

    @Override
    public String getDescription() {
        return "Wechselt den derzeitig angemeldeten Nutzer.";
    }

    @Override
    public String getDetailedDescription() {
        // TODO(christian): write a detailed description.
        return "";
    }
}
