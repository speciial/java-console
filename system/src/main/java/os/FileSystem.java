package os;

import os.file.OSFile;

import java.util.LinkedList;
import java.util.List;

public class FileSystem {

    private OSFile currentWorkingDirectory;

    public FileSystem() {
        currentWorkingDirectory = new OSFile("home", null, true, null);

        currentWorkingDirectory.children.add(new OSFile("Docs", null, true, currentWorkingDirectory));
        currentWorkingDirectory.children.add(new OSFile("Imgs", null, true, currentWorkingDirectory));
        currentWorkingDirectory.children.add(new OSFile("Vids", null, true, currentWorkingDirectory));
        currentWorkingDirectory.children.add(new OSFile(".settings", "", false, currentWorkingDirectory));
    }

    public String getDirectoryString() {
        StringBuilder cwd = new StringBuilder();

        OSFile filePointer = currentWorkingDirectory;
        do {
            cwd.insert(0, "/" + filePointer.name);
        } while (filePointer.parent != null);
        String currentDrive = "c";
        cwd.insert(0, currentDrive + ":");

        return cwd.toString();
    }

    public void changeDirectory(String directory) {
        switch (directory) {
            case "." -> {}
            case ".." -> {
                if (currentWorkingDirectory.parent != null) {
                    currentWorkingDirectory = currentWorkingDirectory.parent;
                }
            }
            default -> currentWorkingDirectory.children.forEach(osFile -> {
                if (osFile.isDirectory && osFile.name.equals(directory)) {
                    currentWorkingDirectory = osFile;
                }
            });
        }
    }

    public List<String> getDirectoryContent() {
        List<String> directoryContent = new LinkedList<>();
        currentWorkingDirectory.children.forEach(osFile -> directoryContent.add(osFile.name));
        return directoryContent;
    }

}
