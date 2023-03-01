package hsos;

import hsos.commands.BaseCommand;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Die Klasse CommandList verwaltet die ausführbaren Befehle. Sie ist als Singleton implementiert, damit Befehle nicht
 * mehrfach registriert werden.
 */
public class CommandList {

    /**
     * Interne Instanz der CommandList
     */
    private static CommandList instance = null;

    /**
     * HashTable zum Speichern der Command-Klassen
     */
    private final HashMap<String, Class<? extends BaseCommand>> commandList;

    /**
     * Privater Konstruktor
     */
    private CommandList() {
        commandList = new HashMap<>();
    }

    /**
     * Diese Methode leitet die eingehenden Befehle an die jeweils zuständigen Command-Klassen weiter, oder gibt einen
     * Fehler im Terminal aus, wenn der Befehl nicht bekannt ist.
     *
     * @param command Auszuführender Befehl.
     * @param args    Argumente für den Befehl.
     */
    public void execute(String command, String[] args) {
        try {
            if (commandList.containsKey(command)) {
                commandList.get(command).getConstructor().newInstance().execute(args);
            } else {
                HSOS.out.println();
                HSOS.out.println("Das Kommando '" + command + "' ist nicht bekannt.\n\nBitte geben Sie 'help' ein, um eine Liste der verfügbaren Kommandos anzuzeigen.");
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registriert einen neuen Befehl und macht ihn ausführbar. Der Befehl wird nicht hinzugefügt, wenn der übergebene
     * parameter command bereits registriert wurde.
     *
     * @param command      Name des Befehls.
     * @param commandClass Klasse des Befehls, die BaseCommand implementiert.
     */
    public void registerCommand(String command, Class<? extends BaseCommand> commandClass) {
        commandList.putIfAbsent(command.toLowerCase(), commandClass);
    }

    /**
     * Get a detailed description of a specified command.
     *
     * @param command name of command.
     * @return detailed description of command.
     */
    public String getCommandInformation(String command) {
        try {
            Class<? extends BaseCommand> c = commandList.get(command);
            if (c == null) return null;
            return c.getConstructor().newInstance().getDetailedDescription();
        } catch (InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException |
                 NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gibt eine List bestehend aus Pairs zurück, wobei Key der Name des Befehls und Value die Beschreibung des Befehls
     * ist.
     *
     * @return Liste mit Name und Beschreibung alle registrierter Befehle.
     */
    public List<Pair<String, String>> getCommandInformation() {
        List<Pair<String, String>> list = new ArrayList<>();
        commandList.forEach((command, commandClass) -> {
            try {
                Pair<String, String> info = new Pair<>(command, commandClass.getConstructor().newInstance().getDescription());
                list.add(info);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
        list.sort(Comparator.comparing(Pair::getKey));
        return list;
    }

    /**
     * Gibt Instanz von CommandList zurück. Falls noch keine Instanz besteht, wird sie neu erzeugt.
     *
     * @return Instanz von CommandList.
     */
    public static CommandList getInstance() {
        if (instance == null) {
            instance = new CommandList();
        }
        return instance;
    }
}
