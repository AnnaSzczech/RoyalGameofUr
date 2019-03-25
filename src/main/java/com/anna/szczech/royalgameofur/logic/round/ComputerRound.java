package com.anna.szczech.royalgameofur.logic.round;

import com.anna.szczech.royalgameofur.gui.Pawns;
import java.util.List;
import java.util.stream.Collectors;

public class ComputerRound extends Round {

    public ComputerRound(Pawns pawn) {
        setGame(pawn.getGame());
        setPawn(selectThePawn());
        getPawn().getGame().resetRoll();
        getPawn().getGame().getRoll().diceRoll(getPawn().getGame().isGameEnded());
    }

    private Pawns selectThePawn() {
        List<Pawns> pawns = getComputerPawns().stream().filter(pawn -> pawn.getLocation() < 15).collect(Collectors.toList());
        Pawns pawn = null;
        pawn = lookForPerfectLocation(pawn, pawns, 8);
        pawn = lookForPerfectLocation(pawn, pawns, 14);
        pawn = lookForPerfectLocation(pawn, pawns, 4);
        pawn = lookForPerfectLocation(pawn, pawns, 15);
        if (pawn == null) {
            int rollNumber = (int) (Math.random() * pawns.size());
            pawn = pawns.get(rollNumber);
        }
        return pawn;
    }

    private boolean isTheLocationFree(int location, List<Pawns> userPawns, List<Pawns> computerPawns){
        boolean isTheLocationFree;
        isTheLocationFree = !isPawnOnSpecificLocation(userPawns, location);
        if (isTheLocationFree){
            isTheLocationFree = !isPawnOnSpecificLocation(computerPawns, location);
        }
        return isTheLocationFree;
    }

    private Pawns moveToBonusRollIfPossible(List<Pawns> pawns, int selectedLocation){
        Pawns pawn = null;
        if (isPawnOnSpecificLocation(pawns, selectedLocation)) {
           pawn =  pawns.stream().filter(p -> p.getLocation() + getGame().getRoll().getRolledNumber() == selectedLocation).findFirst().get();
        }
        return pawn;
    }

    private Pawns lookForPerfectLocation(Pawns pawn, List<Pawns> pawns, int location){
        if (pawn == null) {
            if (isTheLocationFree(location, getUserPawns(), getComputerPawns())) {
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
