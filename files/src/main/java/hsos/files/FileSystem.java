package hsos.files;

import com.google.gson.*;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Die Klasse FileSystem simuliert ein einfaches Dateisystem mit grundlegenden Funktionen zum Erstellen von
 * Verzeichnissen und Dateien. Intern wird das Dateisystem als Baum mit einer Wurzel (diese Konzepte lernen Sie im
 * zweiten Lernsprint kennen) verwaltet.
 */
public class FileSystem {

    /**
     * Wurzel des Dateisystems.
     */
    private FileNode root;

    /**
     * Zeiger zum derzeit ausgewählten Verzeichnis
     */
    private transient FileNode currentNode;

    /**
     * Derzeit ausgewähltes Verzeichnis als Pfad-String
     */
    private String currentNodePath;

    /**
     * Konstruktor zum Initialisieren des Dateisystems falls, es nicht aus der Datei geladen werden konnte.
     */
    public FileSystem() {
        root = new FileNode("H:", true, "OS");
        currentNode = root;
        currentNodePath = getCwd();
    }

    /**
     * Gibt das derzeitig ausgewählt Verzeichnis als Pfad-String wieder.
     *
     * @return Pfad zum derzeitig ausgewählt Verzeichnis.
     */
    public String getCwd() {
        StringBuilder cwd = new StringBuilder();
        cwd.insert(0, "/");
        cwd.insert(0, currentNode.getFileProperties().getName());

        FileNode searchNode = currentNode;
        while (searchNode.getParent() != null) {
            cwd.insert(0, "/");
            cwd.insert(0, searchNode.getParent().getFileProperties().getName());

            searchNode = searchNode.getParent();
        }

        return cwd.toString();
    }

    /**
     * Wechselt das derzeitige Verzeichnis. Wenn das Verzeichnis nicht gefunden werden konnte, wird das alte Verzeichnis
     * zurückgegeben.
     *
     * @param newDirectory Relativer Pfad zu Verzeichnis.
     * @return Absoluter Pfad zum neuen Verzeichnis.
     */
    public String changeDirectory(String newDirectory) {
        String result = null;
        FileNode newCurrent = findFileOrDirectory(newDirectory);
        if (newCurrent != null) {
            currentNode = newCurrent;
            currentNodePath = getCwd();

            result = currentNodePath;
        }
        return result;
    }

    /**
     * Legt ein neues Verzeichnis im derzeitigen Verzeichnis an. Die Methode gibt true zurück, wenn das Verzeichnis
     * angelegt werden konnte. Wenn das Verzeichnis ein Sonderzeichen enthält oder bereits vorhanden ist, wird false
     * zurückgegeben.
     *
     * @param username Name des erstellenden Benutzers
     * @param name     Name des neuen Verzeichnisses
     * @return true - wenn das Verzeichnis erstellt wurde.
     * false - wenn das Verzeichnis nicht erstellt werden konnte.
     */
    public boolean makeDirectory(String username, String name) {
        if (!name.contains("/")) {
            return currentNode.addChild(new FileNode(name, true, username));
        } else {
            return false;
        }
    }

    /**
     * Legt eine neue Datei im derzeitigen Verzeichnis an. Die Methode gibt true zurück, wenn die Datei angelegt werden
     * konnte. Wenn die Datei ein Sonderzeichen enthält oder bereits vorhanden ist, wird false zurückgegeben.
     *
     * @param username Name des erstellenden Benutzers
     * @param name     Name der Datei
     * @param content  Inhalt der Datei
     * @return true - wenn die Datei erstellt wurde; false - wenn die Datei nicht erstellt werden konnte.
     */
    public boolean makeFile(String username, String name, String content) {
        if (!name.contains("/")) {
            return currentNode.addChild(new FileNode(name, false, username, content));
        } else {
            return false;
        }
    }

    /**
     * Legt eine neue Datei im derzeitigen Verzeichnis an. Die Methode gibt true zurück, wenn die Datei angelegt werden
     * konnte. Wenn die Datei ein Sonderzeichen enthält oder bereits vorhanden ist, wird false zurückgegeben.
     *
     * @param name     Name der Datei
     * @param username Nutzer dem die Datei zugeordnet ist
     * @param content  Inhalt der Datei
     * @param created  Erstelldatum der Datei
     * @return true - wenn die Datei erstellt wurde; false - wenn die Datei nicht erstellt werden konnte.
     */
    public boolean makeFile(String name, String username, String content, LocalDateTime created) {
        if (!name.contains("/")) {
            FileNode node = new FileNode(name, false, username, content);
            node.getFileProperties().setCreated(created);

            return currentNode.addChild(node);
        } else {
            return false;
        }
    }

