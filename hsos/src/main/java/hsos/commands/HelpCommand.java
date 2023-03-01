package hsos.commands;

import hsos.CommandList;
import hsos.HSOS;

public class HelpCommand implements BaseCommand {

    @Override
    public void execute(String[] args) {
        HSOS.out.println();
        CommandList.getInstance().getCommandInformation().forEach(commandInfo -> {
            String line = String.format("%-10s %s", commandInfo.getKey(), commandInfo.getValue());
            HSOS.out.println(line);
        });
    }

    @Override
    public String getDescription() {
        return "Gibt eine Beschreibung aller Befehle aus.";
    }

    @Override
    public String getDetailedDescription() {
        // TODO(christian): write a detailed description.
        return "";
    }

}
