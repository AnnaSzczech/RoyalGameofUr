package com.anna.szczech.royalgameofur.logic.round;

import com.anna.szczech.royalgameofur.gui.Pawns;
import com.anna.szczech.royalgameofur.logic.Game;

public class PlayerRound extends Round {

    public PlayerRound(Pawns pawn, Game game) {
        super(game);
        setPawn(pawn);
    }

    @Override
    public void specificMove(int oldLocation){
        if (!getGame().getUser().isPlayerTurn() && !getGame().isGameEnded()) {
            getGame().createNewComputerRound(new ComputerRound(getGame()));
        }
    }
}
