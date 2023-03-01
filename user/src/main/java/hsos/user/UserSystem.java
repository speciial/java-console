package hsos.user;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
     * Utility-Funktion zum Laden eines Benutzer-Systems aus einer JSON-Datei.
     *
     * @param filePath Pfad der Datei.
     * @return Dateisystem mit geladenen Inhalten.
     * @throws IOException falls der angegebene Dateipfad nicht gefunden werden konnte.
     */
    public static UserSystem loadFromFile(String filePath) throws IOException {
        Gson gson = getGson();
        Reader reader = Files.newBufferedReader(Path.of(filePath), StandardCharsets.UTF_8);
        UserSystem userSystem = gson.fromJson(reader, UserSystem.class);
        userSystem.switchUser("root", "root");

        return userSystem;
    }

    /**
     * Utility-Funktion zum Schreiben eines Benutzer-Systems in eine JSON-Datei.
     *
     * @param filePath   Pfad der Datei.
     * @param userSystem Zu speicherndes Benutzer-System
     * @throws IOException falls der angegebene Dateipfad nicht gefunden werden konnte.
     */
    public static void saveToFile(String filePath, UserSystem userSystem) throws IOException {
        Gson gson = getGson();
        FileWriter writer = new FileWriter(filePath, false);
        gson.toJson(userSystem, writer);
    }

    /**
     * Utility-Funktion zum Erstellen eines Gson-Objektes.
     *
     * @return Gson Objekt.
     */
    private static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                        (jsonElement, type, jsonDeserializationContext) ->
                                ZonedDateTime.parse(jsonElement.getAsString()).toLocalDateTime())
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>)
                        (localDateTime, type, jsonSerializationContext) ->
                                new JsonPrimitive(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).toString()))
                .enableComplexMapKeySerialization()
                .registerTypeAdapter(UserSystem.class, new UserSystemAdapter())
                .setPrettyPrinting()
                .create();
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

    /**
     * Utility-Klasse zum Serialisieren der Benutzerverwaltung.
     */
    private static class UserSystemAdapter extends TypeAdapter<UserSystem> {

        @Override
        public void write(JsonWriter jsonWriter, UserSystem userSystem) throws IOException {
            jsonWriter.beginObject();
            jsonWriter.name("users");
            jsonWriter.beginArray();
            for (OSUser user : userSystem.getOsUsers()) {
                jsonWriter.beginObject();
                jsonWriter.name("name");
                jsonWriter.value(user.getName());
                jsonWriter.name("hashedPassword");
                jsonWriter.value(user.getHashedPassword());
                jsonWriter.endObject();
            }
            jsonWriter.endArray();
            jsonWriter.endObject();
        }

        @Override
        public UserSystem read(JsonReader jsonReader) throws IOException {
            UserSystem system = new UserSystem();
            jsonReader.beginObject();
            jsonReader.nextName();
            jsonReader.beginArray();

            while (jsonReader.hasNext()) {
                jsonReader.beginObject();
                jsonReader.nextName();
                String name = jsonReader.nextString();
                jsonReader.nextName();
                String password = jsonReader.nextString();
                jsonReader.endObject();

                system.addOSUser(name, password);
            }
            jsonReader.endArray();
            jsonReader.endObject();

            return system;
        }
    }

}
