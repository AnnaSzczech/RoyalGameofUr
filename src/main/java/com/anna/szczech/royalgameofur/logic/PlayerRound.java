package com.anna.szczech.royalgameofur.logic;

import com.anna.szczech.royalgameofur.GUI.Board;
import com.anna.szczech.royalgameofur.GUI.Pawns;

public class PlayerRound extends Round {

    public PlayerRound(Board board, Pawns pawn) {
        super(board);
        this.pawn = pawn;
    }

    @Override
    public void specificMove(int oldLocation){
        if (!board.isUserTurn() && !board.isEndGame) {
            ComputerRound computerRound = new ComputerRound(board);
            if (computerRound.isThereAnyPossibleMove()) {
                computerRound.newRound();
            } else {
                System.out.println("no move , 1");
                changeTurn();
                resetRoll();
            }
        }
    }
}
