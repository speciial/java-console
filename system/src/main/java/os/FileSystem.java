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

    /**
     * This method constructs an absolute path for the currentWorkingDirectory. It looks more complicated than it
     * actually is. The implementation is based on this post:
     *
     *      https://stackoverflow.com/questions/5931261/java-use-stringbuilder-to-insert-at-the-beginning
     *
     * The method constructs the directory path in reverse order and inverses the entire string at the end. This is done
     * to use the append functionality of the StringBuild class rather than inserting String at the beginning.
     *
     * @return Absolute path of the current working directory
     */
    public String getDirectoryString() {
        StringBuilder cwd = new StringBuilder();

        OSFile filePointer = currentWorkingDirectory;
        do {
            cwd.append(new StringBuilder(filePointer.name).reverse()).append("/");
            filePointer = filePointer.parent;
        } while (filePointer != null);
        cwd.append(":c");

        return cwd.reverse().toString();
    }

    /**
     * Changes the current working directory. This method currently only supports changing a single directory up or down
     * in the hierarchy. Absolute paths aren't supported either.
     *
     * @param directory Directory to change to
     */
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

    /**
     * Retrieves the names of all items in the current working directory.
     *
     * @return List of all directory or file names
     */
    public List<String> getDirectoryContent() {
        List<String> directoryContent = new LinkedList<>();
        currentWorkingDirectory.children.forEach(osFile -> directoryContent.add(osFile.name));
        return directoryContent;
    }

    /**
     * Finds all items in the current working directory that are possible matches for auto-completing a given string.
     *
     * @param toComplete String to complete
     * @return List of all possible matches
     */
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
