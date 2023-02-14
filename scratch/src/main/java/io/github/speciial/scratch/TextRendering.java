package io.github.speciial.scratch;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.nio.IntBuffer;

public class TextRendering extends Application {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    int[] backBuffer = new int[WIDTH * HEIGHT];
    int[] renderBuffer = new int[WIDTH * HEIGHT];

    public static int roundFloatToInt(float number) {
        return (int) (number + 0.5f);
    }

    private int convertFloatColorToIntColor(float r, float g, float b) {
        return ((255 << 24) |
                (roundFloatToInt(r * 255.0f) << 16) |
                (roundFloatToInt(g * 255.0f) << 8) |
                (roundFloatToInt(b * 255.0f)));
    }

    public void fillRectangle(float fMinX, float fMinY, float fMaxX, float fMaxY,
                              float r, float g, float b) {
        int minX = roundFloatToInt(fMinX);
        int minY = roundFloatToInt(fMinY);
        int maxX = roundFloatToInt(fMaxX);
        int maxY = roundFloatToInt(fMaxY);

        if (minX < 0) {
            minX = 0;
        }
        if (minY < 0) {
            minY = 0;
        }
        if (maxX > WIDTH) {
            maxX = WIDTH;
        }
        if (maxY > HEIGHT) {
            maxY = HEIGHT;
        }

        int color = convertFloatColorToIntColor(r, g, b);

        for (int y = minY; y < maxY; y++) {
            for (int x = minX; x < maxX; x++) {
                backBuffer[(y * WIDTH) + x] = color;
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        ImageView imageView = new ImageView();
        root.getChildren().add(imageView);

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        IntBuffer intBuffer = IntBuffer.wrap(renderBuffer);
        PixelFormat<IntBuffer> pixelFormat = PixelFormat.getIntArgbPreInstance();
        PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer<>(WIDTH, HEIGHT, intBuffer, pixelFormat);
        Image image = new WritableImage(pixelBuffer);
        imageView.setImage(image);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (int y = 0; y < HEIGHT; y++) {
                    for (int x = 0; x < WIDTH; x++) {
                        backBuffer[(y * WIDTH) + x] = convertFloatColorToIntColor(255, 0, 255);
                    }
                }

                fillRectangle(50, 50, 400, 400, 0.0f, 0.0f, 0.0f);

                System.arraycopy(backBuffer, 0, renderBuffer, 0, backBuffer.length);
                pixelBuffer.updateBuffer(b -> null);
            }
        };
        timer.start();


        primaryStage.setScene(scene);
        primaryStage.setTitle("Scratch - TextRendering");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
