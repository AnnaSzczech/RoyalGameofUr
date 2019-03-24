package com.anna.szczech.royalgameofur.logic.round;

import com.anna.szczech.royalgameofur.gui.Pawns;
import com.anna.szczech.royalgameofur.logic.Game;

import java.util.List;
import java.util.stream.Collectors;

public class ComputerRound extends Round {
    public ComputerRound(Game game) {
        super(game);
        setPawn(selectThePawn());
        game.resetRoll();
        game.getRoll().diceRoll(game.isGameEnded());
    }

    private Pawns selectThePawn() {
        List<Pawns> pawns = getComputerPawns().stream().filter(pawn -> pawn.getLocation() < 15).collect(Collectors.toList());
        int rollNumber = (int) (Math.random() * pawns.size());
        return pawns.get(rollNumber);
    }

    @Override
    public void specificMove(int oldLocationOfPawn) {
        if (isBonusRoll(oldLocationOfPawn, getPawn().getLocation())) {
            getGame().resetRoll();
            getGame().getRoll().diceRoll(getGame().isGameEnded());
            if (isThereAnyPossibleMove()) {
                repeatMove();
            }else {
                getGame().changeTurn();
                getGame().resetRoll();
            }
        } else if (repeatIfPawnDidntMakeMove(oldLocationOfPawn, getPawn().getLocation())) {
            repeatMove();
        }
    }

    private boolean repeatIfPawnDidntMakeMove(int oldLocationOfPawn, int newLocationOfPawn) {
        return (oldLocationOfPawn == newLocationOfPawn);
    }

    private void repeatMove() {
        setPawn(selectThePawn());
        this.newRound();
    }
}
