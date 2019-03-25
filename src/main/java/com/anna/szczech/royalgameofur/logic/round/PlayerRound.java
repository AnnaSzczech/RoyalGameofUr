package com.anna.szczech.royalgameofur.logic.round;

import com.anna.szczech.royalgameofur.gui.Pawns;

public class PlayerRound extends Round {

    public PlayerRound(Pawns pawn) {
        setGame(pawn.getGame());
        setPawn(pawn);
    }
    @Override
    public void specificMove(int oldLocation){
        if (!getGame().getUser().isPlayerTurn() && !getGame().isGameEnded()) {
            getGame().createNewComputerRound(new ComputerRound(getPawn()));
        }
    }
}
