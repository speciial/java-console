package hsos.commands;

import hsos.HSOS;

public class ListUserCommand implements BaseCommand {

    @Override
    public void execute(String[] args) {
        HSOS.out.println();

        String activeUser = HSOS.userSystem.getActiveUser();
        HSOS.userSystem.listUsers().forEach(userName -> {
            String line;
            if (userName.equals(activeUser)) {
                line = String.format("%-10s %s", userName, "ACTIVE");
            } else {
                line = String.format("%-10s", userName);
            }
            HSOS.out.println(line);
        });
    }

    @Override
    public String getDescription() {
        return "Gibt eine Liste aller Nutzer aus.";
    }

    @Override
    public String getDetailedDescription() {
        // TODO(christian): write a detailed description.
        return "";
    }
}
