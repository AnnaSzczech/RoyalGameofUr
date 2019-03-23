package com.anna.szczech.royalgameofur.gui;

import com.anna.szczech.royalgameofur.logic.PlayerRound;
import com.anna.szczech.royalgameofur.player.PlayerEnum;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pawns extends Label {
    private int location = 0;
    private Roll roll;
    private PlayerEnum playerEnum;

    public Pawns(Image image, Board board, PlayerEnum playerEnum, Roll roll) {
        super("", new ImageView(image));
        this.roll = roll;
        this.playerEnum = playerEnum;
        this.setOnMouseClicked(event -> movePawn(board));
    }

    private void movePawn(Board board){
        if (!board.isGameEnded  && roll.getRolledNumber() != 0) {
            PlayerRound playerRound = new PlayerRound(board, this, roll);
            playerRound.newRound();
        }
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }


    public PlayerEnum getPlayerEnum() {
        return playerEnum;
    }
}
