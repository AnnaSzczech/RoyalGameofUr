package com.anna.szczech.royalgameofur.logic;

import com.anna.szczech.royalgameofur.gui.Board;
import com.anna.szczech.royalgameofur.gui.Pawns;

import java.util.List;
import java.util.stream.Collectors;

public class ComputerRound extends Round {
    public ComputerRound(Board board) {
        super(board);
        this.pawn = selectThePawn();
        resetRoll();
        board.roll();
    }

    private Pawns selectThePawn() {
        List<Pawns> pawns = getComputerPawns().stream().filter(pawn -> pawn.getLocation() < 15).collect(Collectors.toList());
        int rollNumber = (int) (Math.random() * pawns.size());
        return pawns.get(rollNumber);
    }

    @Override
    public void specificMove(int oldLocationOfPawn) {
        if (isBonusRoll(oldLocationOfPawn, pawn.getLocation())) {
            resetRoll();
            board.roll();
            if (isThereAnyPossibleMove()) {
                repeatMove();
            }else {
                System.out.println("no move 2");
                changeTurn();
                resetRoll();
            }
        } else if (repeatIfPawnDidntMakeMove(oldLocationOfPawn, pawn.getLocation())) {
            repeatMove();
        }
    }

    private boolean repeatIfPawnDidntMakeMove(int oldLocationOfPawn, int newLocationOfPawn) {
        return (oldLocationOfPawn == newLocationOfPawn);
    }

    private void repeatMove() {
        pawn = selectThePawn();
        this.newRound();
    }
}
