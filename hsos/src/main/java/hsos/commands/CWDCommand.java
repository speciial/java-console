package hsos.commands;

import hsos.HSOS;

public class CWDCommand implements BaseCommand {

    @Override
    public void execute(String[] args) {
        HSOS.out.println();
        HSOS.out.println(HSOS.fileSystem.getCwd());
    }

    @Override
    public String getDescription() {
        return "Gibt das derzeitige Verzeichnis aus.";
    }

    @Override
    public String getDetailedDescription() {
        // TODO(christian): write a detailed description.
        return "";
    }
}
