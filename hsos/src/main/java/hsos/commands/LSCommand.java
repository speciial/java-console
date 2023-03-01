package hsos.commands;

import hsos.HSOS;
import hsos.files.OSFile;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public class LSCommand implements BaseCommand {
    @Override
    public void execute(String[] args) {
        OSFile[] directoryContent = HSOS.fileSystem.getDirectoryContent();

        if (args.length > 0) {
            switch (args[0]) {
                case "-name", "-n" -> directoryContent = sortName(directoryContent);
                case "-date", "-d" -> directoryContent = sortDate(directoryContent);
                case "-size", "-s" -> directoryContent = sortSize(directoryContent);
                case "-user", "-u" -> directoryContent = sortUser(directoryContent);
                default -> {
                    HSOS.out.println();
                    HSOS.out.println("Parameter '" + args[0] + "' ist unbekannt.");
                }
            }
        }

        HSOS.out.println();
        StringBuilder builder = new StringBuilder();
        for (OSFile fileOrDirectory : directoryContent) {
            builder.append(getFormattedLine(fileOrDirectory)).append("\n");
        }
        HSOS.out.println(builder.toString());
    }

    public OSFile[] sortName(OSFile[] directoryContent) {
        // TODO: Sortieralgorithmus implementieren!
        return directoryContent;
    }

    public OSFile[] sortDate(OSFile[] directoryContent) {
        // TODO: Sortieralgorithmus implementieren!
        return directoryContent;
    }

    public OSFile[] sortSize(OSFile[] directoryContent) {
        // TODO: Sortieralgorithmus implementieren!
        return directoryContent;
    }

    public OSFile[] sortUser(OSFile[] directoryContent) {
        // TODO: Sortieralgorithmus implementieren!
        return directoryContent;
    }

    private String getFormattedLine(OSFile fileOrDirectory) {
        // created Formatting
        String date = fileOrDirectory.getCreated().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        // isFolder Formatting
        String dir = fileOrDirectory.isFolder() ? "<DIR>" : "";
        // size Formatting
        DecimalFormat df = new DecimalFormat("###.###");
        String size = "";
        if (fileOrDirectory.getSize() > -1) {
            size = df.format(fileOrDirectory.getSize());
        }

        return String.format("%s %-6s %-6s %-7s %s", date, dir, fileOrDirectory.getUser(), size, fileOrDirectory.getName());
    }

    @Override
    public String getDescription() {
        return "Listet alle im Verzeichnis enthaltenen Elemente.";
    }

    @Override
    public String getDetailedDescription() {
        // TODO(christian): write a detailed description.
        return "";
    }
}
