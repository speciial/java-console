package os.file;

import java.util.LinkedList;
import java.util.List;

public class OSFile {

    public String name;

    public String content;

    public boolean isDirectory;

    public OSFile parent;

    public List<OSFile> children;

    public OSFile(String name, String content, boolean isDirectory, OSFile parent) {
        this.name = name;
        this.content = content;
        this.isDirectory = isDirectory;

        if(isDirectory) {
            children = new LinkedList<>();
        }
    }

}
