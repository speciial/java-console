package hsos.commands;

import hsos.HSOS;

public class MoreCommand implements BaseCommand {
    @Override
    public void execute(String[] args) {
        if (args.length == 1) {
            String fileContent = HSOS.fileSystem.getFileContent(args[0]);
            if (fileContent != null) {
                HSOS.out.println();
                HSOS.out.println(fileContent);
            }
        } else {
            HSOS.out.println();
            HSOS.out.println("Ungültige Parameter für more. Versuchen Sie folgendes:");
            HSOS.out.println("more FILENAME");
        }
    }

    @Override
    public String getDescription() {
        return "Gibt den Inhalt einer Datei aus.";
    }

    @Override
    public String getDetailedDescription() {
        // TODO(christian): write a detailed description.
        return "";
    }
}
