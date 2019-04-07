package com.anna.szczech.royalgameofur.logic.round;

import com.anna.szczech.royalgameofur.gui.Pawn;
import com.anna.szczech.royalgameofur.logic.Game;

import java.util.List;
import java.util.stream.Collectors;

public class ComputerRound extends Round {

    public ComputerRound(Game game) {
        setGame(game);
//        setPawn(selectThePawn());
        game.resetRoll();
        game.getRoll().diceRoll(game.isGameEnded());
        setPawn(selectThePawn());
    }

    private Pawn selectThePawn() {
        List<Pawn> pawns = getComputerPawns().stream().filter(pawn -> pawn.getLocation() < 15).collect(Collectors.toList());
        Pawn pawn = moveToBonusRoll(pawns);
        if (pawn != null) {
            return pawn;
        }
        pawn = captureThePawn(pawns);
        if (pawn != null) {
            return pawn;
        }
        int rollNumber = (int) (Math.random() * pawns.size());
        pawn = pawns.get(rollNumber);
        return pawn;
    }

    private Pawn captureThePawn(List<Pawn> pawns){
        Pawn pawn = null;
        List<Pawn> userPawns = getUserPawns().stream().filter(userPawn -> userPawn.getLocation() > 4 && userPawn.getLocation() < 12).collect(Collectors.toList());
        if (userPawns.size() > 0) {
            if (isPawnToCapture(pawns, userPawns)) {
                pawn = pawns.stream()
                        .filter(computerPawn -> isPawnOnSpecificLocation(userPawns, computerPawn.getLocation() + getGame().getRoll().getRolledNumber()))
                        .findFirst()
                        .get();
            }
        }
        return pawn;
    }

    private boolean isPawnToCapture(List<Pawn> pawns, List<Pawn> userPawns){
        return pawns.stream()
                .filter(computerPawn -> isPawnOnSpecificLocation(userPawns, computerPawn.getLocation() + getGame().getRoll().getRolledNumber()))
                .findFirst()
                .isPresent();
    }

    private Pawn moveToBonusRoll(List<Pawn> pawns) {
        Pawn pawn = null;
        pawn = lookForPerfectLocation(pawn, pawns, 8);
        pawn = lookForPerfectLocation(pawn, pawns, 14);
        pawn = lookForPerfectLocation(pawn, pawns, 4);
        pawn = lookForPerfectLocation(pawn, pawns, 15);
        return pawn;
    }

    private boolean isTheLocationFree(int location, List<Pawn> userPawns) {
        boolean isTheLocationFree;
        isTheLocationFree = !isPawnOnSpecificLocation(userPawns, location);
        if (isTheLocationFree) {
            isTheLocationFree = !isPawnOnSpecificLocation(getComputerPawns(), location);
        }
        return isTheLocationFree;
    }

    private Pawn moveToBonusRollIfPossible(List<Pawn> pawns, int selectedLocation) {
        Pawn pawn = null;
        if (isPawnOnSpecificLocation(pawns, selectedLocation-getGame().getRoll().getRolledNumber())) {
            pawn = pawns.stream().filter(p -> p.getLocation() + getGame().getRoll().getRolledNumber() == selectedLocation).findFirst().get();
        }
        return pawn;
    }

    private Pawn lookForPerfectLocation(Pawn pawn, List<Pawn> pawns, int location) {
        if (pawn == null) {
            List<Pawn> userPawns = getUserPawns().stream()
                    .filter(userPawn -> userPawn.getLocation() > 4 && userPawn.getLocation() < 12)
                    .collect(Collectors.toList());
            if (isTheLocationFree(location, userPawns)) {
                pawn = moveToBonusRollIfPossible(pawns, location);
            }
        }
        return pawn;
    }

    @Override
    public void specificMove(int oldLocationOfPawn) {
        if (isBonusRoll(oldLocationOfPawn, getPawn().getLocation())) {
            getGame().resetRoll();
            getGame().getRoll().diceRoll(getGame().isGameEnded());
            if (isThereAnyPossibleMove()) {
                repeatMove();
            } else {
                System.out.println("no move 2");
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
