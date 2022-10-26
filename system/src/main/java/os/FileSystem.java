package os;

import os.file.OSFile;

import java.util.LinkedList;
import java.util.List;

public class FileSystem {

    private OSFile root;

    private OSFile currentWorkingDirectory;

    public FileSystem() {
        root = new OSFile("home", null, true, null);
        currentWorkingDirectory = root;

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

    public List<String> findAutoCompleteMatch(String toComplete) {
        // NOTE: this is not a full implementation and just meant for testing!
        List<String> result = new LinkedList<>();
        currentWorkingDirectory.children.forEach(item -> {
            if(item.name.startsWith(toComplete)) {
                result.add(item.name);
            }
        });
        return result;
    }

}
