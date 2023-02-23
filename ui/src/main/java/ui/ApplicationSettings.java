package ui;

import javafx.scene.text.Font;
import ui.components.EditorComponent;
import ui.components.TerminalComponent;
import ui.components.WindowComponent;

public class ApplicationSettings {

    /*
        Settings List:
            Window
            [ ] Font
            [ ] Color Scheme
            [ ] Component switch (terminal, editor)
            Terminal
            [ ] Edit active line
            Editor
            [ ] Get the content of the file
     */

    private static ApplicationSettings instance = null;

    private WindowComponent windowComponent;
    private TerminalComponent terminalComponent;
    private EditorComponent editorComponent;

    private ApplicationSettings() {
    }

    public void setWindowFont(String fontName, int size) {
        windowComponent.setFont(new Font(fontName, size));
    }

    public void setWindowColorScheme(String schemeName) {

    }

    public void setWindowMode(String modeName) {

    }

    protected void setComponents(WindowComponent windowComponent) {
        this.windowComponent = windowComponent;
        this.terminalComponent = windowComponent.getTerminalComponent();
        this.editorComponent = windowComponent.getEditorComponent();
    }

    public static ApplicationSettings getInstance() {
        if (instance == null) {
            instance = new ApplicationSettings();
        }
        return instance;
    }

}
