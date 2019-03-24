package com.anna.szczech.royalgameofur.gui;

import com.anna.szczech.royalgameofur.logic.Game;
import com.anna.szczech.royalgameofur.player.PlayerEnum;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pawns extends Label {
    private int location = 0;
    private PlayerEnum playerEnum;

    public Pawns(Image image, PlayerEnum playerEnum, Game game) {
        super("", new ImageView(image));
        this.playerEnum = playerEnum;
        this.setOnMouseClicked(event -> game.movePawn(this));
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