    /**
     * Löscht eine Datei oder einen Ordner. Die Methode gibt true zurück, wenn die Datei/der Ordner gelöscht werden
     * konnte. Wenn die Datei/der Ordner nicht existiert, wird false zurückgegeben.
     *
     * @param name Name der Datei
     * @return true - wenn die Datei/der Ordner gelöscht wurde.
     * false - wenn die Datei/der Ordner nicht existiert.
     */
    public boolean removeFileOrDirectory(String name) {
        if (!name.contains("/")) {
            return currentNode.removeChild(currentNode.getChild(name));
        } else {
            return false;
        }
    }

    /**
     * Gibt den Inhalt einer Datei zurück. Fall die Datei nicht gefunden werden kann, wird null zurückgegeben.
     *
     * @param fileName Name der Datei.
     * @return Inhalt der Datei.
     */
    public String showFileContent(String fileName) {
        String result = null;

        FileNode file = findFileOrDirectory(fileName);
        if (file != null) {
            result = file.getFileProperties().getContent();
        }

        return result;
    }

    /**
     * Gibt den Inhalt eines Verzeichnisses wieder. Wenn das Verzeichnis leer ist, wird ein Array mit size 0
     * zurückgegeben.
     *
     * @return Inhalt des Verzeichnisses.
     */
    public OSFile[] getDirectoryContent() {
        FileNode[] nodes = currentNode.getChildrenList();
        OSFile[] childrenList = new OSFile[nodes.length];
        for (int nodeIndex = 0; nodeIndex < nodes.length; nodeIndex++) {
            childrenList[nodeIndex] = nodes[nodeIndex].getFileProperties();
        }
        return childrenList;
    }

    /**
     * Sucht eine Datei oder Verzeichnis im Dateisystem. Die Methode gibt null zurück, wenn die Datei oder das
     * Verzeichnis nicht gefunden werden konnte.
     *
     * @param fileOrDirectory Name oder Pfad zu Datei oder Verzeichnis.
     * @return FileNode zu Datei oder Verzeichnis.
     */
    private FileNode findFileOrDirectory(String fileOrDirectory) {
        String[] pathTokens = fileOrDirectory.trim().split("/");

        FileNode searchNode = currentNode;
        int pathTokenIndex = 0;

        if (pathTokens[0].equals(root.getFileProperties().getName())) {
            searchNode = root;
            pathTokenIndex++;
        }

        for (; pathTokenIndex < pathTokens.length; pathTokenIndex++) {
            // If the previous step has set newCurrent to null newDirectory was invalid
            if (searchNode == null) {
                break;
            }

            if (!pathTokens[pathTokenIndex].equals(".")) {
                if (pathTokens[pathTokenIndex].equals("..")) {
                    searchNode = searchNode.getParent();
                } else {
                    searchNode = searchNode.getChild(pathTokens[pathTokenIndex]);
                }
            }
        }

        return searchNode;
    }

    /**
     * Utility-Funktion zum Laden eines Dateisystems aus einer JSON-Datei.
     *
     * @param filePath Pfad der Datei.
     * @return Dateisystem mit geladenen Inhalten.
     * @throws IOException falls der angegebene Dateipfad nicht gefunden werden konnte.
     */
    public static FileSystem loadFromFile(String filePath) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                        (jsonElement, type, jsonDeserializationContext) ->
                                ZonedDateTime.parse(jsonElement.getAsString()).toLocalDateTime())
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>)
                        (localDateTime, type, jsonSerializationContext) ->
                                new JsonPrimitive(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).toString()))
                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
                .create();

        Reader reader = Files.newBufferedReader(Path.of(filePath), StandardCharsets.UTF_8);
        FileSystem fileSystem = gson.fromJson(reader, FileSystem.class);
        fileSystem.fixParent();
        fileSystem.fixCurrentNode();

        return fileSystem;
    }

    /**
     * Utility Methode zum Wiederherstellen des Dateisystems nach dem Laden aus der JSON Datei.
     */
    private void fixParent() {
        Queue<FileNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            FileNode parent = queue.poll();
            if (parent.getFileProperties().isFolder()) {
                for (FileNode child : parent.getChildrenList()) {
                    child.setParent(parent);
                    queue.offer(child);
                }
            }
        }
    }

    /**
     * Utility Methode zum Wiederherstellen des Dateisystems nach dem Laden aus der JSON Datei.
     */
    private void fixCurrentNode() {
        changeDirectory(currentNodePath);
    }

}
