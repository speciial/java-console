package os.io;

import ui.ApplicationInputStream;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TerminalInputStream extends ApplicationInputStream {

    private volatile StringBuilder lineBuffer = new StringBuilder();
    private volatile boolean isWaiting = true;

    ReentrantLock lock = new ReentrantLock();
    Condition newLineCondition = lock.newCondition();

    @Override
    public void writeToBuffer(String line) {
        try {
            lock.lock();
            lineBuffer.append(line);

            isWaiting = false;
            newLineCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String readLine() {
        try {
            lock.lock();

            while (isWaiting) {
                newLineCondition.await();
            }
            String line = lineBuffer.toString();
            lineBuffer = new StringBuilder();

            return line;
        } catch (InterruptedException e) {
            System.out.println("An error occurred while reading from the terminal!");
            System.out.println("Error: " + e.getMessage());

            // TODO(christian): should we just throw an error here?
            return null;
        } finally {
            lock.unlock();
            isWaiting = true;
        }
    }

}
