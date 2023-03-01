package hsos.commands;

import hsos.HSOS;
import hsos.ui.ApplicationController;

public class ExitCommand implements BaseCommand {

    @Override
    public void execute(String[] args) {
        HSOS.shutdown();
        ApplicationController.getInstance().closeApplication();
    }

    @Override
    public String getDescription() {
        return "Beendet das hsOS Terminal.";
    }

    @Override
    public String getDetailedDescription() {
        // TODO(christian): write a detailed description.
        return "";
    }

}
