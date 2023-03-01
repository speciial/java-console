package hsos.commands;

import hsos.HSOS;
import hsos.ui.ApplicationController;
import hsos.ui.ApplicationEditorCallBack;
import hsos.ui.ApplicationMode;

public class HSEditCommand implements BaseCommand {

    @Override
    public void execute(String[] args) {
        ApplicationController.getInstance().setWindowMode(ApplicationMode.EDITOR);

        // TODO(christian): clear the file contents, or fill them in if there is an existing file being loaded.

        // TODO(christian): pass this in the args.
        String filename = "something.txt";

        ApplicationController.getInstance().setEditorFilename(filename);
        ApplicationController.getInstance().setEditorCallback(new ApplicationEditorCallBack() {
            @Override
            public void quit() {
                ApplicationController.getInstance().setEditorFilename(null);
                ApplicationController.getInstance().setEditorCallback(null);
                ApplicationController.getInstance().setWindowMode(ApplicationMode.TERMINAL);
            }

            @Override
            public void save(String filename, String fileContent) {
                HSOS.out.println("FILENAME: " + filename);
                HSOS.out.println("FILE CONTENT: " + fileContent);
            }
        });
    }

    @Override
    public String getDescription() {
        return "A simple text editor for HSOS";
    }

    @Override
    public String getDetailedDescription() {
        // TODO(christian): Write a details description.
        return "";
    }

}
