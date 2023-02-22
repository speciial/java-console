 package ui.components;

 import javafx.scene.control.TextArea;
 import javafx.scene.input.KeyEvent;
 import javafx.scene.text.Font;
 import ui.ApplicationEvent;
 import ui.ApplicationInputStream;

 import java.util.concurrent.BlockingQueue;

 public class TerminalComponent extends TextArea {

     private final BlockingQueue<ApplicationEvent> eventQueue;
     private final ApplicationInputStream inputStream;

     private int lastValidWritePos;

     private String inputLine = "";

     public TerminalComponent(BlockingQueue<ApplicationEvent> eventQueue,
                              ApplicationInputStream inputStream,
                              double width, double height, Font font) {
         this.inputStream = inputStream;
         this.eventQueue = eventQueue;
         lastValidWritePos = 0;

         setUpFx(width, height, font);
         setUpKeyListener();
     }

     public void appendToTextBuffer(String content) {
         int currentContentLength = getContent().length();

         getContent().insert(currentContentLength, content, true);

         lastValidWritePos = getContent().length();
         selectRange(lastValidWritePos, lastValidWritePos);
     }

     private void setUpFx(double width, double height, Font font) {
         setFont(font);

         setMaxWidth(width);
         setMaxHeight(height);

         setWrapText(true);
     }

     private void setUpKeyListener() {
         addEventHandler(KeyEvent.KEY_PRESSED, event -> {
             switch (event.getCode()) {
                 case ENTER -> {
                     inputLine = getContent().get(lastValidWritePos, getContent().length());
                     inputStream.writeToBuffer(inputLine);

                     eventQueue.add(ApplicationEvent.TERMINAL_KEY_ENTER);
                     event.consume();
                 }
                 case TAB -> {
                     inputLine = getContent().get(lastValidWritePos, getContent().length());

                     eventQueue.add(ApplicationEvent.TERMINAL_KEY_TAB);
                     event.consume();
                 }
                 case UP -> {
                     inputLine = getContent().get(lastValidWritePos, getContent().length());

                     eventQueue.add(ApplicationEvent.TERMINAL_KEY_UP);
                     event.consume();
                 }
                 case DOWN -> {
                     inputLine = getContent().get(lastValidWritePos, getContent().length());

                     eventQueue.add(ApplicationEvent.TERMINAL_KEY_DOWN);
                     event.consume();
                 }
             }
         });
    }

}
