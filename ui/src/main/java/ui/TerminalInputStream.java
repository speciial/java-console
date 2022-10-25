package ui;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TerminalInputStream {

    // These should probably be made volatile since they are being accessed by different threads!
    private volatile StringBuilder lineBuffer = new StringBuilder();
    // The "main" thread should be waiting on input by default since there is nothing in the line buffer at the
    // start of the application.
    private volatile boolean isWaiting = true;

    ReentrantLock lock = new ReentrantLock();
    Condition newLineCondition = lock.newCondition();

    // Perhaps this function should be synchronized?
    // This should "unlock" the main thread
    public void writeLine(String line) {
        lock.lock();
        try {
            lineBuffer.append(line);
            isWaiting = false;
            newLineCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    // This is the place that tells the main thread to wait for user input. But it should not use Thread.sleep.
    // There should be a way of locking the thread until an event occurred.
    // This should only wait when called from the main thread! I need to check how that would work.
    // Once done, this should lock again
    public String readLine() {
        String line = null;

        lock.lock();
        try {
            while (isWaiting) {
                newLineCondition.await();
            }
            line = lineBuffer.toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            isWaiting = true;
        }

        lineBuffer = new StringBuilder();
        return line;
    }
}
