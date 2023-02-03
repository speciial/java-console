package os;

import ui.TerminalApplication;


public class OS implements Runnable {

    @Override
    public void run() {

    }

    public static void main(String[] args) {
        Thread uiThread = new Thread(new TerminalApplication());
        uiThread.start();

        // Thread mainThread = new Thread(new OS());
        // mainThread.start();
    }

}
