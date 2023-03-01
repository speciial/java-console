package hsos.settings;

/**
 * Das SettingsSystem soll in der Zukunft alle wichtigen Einstellungen für das Betriebssystem verwalten. Dabei ist es
 * unter anderem wichtig, dass die Einstellungen direkt mit einem eindeutigen Namen adressiert werden können. Außerdem
 * ist darauf zu achten, dass die Zugriffe möglichst effizient gestaltet sind, damit es beim Anwenden der Einstellungen
 * nicht zu Wartezeiten für den Nutzer kommt.
 */
public class SettingsSystem {

    //=====================================================================================
    //
    //  Das Ziel des 2. Sprints soll sein, eine Einstellungsverwaltung zu implementieren.
    //  Das dafür benötigte Klassen-Skelett ist hier vorgegeben. Es soll besonderer Wert
    //  auf die Zugriffszeiten bei der gewählten Datenstruktur gelegt werden. Die
    //  Methoden addSetting, getSetting, setSetting und removeSetting sollen dabei im
    //  Durchschnitt mit einer Laufzeit von log(N) ablaufen.
    //
    //  Weitere Vorgaben hinsichtlich der Implementationsdetails entnehmen Sie den
    //  Beschreibungen der Doc-Kommentare über den jeweiligen Methoden. Die Test-Klasse
    //  SettingSystemTest überprüft auch das Verhalten bei fehlerhaften Eingaben.
    //
    //  Es steht Ihnen frei die bestehende Klasse 'Setting' im Package 'algo.model.setting'
    //  um Variablen und Methoden zu erweitern oder neue Klassen im angegebenen Package
    //  zu erstellen.
    //
    //=====================================================================================

    /**
     * Fügt eine neue Einstellungsoption dem System hinzu. Es soll ein Objekt vom Typ 'Setting' aus dem Package
     * 'algo.model.setting' erzeugt werden und unter entsprechender Laufzeitanforderung in die Einstellungsverwaltung
     * eingefügt werden.
     * <p>
     * Der Name einer Einstellung darf nur einmal vergeben werden. Sollte ein Name bereits vergeben sein, soll die
     * Methode 'false' zurückgeben.
     *
     * @param name  Name der Einstellung.
     * @param value Wert der zugehörigen Einstellung.
     * @return true - falls das Einfügen erfolgreich war.
     * false - falls das Einfügen nicht erfolgreich war.
     */
    public boolean addSetting(String name, String value) {
        // TODO: Implementieren der Funktionalität für das Hinzufügen von Einstellungen.
        return false;
    }

    /**
     * Aktualisiert eine existierende Einstellung mit einem neuen Wert. Der Name der Einstellung soll im System gesucht
     * und der zugehörige Wert der hinterlegten 'Setting'-Instanz angepasst werden. Auch bei dieser Methode gilt die
     * aus der Aufgabenstellung bekannte Laufzeitanforderung.
     * <p>
     * Sollte der übergebene Name der Einstellung im System nicht gefunden werden, so werden keine Werte aktualisiert
     * und 'false' zurückgegeben.
     *
     * @param name  Name der Einstellung.
     * @param value Neuer Wert der Einstellung.
     * @return true - falls das Aktualisieren erfolgreich war.
     * false - falls das Aktualisieren nicht erfolgreich war.
     */
    public boolean setSetting(String name, String value) {
        // TODO: Implementieren der Funktionalität für das Aktualisieren einer Einstellung.
        return false;
    }

    /**
     * Gibt eine bestehende Einstellung mit gegebenem Namen aus dem System zurück. Das System soll anhand des
     * übergebenen Namens durchsucht werden und anschließend die zugehörige Einstellung zurückgeben. Auch bei dieser
     * Methode gilt die aus der Aufgabenstellung bekannte Laufzeitanforderung.
     * <p>
     * Sollte keine Einstellung mit gegebenem Namen gefunden werden, soll die Methode 'null' zurückgeben.
     *
     * @param name Name der zu suchenden Einstellung.
     * @return Instanz der gefundenen Einstellung oder 'null', wenn keine Einstellung gefunden wurde.
     */
    public Setting getSetting(String name) {
        // TODO: Implementieren der Funktionalität zum Abrufen einzelner Einstellungen.
        return null;
    }

    /**
     * Löschte eine bestehende Einstellung aus dem System. Der Name der Einstellung soll im System sucht und zugehörige
     * Einstellung ('Setting'-Instanz) gelöscht werden. Auch bei dieser Methode gilt die aus der Aufgabenstellung
     * bekannte Laufzeitanforderung.
     * <p>
     * Sollte die Einstellung nicht im System existieren, soll die Methode 'false' zurückgeben.
     *
     * @param name Name der zu löschenden Einstellung.
     * @return true - falls das Löschen erfolgreich war.
     * false - falls das Löschen nicht erfolgreich war.
     */
    public boolean removeSetting(String name) {
        // TODO: Implementieren der Funktionalität zum Löschen einzelner Einstellungen.
        return false;
    }

    /**
     * Gibt eine sortierte Liste mit allen im System befindlichen Einstellungen zurück. Die Einstellungen sollen in Form
     * eines standard Java-Arrays zurückgegeben werden.
     *
     * @return Sortiertes Array mit allen existierenden Einstellungen.
     */
    public Setting[] getAllSettings() {
        // TODO: Implementieren der Funktionalität zum Abrufen aller Einstellungen.
        return null;
    }

    /**
     * TODO(christian): Implement this.
     *
     * @param filePath
     * @return
     */
    public static SettingsSystem loadFromFile(String filePath) {
        return null;
    }

}
