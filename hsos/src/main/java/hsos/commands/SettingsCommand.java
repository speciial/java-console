package hsos.commands;

import hsos.HSOS;
import hsos.settings.Setting;

public class SettingsCommand implements BaseCommand {

    @Override
    public void execute(String[] args) {
        HSOS.out.println();
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "add" -> {
                    if (args.length == 3) {
                        addSetting(args[1], args[2]);
                    } else {
                        HSOS.out.println("Ungültige Parameter für 'settings add'. Versuchen Sie folgendes:");
                        HSOS.out.println("setting add [NAME] [VALUE]");
                    }
                }
                case "set" -> {
                    if (args.length == 3) {
                        setSetting(args[1], args[2]);
                    } else {
                        HSOS.out.println("Ungültige Parameter für 'settings set'. Versuchen Sie folgendes:");
                        HSOS.out.println("setting set [NAME] [VALUE]");
                    }
                }
                case "get" -> {
                    if (args.length == 2) {
                        getSetting(args[1]);
                    } else {
                        HSOS.out.println("Ungültige Parameter für 'settings get'. Versuchen Sie folgendes:");
                        HSOS.out.println("setting get [NAME]");
                    }
                }
                case "remove" -> {
                    if (args.length == 2) {
                        removeSetting(args[1]);
                    } else {
                        HSOS.out.println("Ungültige Parameter für 'settings remove'. Versuchen Sie folgendes:");
                        HSOS.out.println("setting remove [NAME]");
                    }
                }
                case "list" -> listSettings();
            }
        }
    }

    private void addSetting(String name, String value) {
        if (!HSOS.settingsSystem.addSetting(name, value)) {
            HSOS.out.println("Die Einstellung konnte nicht hinzugefügt werden. Existiert der Name bereits?");
        }
    }

    private void setSetting(String name, String value) {
        if (!HSOS.settingsSystem.setSetting(name, value)) {
            HSOS.out.println("Die Einstellung konnte nicht aktualisiert werden. Existiert die Einstellung?");
        }
    }

    private void getSetting(String name) {
        Setting setting = HSOS.settingsSystem.getSetting(name);
        if (setting != null) {
            HSOS.out.println(setting.toString());
        } else {
            HSOS.out.println("Setting '" + name + "' konnte nicht gefunden werden.");
        }
    }

    private void removeSetting(String name) {
        if (!HSOS.settingsSystem.removeSetting(name)) {
            HSOS.out.println("Die Einstellung konnte nicht gelöscht werden. Existiert die Einstellung?");
        }
    }

    private void listSettings() {
        Setting[] settings = HSOS.settingsSystem.getAllSettings();
        for (Setting setting : settings) {
            HSOS.out.println(setting.toString());
        }
    }

    @Override
    public String getDescription() {
        return "Verwaltet die Systemeinstellungen.";
    }

    @Override
    public String getDetailedDescription() {
        // TODO(christian): write a detailed description.
        return "";
    }
}
