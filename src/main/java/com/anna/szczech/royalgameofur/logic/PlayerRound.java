package com.anna.szczech.royalgameofur.logic;

import com.anna.szczech.royalgameofur.GUI.Board;
import com.anna.szczech.royalgameofur.GUI.Pawns;

public class PlayerRound extends Round {

    public PlayerRound(Board board, Pawns pawn) {
        super(board);
        this.pawn = pawn;
    }

    @Override
    public void specificMove(int oldLocation, int newLocation){
        if (!board.isUserTurn()) {
            ComputerRound computerRound = new ComputerRound(board);
            computerRound.newRound();
        }
    }

    @Override
    public void noMove(){
        sendMessage("No move to make, click Stand");
    }
}
