package com.anna.szczech.royalgameofur.gui;

import com.anna.szczech.royalgameofur.logic.Game;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
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
    private PaneChildrenFactory paneChildrenFactory;

    public Board(Pane pane, PaneChildrenFactory paneChildrenFactory){
        this.pane = pane;
        this.paneChildrenFactory = paneChildrenFactory;
    }

    public void createBoard(Game game){
        game.getUser().changeTurn();
        resultLabel = paneChildrenFactory.createNewLabel(String.valueOf(resultForAllPlayers(game.getUser().getPoints(), game.getComputer().getPoints())), 500, 120, 40);
        addMessageLabel();
        addNewRoundButton(game);
        addRollButton(game);
        createBoxesWithPlayersPawns(game);
        addToPane(getResultLabel());
    }
    private void addToPane(Node node){
        pane.getChildren().add(node);
    }

    public String resultForAllPlayers(int userPoints, int computerPoints){
        return "USER POINTS : COMPUTER POINTS\n                   "
                + userPoints + "  :  " + computerPoints;
    }

    private void addRollButton(Game game){
        Button rollButton = paneChildrenFactory.createNewButton("ROLL DICE", 650, 660);
        rolledNumberLabel = paneChildrenFactory.createNewLabel("", 790, 650, 35);
        rollButton.setOnAction(event -> {
            game.makeARoll(this);
        });
        addToPane(rolledNumberLabel);
        addToPane(rollButton);
    }

    private void createBoxesWithPlayersPawns(Game game){
        boxWithPlayerPawns = paneChildrenFactory.createFlowPane(150, 150, 616);
        addPawnsToBox(boxWithPlayerPawns, game.getUser().getPawns());
        boxWithComputerPawns = paneChildrenFactory.createFlowPane(1350, 150, 616);
        addPawnsToBox(boxWithComputerPawns, game.getComputer().getPawns());
    }

    private void addPawnsToBox(FlowPane box, List<Pawn> pawns){
        pawns.stream().forEach((n) -> box.getChildren().add(n));
        addToPane(box);
    }

    private void addMessageLabel(){
        messageLabel = paneChildrenFactory.createNewLabel("", 600, 720, 35);
        addToPane(messageLabel);
    }

    private void addNewRoundButton(Game game){
        newRoundButton = paneChildrenFactory.createNewButton("NEW ROUND", 450.0, 660.0);
        newRoundButton.setVisible(false);
        newRoundButton.setOnAction(event -> game.startNewRound());
        addToPane(newRoundButton);
    }

    public Label getResultLabel() {
        return resultLabel;
    }

    public void printResult(int userPoints, int computePoints) {
        resultLabel.setText(resultForAllPlayers(userPoints, computePoints));
    }

    public void movePawnFromBoxToGameBoard(Pawn pawn, boolean isUserPawn) {
        FlowPane boxWithPawns;
        if (isUserPawn) {
            boxWithPawns = boxWithPlayerPawns;
        } else {
            boxWithPawns = boxWithComputerPawns;
        }
        boxWithPawns.getChildren().remove(pawn);
        addToPane(pawn);
    }

    public void movePawnIfCaptured(Pawn capturePawn, boolean isUserPawn) {
        if (capturePawn != null) {
            movePawnFromGameBoardToBoxWithPawns(capturePawn, isUserPawn);
        }
    }

    private void movePawnFromGameBoardToBoxWithPawns(Pawn capturePawn, boolean isUserPawn) {
        capturePawn.setLocation(0);
        pane.getChildren().remove(capturePawn);
        if (isUserPawn) {
            boxWithPlayerPawns.getChildren().add(capturePawn);
        } else {
            boxWithComputerPawns.getChildren().add(capturePawn);
        }
    }

    public void moveFromTheGameBoard(Pawn pawn, int userPoints, int computerPoints, boolean isPlayerTurn){
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
