package com.anna.szczech.royalgameofur.logic;

import com.anna.szczech.royalgameofur.GUI.Pawns;
import com.anna.szczech.royalgameofur.result.Points;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Label;

public class Board {
    private Player computerPawns;
    private Player userPawns;
    private int move = 0;
    private boolean isUserTurn = true;
    private Points result = new Points();
    private Label resultLabel;
    public Pawns selectedPawn;
    public Pawns capturedPawn = null;
    public boolean isEndGame = false;

    public Board(){
        computerPawns = new Computer(this);
        userPawns = new User(this);
        resultLabel = printResult();
    }

    public Label printResult(){
        Label resultLabel = new Label(String.valueOf(result));
        resultLabel.setLayoutX(500);
        resultLabel.setLayoutY(120);
        resultLabel.setFont(new Font(40));
        resultLabel.setTextFill(Color.web("#FFF"));
        return resultLabel;
    }

    public void rollNumberOnDice(){move = (int) (Math.random()*4 + 1);}

    public int getMove() {
        return move;
    }

    public Player getComputerPawns() {
        return computerPawns;
    }

    public Player getUserPawns() {
        return userPawns;
    }

    public boolean isUserTurn() {
        return isUserTurn;
    }

    public void setUserTurn(boolean userTurn) {
        isUserTurn = userTurn;
    }

    public Label getResultLabel() {
        return resultLabel;
    }

    public Points getResult() {
        return result;
    }

}
