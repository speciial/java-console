package hsos.files;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Die Klasse FileNode stellt die interne Verwaltung des Dateisystems dar. Sie ist ein Knoten im "Datei-Baum" und hat
 * genau einen Elternknoten und beliebig viele Kindknoten. Die Datei-Eigenschaften werden in einer separaten Klasse
 * verwaltet.
 */
public class FileNode {

    /**
     * Datei und Verzeichnis-Eigenschaften
     */
    private final OSFile fileProperties;

    /**
     * Elternknoten der Datei oder des Verzeichnisses
     */
    private transient FileNode parent = null;

    /**
     * Kindknoten der Datei oder des Verzeichnisses
     */
    private ArrayList<FileNode> children;

    /**
     * Konstruktor zum Erstellen eines Verzeichnisses oder leeren Datei.
     *
     * @param name     Name der Datei oder des Verzeichnisses.
     * @param isFolder Handelt es sich um Datei oder Verzeichnis?
     * @param user     Nutzername.
     */
    public FileNode(String name, boolean isFolder, String user) {
        this.fileProperties = new OSFile(name, isFolder, user);

        if (isFolder) {
            this.children = new ArrayList<>();
        }
    }

    /**
     * Konstruktor zum Anlegen einer Datei mit Inhalt.
     *
     * @param name     Name der Datei.
     * @param isFolder Ist immer false!
     * @param user     Nutzername.
     * @param content  Inhalt.
     */
    public FileNode(String name, boolean isFolder, String user, String content) {
        this.fileProperties = new OSFile(name, isFolder, user, content);

        if (isFolder) {
            this.children = new ArrayList<>();
        }
    }

    /**
     * Gibt die Datei- oder Verzeichnis-Eigenschaften wieder.
     *
     * @return Datei- oder Verzeichnis-Eigenschaften.
     */
    public OSFile getFileProperties() {
        return fileProperties;
    }

    /**
     * Gibt die Kindknoten des Verzeichnisses wieder. Falls es sich um eine Datei handelt oder das Verzeichnis leer ist,
     * wird ein Array mit size 0 zurückgegeben.
     *
     * @return Kindknoten des Verzeichnisses.
     */
    public FileNode[] getChildrenList() {
        FileNode[] result = new FileNode[children.size()];
        for (int nodeIndex = 0; nodeIndex < children.size(); nodeIndex++) {
            result[nodeIndex] = children.get(nodeIndex);
        }
        return result;
    }

    /**
     * Getter für Elternknoten.
     *
     * @return Elternknoten.
     */
    public FileNode getParent() {
        return parent;
    }

    /**
     * Setter für Elternknoten.
     *
     * @param parent Neuer Elternknoten.
     */
    public void setParent(FileNode parent) {
        this.parent = parent;
    }

    /**
     * Fügt dem Verzeichnis einen neuen Knoten hinzu.
     *
     * @param node Neuer Knoten
     * @return true - wenn der Knoten hinzugefügt werden konnte.
     * false - wenn der Knoten nicht hinzugefügt werden konnte.
     */
    public boolean addChild(FileNode node) {
        boolean result = false;
        // if (this.fileProperties.isFolder() && !children.contains(node)) {
        if (this.fileProperties.isFolder()) {
            node.parent = this;
            this.children.add(node);
            result = true;
        }
        return result;
    }

    /**
     * Löscht einen Kindknoten.
     *
     * @param node Zu löschender Knoten
     * @return true - wenn der Knoten gelöscht wurde
     * false - wenn der Knoten nicht gelöscht werden konnte
     */
    public boolean removeChild(FileNode node) {
        boolean result = false;
        if (children.contains(node)) {
            node.parent = this;
            this.children.remove(node);
            result = true;
        }
        return result;
    }

    /**
     * Gibt einen bestimmten Knoten mit Namen name wieder. Wenn der Knoten nicht gefunden werden konnte, wird null
     * zurückgegeben.
     *
     * @param name name des Knotens
     * @return Knoten
     */
    public FileNode getChild(String name) {
        FileNode result = null;
        for (FileNode node : children) {
            if (node.getFileProperties().getName().equals(name)) {
                result = node;
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileNode fileNode = (FileNode) o;
        return fileProperties.getName().equals(fileNode.getFileProperties().getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileProperties.getName());
    }
}
