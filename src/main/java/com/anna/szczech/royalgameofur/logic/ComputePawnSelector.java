package com.anna.szczech.royalgameofur.logic;

import com.anna.szczech.royalgameofur.gui.Pawn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComputePawnSelector {

    private List<Pawn> computerPawns;
    private List<Pawn> userPawns;
    private Game game;
    private PosibleMoveChecker posibleMoveChecker;

    public ComputePawnSelector(List<Pawn> computerPawns, List<Pawn> userPawns, Game game) {
        this.computerPawns = computerPawns;
        this.userPawns = userPawns;
        this.game = game;
        posibleMoveChecker = new PosibleMoveChecker(game.getComputer().getPawns(), game.getUser().getPawns(), game.getRoll().getRolledNumber(), game.isUserTurn());
    }

    public Pawn selectThePawn() {
        Pawn pawn = moveToBonusRoll();
        if (pawn != null) {
            return pawn;
        }
        pawn = captureThePawn();
        if (pawn != null) {
            return pawn;
        }
        return rollPawn(computerPawns);
    }

    private Pawn rollPawn(List<Pawn> pawns){
        Pawn pawn;
        do {
            int rollNumber = (int) (Math.random() * pawns.size());
            pawn = pawns.get(rollNumber);
        }  while (!posibleMoveChecker.canSelectedPawnChangeLocation(pawn.getLocation()+game.getRoll().getRolledNumber(), pawn));
        return pawn;
    }

    private Pawn captureThePawn(){
        Pawn pawn = null;
        userPawns = userPawns.stream()
                .filter(userPawn -> userPawn.getLocation() != 8)
                .collect(Collectors.toList());
        if (!userPawns.isEmpty()) {
            if (isPawnToCapture()) {
                pawn = computerPawns.stream()
                        .filter(computerPawn -> posibleMoveChecker.isPawnOnSpecificLocation(userPawns, computerPawn.getLocation() + game.getRoll().getRolledNumber()))
                        .findFirst()
                        .get();
            }
        }
        return pawn;
    }

    private boolean isPawnToCapture(){
        return computerPawns.stream()
                .anyMatch(computerPawn -> posibleMoveChecker.isPawnOnSpecificLocation(userPawns, computerPawn.getLocation() + game.getRoll().getRolledNumber()));
    }

    private Pawn moveToBonusRoll() {
        Pawn pawn = null;
        List<Integer> locations = new ArrayList<>();
        locations.add(8);
        locations.add(14);
        locations.add(4);
        locations.add(15);
        for (int location : locations){
            pawn = lookForPerfectLocation(pawn, location);
            if (pawn != null) {
                return pawn;
            }
        }
        return pawn;
    }

    private boolean isTheLocationFree(int location) {
        return !posibleMoveChecker.isPawnOnSpecificLocation(computerPawns, location);
    }

    private Pawn moveToBonusRollIfPossible(List<Pawn> pawns, int selectedLocation) {
        Pawn pawn = null;
        if (posibleMoveChecker.isPawnOnSpecificLocation(pawns, selectedLocation-game.getRoll().getRolledNumber())) {
            pawn = pawns.stream().filter(p -> p.getLocation() + game.getRoll().getRolledNumber() == selectedLocation).findFirst().get();
        }
        return pawn;
    }

    private Pawn lookForPerfectLocation(Pawn pawn, int location) {
        if (pawn == null) {
            boolean isUserOnSafeSpot = userPawns.stream()
                    .anyMatch(userPawn -> location == 8 && userPawn.getLocation() == 8);
            if (!isUserOnSafeSpot && isTheLocationFree(location)) {
                pawn = moveToBonusRollIfPossible(computerPawns, location);
            }
        }
        return pawn;
    }
}
