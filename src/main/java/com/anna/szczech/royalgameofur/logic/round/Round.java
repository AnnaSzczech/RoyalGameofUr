package com.anna.szczech.royalgameofur.logic.round;

import com.anna.szczech.royalgameofur.gui.Pawn;
import com.anna.szczech.royalgameofur.logic.Game;
import com.anna.szczech.royalgameofur.logic.PosibleMoveChecker;
import com.anna.szczech.royalgameofur.player.PlayerEnum;

import java.util.List;

abstract class Round {
    private Pawn pawn;
    private Pawn capturePawn;
    private Game game;

    private int oldLocation;

    public Round(Game game, Pawn pawn){
        this.pawn = pawn;
        this.game = game;
    }

    public void newRound() {
        oldLocation = pawn.getLocation();
        int newLocation = oldLocation + game.getRoll().getRolledNumber();
        PosibleMoveChecker posibleMoveChecker = new PosibleMoveChecker(game.getComputer().getPawns(), game.getUser().getPawns(), game.getRoll().getRolledNumber(), game.isUserTurn());
        if (posibleMoveChecker.canSelectedPawnChangeLocation(newLocation, pawn)) {
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
            if (getGame().isUserPawn(pawn)) {
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
        PosibleMoveChecker posibleMoveChecker = new PosibleMoveChecker(game.getComputer().getPawns(), game.getUser().getPawns(), game.getRoll().getRolledNumber(), game.isUserTurn());
        return (posibleMoveChecker.isPawnOnSpecificLocation(pawns, pawn.getLocation()));
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

    public int getOldLocation() {
        return oldLocation;
    }

}
