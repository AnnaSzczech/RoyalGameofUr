package com.anna.szczech.royalgameofur.logic.round;

import com.anna.szczech.royalgameofur.gui.Pawn;
import com.anna.szczech.royalgameofur.logic.Game;
import com.anna.szczech.royalgameofur.player.PlayerEnum;

import java.util.List;
import java.util.stream.Collectors;

abstract class Round {
    private Pawn pawn;
    private Pawn capturePawn = null;
    private Game game;

    public void newRound() {
        int oldLocation = pawn.getLocation();
        int newLocation = oldLocation + game.getRoll().getRolledNumber();
        if (canSelectedPawnChangeLocation(newLocation, pawn)) {
            movePawn(oldLocation, newLocation);
            checkIfIsEndOfTheGame();
            if (!isBonusRoll(oldLocation, newLocation)) {
                game.changeTurn();
            } else {
                game.sendMessage("BONUS ROLL!");
            }
            game.resetRoll();
        } else {
            game.sendMessage("WRONG MOVE, REPEAT");
        }
        specificMove(oldLocation);
    }

    private boolean canSelectedPawnChangeLocation(int newLocation, Pawn pawn) {
        return (!isPawnOffTheBordAfterMove(newLocation) && isTheFieldFree(newLocation) && pawn.getPlayerEnum() == game.whoseTurn() && !isEnemyOnSafeSpot(newLocation));
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

    private boolean isEnemyOnSafeSpot(int newLocation) {
        boolean isEnemyOnSafeSpot = false;
        if (newLocation == 8) {
            if (isUserPawn(pawn)) {
                isEnemyOnSafeSpot = isPawnOnSpecificLocation(getComputerPawns(), newLocation);
            } else {
                isEnemyOnSafeSpot = isPawnOnSpecificLocation(getUserPawns(), newLocation);
            }
        }
        return isEnemyOnSafeSpot;
    }

    private boolean isUserPawn(Pawn pawn) {
        return pawn.getPlayerEnum() == PlayerEnum.USER;
    }

    public boolean isPawnOnSpecificLocation(List<Pawn> pawns, int newLocation) {
        return pawns.stream().filter(enemyPawn -> enemyPawn.getLocation() == newLocation).findFirst().isPresent();
    }

    private List<Pawn> whichListOfPawnIsTurn() {
        List<Pawn> pawns;
        if (isUserPawn(pawn)) {
            pawns = getUserPawns();
        } else {
            pawns = getComputerPawns();
        }
        return pawns;
    }

    private boolean willPawnGetPoint() {
        return pawn.getLocation() == 15;
    }

    private void moveToWin() {
        if (game.isUserTurn()) {
            game.getUser().addPoint();
        } else {
            game.getComputer().addPoint();
        }
        game.printResult();
        game.moveFromTheGameBoard(pawn);
    }

    private void movePawn(int oldLocation, int newLocation) {
        pawn.setLocation(newLocation);
        if (willPawnGetPoint()) {
            moveToWin();
        } else {
            if (game.isUserTurn()) {
                game.changePawnLocalizationOnBoard(pawn);
            } else {
                game.changePawnLocalizationOnBoard(pawn);
            }
            if (isPawnInTheBox(oldLocation)) {
                movePawnFromBoxToGameBoard();
            }
            checkIfPawnHaveToBackToTheBox();
        }
    }

    private boolean isPawnInTheBox(int location) {
        return location == 0;
    }

    private void movePawnFromBoxToGameBoard() {
        if (!game.isGameEnded() && game.getRoll().getRolledNumber() != 0) {
            game.movePawnFromBoxToGameBoard(pawn);
        }
    }

    private void checkIfPawnHaveToBackToTheBox() {
        if (pawn.getLocation() > 4 && pawn.getLocation() < 13) {
            if (isUserPawn(pawn)) {
                capturePawn(getComputerPawns());
            } else {
                capturePawn(getUserPawns());
            }
        }
        if (capturePawn != null) {
            game.moveCapturedPawn(capturePawn); //przenieś pionek jeżeli został zbity
            capturePawn = null;
        }
    }

    private void capturePawn(List<Pawn> pawns) {
        if (isPawnToCapture(pawns)) {
            capturePawn = pawns.stream().filter(enemyPawn -> enemyPawn.getLocation() == pawn.getLocation()).findFirst().get();
        }
    }

    private boolean isPawnToCapture(List<Pawn> pawns) {
        return (isPawnOnSpecificLocation(pawns, pawn.getLocation()));
    }

    public boolean isBonusRoll(int oldLocation, int newLocation) {
        if ((pawn.getLocation() == 4 || pawn.getLocation() == 8 || pawn.getLocation() == 14) && oldLocation != newLocation) {
            game.bonusRoll = true;
        } else {
            game.bonusRoll = false;
        }
        return game.bonusRoll;
    }

    private void checkIfIsEndOfTheGame() {
        if (game.getUser().getPoints() == 7 || game.getComputer().getPoints() == 7) {
            game.setGameEnded(true);
            game.sendMessage("THE WINNER IS " + whoWon());
        }
    }

    private PlayerEnum whoWon() {
        PlayerEnum player;
        if (game.getComputer().getPoints() == 7) {
            player = PlayerEnum.COMPUTER;
        } else {
            player = PlayerEnum.USER;
        }
        return player;
    }

    public boolean isThereAnyPossibleMove() {
        // spr czy został wybrany pionek gracza którego jest kolej
        if (pawn.getPlayerEnum() != game.whoseTurn()) {
            return true;
        }
        List<Pawn> pawns = whichListOfPawnIsTurn();
        pawns = pawns.stream().filter(pawn -> pawn.getLocation() < 15).collect(Collectors.toList());
        return pawns.stream().filter(pawn -> canSelectedPawnChangeLocation(pawn.getLocation() + game.getRoll().getRolledNumber(), pawn)).findFirst().isPresent();
    }

    public List<Pawn> getUserPawns() {
        return game.getUser().getPawns();
    }

    public List<Pawn> getComputerPawns() {
        return game.getComputer().getPawns();
    }

    public Pawn getPawn() {
        return pawn;
    }

    public Game getGame() {
        return game;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    abstract void specificMove(int oldLocationOfPawn);
}
