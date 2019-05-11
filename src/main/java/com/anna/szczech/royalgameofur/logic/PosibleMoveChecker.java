package com.anna.szczech.royalgameofur.logic;

import com.anna.szczech.royalgameofur.gui.Pawn;
import com.anna.szczech.royalgameofur.player.PlayerEnum;

import java.util.List;
import java.util.stream.Collectors;

public class PosibleMoveChecker {
    private List<Pawn> computerPawns;
    private List<Pawn> userPawns;
    private int rolledNumber;
    private boolean isUserTurn;

    public PosibleMoveChecker(List<Pawn> computerPawns, List<Pawn> userPawns, int rolledNumber, boolean isUserTurn) {
        this.computerPawns = computerPawns;
        this.userPawns = userPawns;
        this.rolledNumber = rolledNumber;
        this.isUserTurn = isUserTurn;
    }

    public boolean canSelectedPawnChangeLocation(int newLocation, Pawn pawn) {
        return (!isPawnOffTheBordAfterMove(newLocation) && isTheFieldFree(newLocation) && pawn.getPlayerEnum() == whoseTurn() && !isEnemyOnSafeSpot(newLocation, pawn));
    }

    private PlayerEnum whoseTurn() {
        return (isUserTurn) ? PlayerEnum.USER : PlayerEnum.COMPUTER;
    }

    private boolean isPawnOffTheBordAfterMove(int newLocation) {
        return newLocation > 15;
    }

    private boolean isTheFieldFree(int location) {
        List<Pawn> pawns = whichListOfPawnIsTurn();
        if (location == 15) {
            return true;
        }
        return !(isPawnOnSpecificLocation(pawns, location));
    }

    private boolean isEnemyOnSafeSpot(int newLocation, Pawn pawn) {
        boolean isEnemyOnSafeSpot = false;
        if (newLocation == 8) {
            if (isUserPawn(pawn)) {
                isEnemyOnSafeSpot = isPawnOnSpecificLocation(computerPawns, newLocation);
            } else {
                isEnemyOnSafeSpot = isPawnOnSpecificLocation(userPawns, newLocation);
            }
        }
        return isEnemyOnSafeSpot;
    }

    private boolean isUserPawn(Pawn pawn) {
        return pawn.getPlayerEnum() == PlayerEnum.USER;
    }

    public boolean isPawnOnSpecificLocation(List<Pawn> pawns, int newLocation) {
        return pawns.stream()
                .anyMatch(enemyPawn -> enemyPawn.getLocation() == newLocation);
    }

    private List<Pawn> whichListOfPawnIsTurn() {
        return (isUserTurn) ? userPawns : computerPawns;
    }

    public boolean isThereAnyPossibleMove() {
        List<Pawn> pawns = whichListOfPawnIsTurn().stream()
                .filter(playerPawn -> playerPawn.getLocation() < 15)
                .collect(Collectors.toList());
        return pawns.stream()
                .anyMatch(playerPawn -> canSelectedPawnChangeLocation(playerPawn.getLocation() + rolledNumber, playerPawn));
    }
}
