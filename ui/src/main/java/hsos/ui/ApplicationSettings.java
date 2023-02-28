package hsos.ui;

import hsos.ui.components.EditorComponent;
import hsos.ui.components.TerminalComponent;
import hsos.ui.components.WindowComponent;
import javafx.application.Platform;

public class ApplicationSettings {

    /*
        Settings List:
            Window
            [ ] Font
            [ ] Color Scheme
            [x] Component switch (terminal, editor)
            Terminal
            [ ] Edit active line
            Editor
            [x] Get the content of the file
     */

    private static ApplicationSettings instance = null;

    private WindowComponent windowComponent;
    private TerminalComponent terminalComponent;
    private EditorComponent editorComponent;

    private ApplicationMode activeMode;

    private ApplicationSettings() {
        activeMode = ApplicationMode.TERMINAL;
    }

    public void setWindowMode(ApplicationMode mode) {
        Platform.runLater(() -> {
            switch (mode) {
                case TERMINAL -> {
                    if (activeMode != ApplicationMode.TERMINAL) {
                        activeMode = ApplicationMode.TERMINAL;
                        windowComponent.setTerminalActive();
                    }
                }
                case EDITOR -> {
                    if (activeMode != ApplicationMode.EDITOR) {
                        activeMode = ApplicationMode.EDITOR;
                        windowComponent.setEditorActive();
                    }
                }
            }
        });
    }

    public ApplicationMode getWindowMode() {
        return activeMode;
    }

    public void setEditorFilename(String filename) {
        editorComponent.setFilename(filename);
    }

    public void setEditorCallback(ApplicationEditorCallBack callback) {
        editorComponent.setCallBack(callback);
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