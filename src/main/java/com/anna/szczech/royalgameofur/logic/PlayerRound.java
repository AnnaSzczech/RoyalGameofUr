package com.anna.szczech.royalgameofur.logic;

import com.anna.szczech.royalgameofur.gui.Board;
import com.anna.szczech.royalgameofur.gui.Pawns;
import com.anna.szczech.royalgameofur.gui.Roll;

public class PlayerRound extends Round {

    public PlayerRound(Board board, Pawns pawn, Roll roll) {
        super(board, roll);
        this.pawn = pawn;
    }

    @Override
    public void specificMove(int oldLocation){
        if (!board.isUserTurn() && !board.isGameEnded) {
            ComputerRound computerRound = new ComputerRound(board, roll);
            if (computerRound.isThereAnyPossibleMove()) {
                computerRound.newRound();
            } else {
                changeTurn();
                resetRoll();
            }
        }
    }
}
