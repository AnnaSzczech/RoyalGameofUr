package com.anna.szczech.royalgameofur.player;

import com.anna.szczech.royalgameofur.gui.Pawn;
import java.util.List;

public class Player {
    private List<Pawn> pawns;
    private int points = 0;
    private boolean isPlayerTurn = false;

    public Player(List<Pawn> pawns) {
        this.pawns = pawns;
    }

    public List<Pawn> getPawns() {
        return pawns;
    }

    public int getPoints() {
        return points;
    }

    public void addPoint() {
        this.points++;
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public void changeTurn() {
        isPlayerTurn = !isPlayerTurn;
    }
}
