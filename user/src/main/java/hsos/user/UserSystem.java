package hsos.user;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserSystem {

    /**
     * HashMap zum Verwalten der Nutzer
     */
    private static HashMap<String, OSUser> users;

    /**
     * Derzeitig angemeldeter Nutzer
     */
    private OSUser currentUser;

    /**
     * Konstruktor zum Erstellen eines initialen Nutzers
     */
    public UserSystem() {
        users = new HashMap<>();
        currentUser = new OSUser("root", hashString("root"));

        users.put("root", currentUser);
    }

    /**
     * Methode zum Hinzufügen eines neuen Nutzers. Das Passwort wird im Klartext übergeben und vor dem Einfügen mit
     * SHA-256 gehasht.
     *
     * @param userName      Name des neuen Nutzers
     * @param plainPassword Passwort im Klartext
     * @return true - wenn das Einfügen erfolgreich war; false - wenn das Einfügen nicht erfolgreich war
     */
    public boolean addUser(String userName, String plainPassword) {
        String hashedPassword = "";
        if (!plainPassword.isEmpty()) {
            hashedPassword = hashString(plainPassword);
        }

        if ((hashedPassword != null) && !users.containsKey(userName)) {
            users.put(userName, new OSUser(userName, hashedPassword));
            return true;
        }
        return false;
    }

    /**
     * Methode zum Wechseln des Nutzers. Das Passwort wird im Klartext übergeben.
     *
     * @param userName      Names des Nutzers
     * @param plainPassword Passwort im Klartext
     * @return true - wenn das Wechseln erfolgreich war; false - wenn das Wechseln nicht erfolgreich war
     */
    public boolean switchUser(String userName, String plainPassword) {
        String hashedPassword = "";
        if (!plainPassword.isEmpty()) {
            hashedPassword = hashString(plainPassword);
        }

        if ((hashedPassword != null) && users.containsKey(userName)) {
            OSUser found = users.get(userName);
            if (found.getHashedPassword().equals(hashedPassword)) {
                this.currentUser = found;
                return true;
            }
        }
        return false;
    }

    /**
     * Gibt eine Liste aller Nutzernamen wieder.
     *
     * @return Nutzerliste
     */
    public List<String> listUsers() {
        List<String> userList = new ArrayList<>();
        users.forEach((name, osUser) -> userList.add(name));
        userList.sort(String::compareTo);
        return userList;
    }

    /**
     * Gibt den Namen des derzeitig aktiven Nutzers wieder.
     *
     * @return Aktiver Nutzer
     */
    public String getActiveUser() {
        return currentUser.getName();
    }

    /**
     * Helper Methode für die Serialisierung
     *
     * @return Liste aller Nutzer Objekte
     */
    protected List<OSUser> getOsUsers() {
        List<OSUser> userList = new ArrayList<>();
        users.forEach((name, osUser) -> userList.add(osUser));
        return userList;
    }

    /**
     * Helper Methode für die Serialisierung
     */
    protected void addOSUser(String name, String hashedPassword) {
        users.put(name, new OSUser(name, hashedPassword));
    }

    /**
     * Führt eine SHA-256 Encryption auf dem übergebenen String durch und gibt eine Hex-String des gehashten Strings
     * wieder. Falls der übergebene String nicht gehasht werden kann, wird null zurückgegeben.
     *
     * @param data String auf dem die Encryption durchgeführt wird.
     * @return Hex-String der Daten
     */
    private static String hashString(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();

            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
