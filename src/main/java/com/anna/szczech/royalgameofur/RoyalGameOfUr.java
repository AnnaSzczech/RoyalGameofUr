package com.anna.szczech.royalgameofur;

import com.anna.szczech.royalgameofur.GUI.Pawns;
import com.anna.szczech.royalgameofur.logic.Board;
import com.anna.szczech.royalgameofur.logic.User;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.IntStream;

public class RoyalGameOfUr extends Application {
    private Image imageBackground = new Image("file:src/main/resources/Background.jpg");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Board board = new Board();
        Pane pane = new Pane();

        createRollButton(board, pane);

        FlowPane boxWithPlayerPawns = createBoxWithPawns(150, board.getUserPawns().getPawns(), pane, board);
        FlowPane boxWithComputerPawns = createBoxWithPawns(1350, board.getComputerPawns().getPawns(), pane, board);

        pane.setOnMouseClicked(event -> checkIfPawnIsCaptured(board, pane, boxWithPlayerPawns, boxWithComputerPawns));
        pane.getChildren().add(board.getResultLabel());
        pane.setBackground(createBackground());
        Scene scene = new Scene(pane, 1600, 900, Color.BLACK);

        primaryStage.setTitle("Royal Game Of Ur");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void createRollButton(Board board, Pane pane){
        Button roll = new Button("ROLL DICE");
        roll.setLayoutX(650);
        roll.setLayoutY(730);
        roll.setScaleX(2);
        roll.setScaleY(2);
        Label rolledNumber = writeRolledNumberOnLabel();
        roll.setOnAction(event -> roll(board, rolledNumber));
        pane.getChildren().add(rolledNumber);
        pane.getChildren().add(roll);
    }

    private void roll(Board board, Label rolledNumber){
        board.rollNumberOnDice();
        rolledNumber.setText(String.valueOf(board.getMove()));
    }

    public Label writeRolledNumberOnLabel(){
        Label rolledNumber = new Label();
        rolledNumber.setLayoutX(790);
        rolledNumber.setLayoutY(720);
        rolledNumber.setFont(new Font(35));
        rolledNumber.setTextFill(Color.web("#FFF"));
        return rolledNumber;
    }

    private Background createBackground(){
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, false, false, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        return new Background(backgroundImage);
    }

    private FlowPane createBoxWithPawns(double x, List<Pawns> pawns, Pane  pane, Board board){
        FlowPane boxWithPawns = new FlowPane(Orientation.VERTICAL);
        boxWithPawns.setLayoutX(x);
        boxWithPawns.setLayoutY(150);
        boxWithPawns.setMinHeight(616);
        addPawnsToBox(boxWithPawns, pawns, pane);
        boxWithPawns.setOnMouseClicked(event -> {
            if (!board.isEndGame && board.getMove() != 0) {
                movePawnFromBoxToGameBoard(boxWithPawns, pawns, pane, board);
            }
        });
        return boxWithPawns;
    }

    private void movePawnFromBoxToGameBoard(FlowPane boxWithPawns, List<Pawns> pawns, Pane pane, Board board){
        Pawns selectedPawn = board.selectedPawn;
        board.selectedPawn = null;
        boxWithPawns.getChildren().remove(selectedPawn);
        int indexOfSelectedPawn = IntStream.range(0, pawns.size()).filter(n -> pawns.get(n).equals(selectedPawn)).findFirst().getAsInt();
        pane.getChildren().add(pawns.get(indexOfSelectedPawn));
    }

    private void addPawnsToBox(FlowPane box, List<Pawns> pawns, Pane pane){
        pawns.stream().forEach((n) -> box.getChildren().add(n));
        pane.getChildren().add(box);
    }

    private void checkIfPawnIsCaptured(Board board, Pane pane, FlowPane boxWithPlayerPawns, FlowPane boxWithComputerPawns){
        Pawns pawn = board.capturedPawn;
        board.capturedPawn = null;
        if (pawn != null) {
            movePawnFromGameBoardToBoxWithPawns(pawn, pane, boxWithPlayerPawns, boxWithComputerPawns);
        }
    }

    private void movePawnFromGameBoardToBoxWithPawns(Pawns pawn, Pane pane, FlowPane boxWithPlayerPawns, FlowPane boxWithComputerPawns){
        pawn.setLocation(0);
        pane.getChildren().remove(pawn);
        if (pawn.getPlayerClass() == User.class) {
            boxWithPlayerPawns.getChildren().add(pawn);
        } else {
            boxWithComputerPawns.getChildren().add(pawn);
        }
    }
}
