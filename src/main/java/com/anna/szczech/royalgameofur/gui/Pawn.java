package com.anna.szczech.royalgameofur.gui;

import com.anna.szczech.royalgameofur.player.PlayerEnum;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pawn extends Label {
    private int location = 0;
    private PlayerEnum playerEnum;

    public Pawn(Image image, PlayerEnum playerEnum) {
        super("", new ImageView(image));
        this.playerEnum = playerEnum;
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
