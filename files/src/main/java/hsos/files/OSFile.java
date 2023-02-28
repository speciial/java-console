package hsos.files;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class OSFile {

    private String name;
    private boolean isFolder;
    private String user;
    private LocalDateTime created;

    private String content;
    private int size;

    public OSFile(String name, boolean isFolder, String user) {
        this.name = name;
        this.isFolder = isFolder;
        this.user = user;
        this.created = LocalDateTime.now();

        if (isFolder) {
            this.content = null;
            this.size = -1;
        } else {
            this.content = "";
            this.size = 0;
        }
    }

    public OSFile(String name, boolean isFolder, String user, String content) {
        this.name = name;
        this.isFolder = isFolder;
        this.user = user;
        this.created = LocalDateTime.now();

        this.content = content;
        this.size = content.getBytes(StandardCharsets.UTF_8).length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void setFolder(boolean folder) {
        isFolder = folder;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
