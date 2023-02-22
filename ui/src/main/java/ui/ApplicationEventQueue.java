package ui;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ApplicationEventQueue {

    private final BlockingQueue<ApplicationEvent> eventQueue;

    public ApplicationEventQueue() {
        eventQueue = new ArrayBlockingQueue<>(1024);
    }

    public ApplicationEvent getNextEvent() throws InterruptedException {
        return eventQueue.take();
    }

    public void flushEventQueue() {
        eventQueue.clear();
    }

    /**
     * Allows the application window to extract the underlying BlockingQueue for access in the components.
     *
     * @return Underlying Blocking Queue;
     */
    protected BlockingQueue<ApplicationEvent> getInternalQueue() {
        return eventQueue;
    }

}
