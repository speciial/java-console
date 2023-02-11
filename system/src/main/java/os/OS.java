package os;

import ui.ApplicationWindow;

public class OS implements Runnable {

    @Override
    public void run() {

    }

    public static void main(String[] args) {
        Thread uiThread = new Thread(new ApplicationWindow());
        uiThread.start();

        // Thread mainThread = new Thread(new OS());
        // mainThread.start();
    }
}
