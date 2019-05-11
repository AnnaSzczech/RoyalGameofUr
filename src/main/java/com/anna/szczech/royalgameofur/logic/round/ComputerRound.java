package com.anna.szczech.royalgameofur.logic.round;

import com.anna.szczech.royalgameofur.gui.Pawn;
import com.anna.szczech.royalgameofur.logic.Game;

public class ComputerRound extends Round {

    public ComputerRound(Game game, Pawn pawn) {
        super(game, pawn);
    }

    public void makeBonusRoll(int oldLocationOfPawn) {
        if (isBonusRoll(oldLocationOfPawn, getPawn().getLocation())) {
            getGame().resetRoll();
            getGame().makeARoll(getGame().getBoard());
        }
    }
}
