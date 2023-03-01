package hsos.commands;


public class MakeFileCommand implements BaseCommand {

    @Override
    public void execute(String[] args) {
        // TODO(christian): move file creation to hsedit!
    }

    @Override
    public String getDescription() {
        return "Erzeugt eine neue Datei.";
    }

    @Override
    public String getDetailedDescription() {
        return "";
    }

}
