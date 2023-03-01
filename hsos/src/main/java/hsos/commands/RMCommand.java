package hsos.commands;

import hsos.HSOS;

public class RMCommand implements BaseCommand {

    @Override
    public void execute(String[] args) {
        boolean rmResult = HSOS.fileSystem.removeFileOrDirectory(args[0]);
        HSOS.out.println();
        if (!rmResult) {
            HSOS.out.println("Datei/Ordner '" + args[0] + "' existiert nicht.");
        } else {
            HSOS.out.println("Datei/Ordner '" + args[0] + "' wurde gelöscht.");
        }
    }

    @Override
    public String getDescription() {
        return "Löscht eine Datei oder ein Verzeichnis.";
    }

    @Override
    public String getDetailedDescription() {
        // TODO(christian): write a detailed description.
        return null;
    }

}
