package hsos;

public class CommandHistory {

    private final int capacity;
    private final String[] buffer;

    private int writePos;
    private int readPos;

    public CommandHistory(int capacity) {
        this.writePos = 0;
        this.readPos = -1;
        this.capacity = capacity;
        this.buffer = new String[capacity];
    }

    public void commitCommand(String command) {
        int arrayPos = writePos % capacity;
        buffer[arrayPos] = command;
        writePos++;
        readPos = writePos - 1;
    }

    public String getNext() {
        if (readPos < writePos - 1 && readPos >= 0) {
            readPos++;
            int arrayPos = readPos % capacity;
            return buffer[arrayPos];
        }
        return "";
    }

    public String getPrev() {
        if (readPos >= 0) {
            int arrayPos = readPos % capacity;
            readPos--;
            if (readPos < 0) {
                readPos = 0;
            }
            return buffer[arrayPos];
        }
        return "";
    }

}
