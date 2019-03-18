package com.anna.szczech.royalgameofur;

import com.anna.szczech.royalgameofur.gui.Board;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RoyalGameOfUr extends Application {
    private Image imageBackground = new Image("file:src/main/resources/Background.jpg");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane pane = new Pane();
        Board board = new Board(pane);

        board.run();

        pane.setBackground(createBackground());
        Scene scene = new Scene(pane, 1600, 900, Color.BLACK);

        primaryStage.setTitle("Royal Game Of Ur");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Background createBackground(){
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, false, false, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        return new Background(backgroundImage);
    }
}
