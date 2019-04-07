package com.anna.szczech.royalgameofur.logic.round;

import com.anna.szczech.royalgameofur.gui.Pawn;
import com.anna.szczech.royalgameofur.logic.Game;

public class PlayerRound extends Round {

    public PlayerRound(Pawn pawn, Game game) {
        setGame(game);
        setPawn(pawn);
    }
    @Override
    public void specificMove(int oldLocation){
        if (!getGame().getUser().isPlayerTurn() && !getGame().isGameEnded()) {
            getGame().createNewComputerRound(new ComputerRound(getGame()));
        }
    }
}
