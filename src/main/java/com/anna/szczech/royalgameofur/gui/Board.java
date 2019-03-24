package com.anna.szczech.royalgameofur.gui;

import com.anna.szczech.royalgameofur.logic.Game;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Label;

import java.util.List;

public class Board {

    private Label resultLabel;
    public Button newRoundButton;
    private Pane pane;
    public Label rolledNumberLabel;
    public Label messageLabel;
    private FlowPane boxWithPlayerPawns;
    private FlowPane boxWithComputerPawns;

    public Board(Pane pane){
        this.pane = pane;
    }

    public void createBoard(Game game){
        game.getUser().changeTurn();
        resultLabel = createNewLabel(String.valueOf(resultForAllPlayers(game.getUser().getPoints(), game.getComputer().getPoints())), 500, 120, 40);
        createMessageLabel();
        createNewRoundButton(game);
        createRollButton(game);
        createBoxesWithPlayersPawns(game);
        pane.getChildren().add(getResultLabel());
    }

    public String resultForAllPlayers(int userPoints, int computerPoints){
        return "USER POINTS : COMPUTER POINTS\n                   "
                + userPoints + "  :  " + computerPoints;
    }

    private void createRollButton(Game game){
        Button rollButton = createNewButton("ROLL DICE", 650, 660);
        rolledNumberLabel = createNewLabel("", 790, 650, 35);
        rollButton.setOnAction(event -> {
            game.makeARoll(this);
        });
        pane.getChildren().add(rolledNumberLabel);
        pane.getChildren().add(rollButton);
    }

    private void createBoxesWithPlayersPawns(Game game){
        boxWithPlayerPawns = createBoxWithPawns(150, game.getUser().getPawns());
        boxWithComputerPawns = createBoxWithPawns(1350, game.getComputer().getPawns());
    }

    private FlowPane createBoxWithPawns(double x, List<Pawns> pawns){
        FlowPane boxWithPawns = new FlowPane(Orientation.VERTICAL);
        boxWithPawns.setLayoutX(x);
        boxWithPawns.setLayoutY(150);
        boxWithPawns.setMinHeight(616);
        addPawnsToBox(boxWithPawns, pawns);
        return boxWithPawns;
    }

    private void addPawnsToBox(FlowPane box, List<Pawns> pawns){
        pawns.stream().forEach((n) -> box.getChildren().add(n));
        pane.getChildren().add(box);
    }

    private void createMessageLabel(){
        messageLabel = createNewLabel("", 600, 720, 35);
        pane.getChildren().add(messageLabel);
    }

    private void createNewRoundButton(Game game){
        newRoundButton = createNewButton("NEW ROUND", 450.0, 660.0);
        newRoundButton.setVisible(false);
        newRoundButton.setOnAction(event -> game.startNewRound());
        pane.getChildren().add(newRoundButton);
    }

    private Button createNewButton(String name, double x, double y){
        Button button = new Button(name);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setScaleX(2);
        button.setScaleY(2);
        return button;
    }

    private Label createNewLabel(String name, double x, double y, int fontSize){
        Label label = new Label(name);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setFont(new Font(fontSize));
        label.setTextFill(Color.web("#FFF"));
        return label;
    }

    public Label getResultLabel() {
        return resultLabel;
    }

    public void printResult(int userPoints, int computePoints) {
        resultLabel.setText(resultForAllPlayers(userPoints, computePoints));
    }

    public void movePawnFromBoxToGameBoard(Pawns pawn, boolean isUserPawn) {
        FlowPane boxWithPawns;
        if (isUserPawn) {
            boxWithPawns = boxWithPlayerPawns;
        } else {
            boxWithPawns = boxWithComputerPawns;
        }
        boxWithPawns.getChildren().remove(pawn);
        pane.getChildren().add(pawn);
    }

    public void movePawnIfCaptured(Pawns capturePawn, boolean isUserPawn) {
        if (capturePawn != null) {
            movePawnFromGameBoardToBoxWithPawns(capturePawn, isUserPawn);
        }
    }

    private void movePawnFromGameBoardToBoxWithPawns(Pawns capturePawn, boolean isUserPawn) {
        capturePawn.setLocation(0);
        pane.getChildren().remove(capturePawn);
        if (isUserPawn) {
            boxWithPlayerPawns.getChildren().add(capturePawn);
        } else {
            boxWithComputerPawns.getChildren().add(capturePawn);
        }
    }

    public void moveFromTheGameBoard(Pawns pawn, int userPoints, int computerPoints, boolean isPlayerTurn){
        pawn.setScaleX(0.5);
        pawn.setScaleY(0.5);
        if (isPlayerTurn) {
            pawn.setLayoutX(640 - (userPoints - 1) * 30);
            pawn.setLayoutY(160);
        } else {
            pawn.setLayoutX(790 + (computerPoints - 1) * 30);
            pawn.setLayoutY(160);
        }
    }
}
