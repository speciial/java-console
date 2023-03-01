package hsos.commands;

import hsos.HSOS;

public class MakeDirectoryCommand implements BaseCommand {
    @Override
    public void execute(String[] args) {
        if (args.length == 1) {
            String userName = HSOS.userSystem.getActiveUser();
            boolean mkdirResult = HSOS.fileSystem.makeDirectory(userName, args[0]);
            if (!mkdirResult) {
                HSOS.out.println();
                HSOS.out.println("The directory '" + args[0] + "' already exists.");
            }
        } else {
            HSOS.out.println();
            HSOS.out.println("Ungültige Parameter für mkdir. Versuchen Sie folgendes:");
            HSOS.out.println("mkdir DIRNAME");
        }
    }

    @Override
    public String getDescription() {
        return "Erzeugt ein neues Verzeichnis.";
    }

    @Override
    public String getDetailedDescription() {
        // TODO(christian): write a detailed description.
        return "";
    }
}
