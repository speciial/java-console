package hsos.commands;

/**
 * BaseCommand ist das grundlegende Interface für alle Befehle. Es stellt eine Methode zum Ausführen und eine zum
 * Abfragen einer Beschreibung zur Verfügung.
 */
public interface BaseCommand {

    /**
     * Die Klasse TerminalController nutzt diese Methode zum Ausführen des Commands, der dieses Interface implementiert.
     *
     * @param args Argumente, die dem Befehl beigefügt wurden.
     */
    void execute(String[] args);

    /**
     * Die Klasse HelpCommand nutzt diese Methode, um eine kurze Beschreibung des Befehls darstellen zu können.
     *
     * @return Kurze Beschreibung des Befehls.
     */
    String getDescription();

    /**
     * The Class HelpCommand uses this method to get a detailed description of the command.
     *
     * @return detailed description of command.
     */
    String getDetailedDescription();

}
