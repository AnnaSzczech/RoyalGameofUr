package com.anna.szczech.royalgameofur.GUI;

import com.anna.szczech.royalgameofur.logic.Board;
import com.anna.szczech.royalgameofur.logic.Player;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class Pawns extends Label {
    private int location = 0;
    private Board board;

    public Pawns(Node graphic, Board board) {
        super("", graphic);
        this.board = board;
        this.setOnMouseClicked(event -> {
            movePawn();
        });
    }


    public void movePawn() {
        location += board.getMove();
        if (willPawnGetPoint()) {
            if (board.isUserTurn()) {
                board.getResult().addPointToUser();
            } else {
                board.getResult().addPointToComputer();
            }
            printResult();
            moveToSaveSpot();
        } else if (location < 15) {
            if (board.isUserTurn()) {
                changePawnLocalizationOnBoard(board.getUserPawns());
            } else {
                changePawnLocalizationOnBoard(board.getComputerPawns());
            }
        }
        changeTurn();
    }

    private void moveToSaveSpot(){
        this.setScaleX(0.5);
        this.setScaleY(0.5);
        if (board.isUserTurn()) {
            this.setLayoutX(640-(board.getResult().getUserPoints()-1)*30);
            this.setLayoutY(160);
        } else {
            this.setLayoutX(790+(board.getResult().getComputerPoints()-1)*30);
            this.setLayoutY(160);
        }
    }

    private void printResult(){
        board.getResultLabel().setText(String.valueOf(board.getResult()));
    }

    private boolean willPawnGetPoint(){
        return location == 15;
    }

    private void changeTurn(){
        board.setUserTurn(!board.isUserTurn());
    }

    private void changePawnLocalizationOnBoard(Player player){
        this.setLayoutX(player.getLocalizationOnBoard().entrySet().stream().filter(n -> n.getValue() == location).map(n -> n.getKey().getX()).findFirst().get());
        this.setLayoutY(player.getLocalizationOnBoard().entrySet().stream().filter(n -> n.getValue() == location).map(n -> n.getKey().getY()).findFirst().get());
    }

    public Pawns getPawn(){
        return this;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
}
