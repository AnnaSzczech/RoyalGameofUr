package com.anna.szczech.royalgameofur.logic;

import com.anna.szczech.royalgameofur.GUI.Board;
import com.anna.szczech.royalgameofur.GUI.Pawns;

public class ComputerRound extends Round {
    public ComputerRound(Board board) {
        super(board);
        this.pawn = selectThePawn();
        resetRoll();
        board.roll();
    }

    private Pawns selectThePawn() {
        int rollNumber = (int) (Math.random() * 7);
        return getComputerPawns().get(rollNumber);
    }

    @Override
    public void specificMove(int oldLocationOfPawn, int newLocationOfPawn) {
        if (repeatIfPawnDidntMakeMove(oldLocationOfPawn, pawn.getLocation()) || isBonusRoll()) {
            repeatMove();
        }
    }

    private boolean repeatIfPawnDidntMakeMove(int oldLocationOfPawn, int newLocationOfPawn) {
        return (oldLocationOfPawn == newLocationOfPawn);
    }

    private void repeatMove() {
        if (isBonusRoll()) {
            resetRoll();
            board.roll();
        }
        pawn = selectThePawn();
        this.newRound();
    }

    @Override
    public void noMove(){
        System.out.println("no move");
        changeTurn();
    }
}
