package hsos.commands;

import hsos.HSOS;

public class CDCommand implements BaseCommand {
    @Override
    public void execute(String[] args) {
        if (args.length == 1) {
            String displayDirectory = HSOS.fileSystem.changeDirectory(args[0]);
            if (displayDirectory == null) {
                HSOS.out.println();
                HSOS.out.println("'" + args[0] + "' is not a valid directory.");
            }
        } else {
            HSOS.out.println();
            HSOS.out.println("Ungültige Parameter für cd. Versuchen Sie folgendes:");
            HSOS.out.println("cd PFAD");
        }
    }

    @Override
    public String getDescription() {
        return "Wechselt das Verzeichnis.";
    }

    @Override
    public String getDetailedDescription() {
        // TODO(christian): write a detailed description.
        return "";
    }
}
