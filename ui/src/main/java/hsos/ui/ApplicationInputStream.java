package hsos.ui;

public abstract class ApplicationInputStream {

    public abstract void writeToBuffer(String line);

    public abstract String readLine();

}
