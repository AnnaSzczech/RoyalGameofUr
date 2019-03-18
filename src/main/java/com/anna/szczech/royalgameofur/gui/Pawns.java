package com.anna.szczech.royalgameofur.gui;

import com.anna.szczech.royalgameofur.logic.PlayerRound;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class Pawns extends Label {
    private int location = 0;
    private Object playerClass;

    public Pawns(Node graphic, Board board, Object playerClass) {
        super("", graphic);
        this.playerClass = playerClass;
        this.setOnMouseClicked(event -> movePawn(board));
    }

    private void movePawn(Board board){
        if (!board.isEndGame  && board.getMove() != 0) {
            PlayerRound playerRound = new PlayerRound(board, this);
            playerRound.newRound();
        }
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public Object getPlayerClass() {
        return playerClass;
    }
}
