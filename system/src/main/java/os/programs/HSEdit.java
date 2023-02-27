package os.programs;

import os.HSOS;
import ui.ApplicationEditorCallBack;
import ui.ApplicationMode;
import ui.ApplicationSettings;

public class HSEdit {

    public void execute(String[] args) {
        ApplicationSettings.getInstance().setWindowMode(ApplicationMode.EDITOR);

        // TODO(christian): pass this in the args.
        String filename = "something.txt";

        ApplicationSettings.getInstance().setEditorFilename(filename);
        ApplicationSettings.getInstance().setEditorCallback(new ApplicationEditorCallBack() {
            @Override
            public void quit() {
                ApplicationSettings.getInstance().setEditorFilename(null);
                ApplicationSettings.getInstance().setEditorCallback(null);
                ApplicationSettings.getInstance().setWindowMode(ApplicationMode.TERMINAL);
            }

            @Override
            public void save(String filename, String fileContent) {
                HSOS.out.println("FILENAME: " + filename);
                HSOS.out.println("FILE CONTENT: " + fileContent);
            }
        });
    }

}
