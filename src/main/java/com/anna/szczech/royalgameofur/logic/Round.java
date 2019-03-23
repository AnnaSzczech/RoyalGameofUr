package com.anna.szczech.royalgameofur.logic;

import com.anna.szczech.royalgameofur.gui.Board;
import com.anna.szczech.royalgameofur.gui.Pawns;
import com.anna.szczech.royalgameofur.gui.Roll;
import com.anna.szczech.royalgameofur.player.Player;
import com.anna.szczech.royalgameofur.player.PlayerEnum;
import javafx.scene.layout.FlowPane;

import java.util.List;
import java.util.stream.Collectors;

abstract class Round {
    public Board board;
    public Pawns pawn;
    private Pawns capturePawn = null;
    public boolean bonusRoll = false;
    public Roll roll;

    public Round(Board board, Roll roll) {
        this.board = board;
        this.roll = roll;
    }

    public void newRound() {
            int oldLocation = pawn.getLocation();
            int newLocation = oldLocation + roll.getRolledNumber();
            if (canSelectedPawnChangeLocation(newLocation, pawn)) {
                movePawn(oldLocation, newLocation);
                checkIfIsEndOfTheGame();
                if (!isBonusRoll(oldLocation, newLocation)) {
                    changeTurn();
                } else {
                    sendMessage("BONUS ROLL!");
                }
                resetRoll();
            } else {
                sendMessage("WRONG MOVE, REPEAT");
            }
            specificMove(oldLocation);
    }

    private boolean canSelectedPawnChangeLocation(int newLocation, Pawns pawn) {
        return (!isPawnOffTheBordAfterMove(newLocation) && isTheFieldFree(newLocation) && pawn.getPlayerEnum() == whoseTurn() && !isEnemyOnSafeSpot(newLocation));
    }

    private boolean isPawnOffTheBordAfterMove(int newLocation) {
        return newLocation > 15;
    }

    private boolean isTheFieldFree(int location) {
        List<Pawns> pawns = whichListOfPawnIsTurn();
        if (location == 15) {
            return true;
        }
        return !(isPawnOnSpecificLocation(pawns, location));
    }

    private PlayerEnum whoseTurn() {
        PlayerEnum whoseTurn;
        if (board.isUserTurn()) {
            whoseTurn = PlayerEnum.USER;
        } else {
            whoseTurn = PlayerEnum.COMPUTER;
        }
        return whoseTurn;
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

    private boolean isUserPawn(Pawns pawn){
        return pawn.getPlayerEnum() == PlayerEnum.USER;
    }

    private boolean isPawnOnSpecificLocation(List<Pawns> pawns, int newLocation){
        return pawns.stream().filter(enemyPawn -> enemyPawn.getLocation() == newLocation).findFirst().isPresent();
    }

    private List<Pawns> whichListOfPawnIsTurn() {
        List<Pawns> pawns;
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
        if (board.isUserTurn()) {
            board.getUser().addPoint();
        } else {
            board.getComputer().addPoint();
        }
        printResult();
        moveToSaveSpot();
    }

    private void printResult() {
        board.getResultLabel().setText(board.resultForAllPlayers());
    }

    private void moveToSaveSpot() {
        pawn.setScaleX(0.5);
        pawn.setScaleY(0.5);
        if (board.isUserTurn()) {
            pawn.setLayoutX(640 - (board.getUser().getPoints() - 1) * 30);
            pawn.setLayoutY(160);
        } else {
            pawn.setLayoutX(790 + (board.getComputer().getPoints() - 1) * 30);
            pawn.setLayoutY(160);
        }
    }

    private void movePawn(int oldLocation, int newLocation) {
        pawn.setLocation(newLocation);
        if (willPawnGetPoint()) {
            moveToWin();
        } else {
            if (board.isUserTurn()) {
                changePawnLocalizationOnBoard(board.getUser());
            } else {
                changePawnLocalizationOnBoard(board.getComputer());
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

    public void movePawnFromBoxToGameBoard() {
        if (!board.isGameEnded && roll.getRolledNumber() != 0) {
            FlowPane boxWithPawns = fromWhichBoxGetPawn();
            boxWithPawns.getChildren().remove(pawn);
            board.pane.getChildren().add(pawn);
        }
    }

    private FlowPane fromWhichBoxGetPawn() {
        FlowPane boxWithPawns;
        if (isUserPawn(pawn)) {
            boxWithPawns = board.boxWithPlayerPawns;
        } else {
            boxWithPawns = board.boxWithComputerPawns;
        }
        return boxWithPawns;
    }

    private void changePawnLocalizationOnBoard(Player players) {
        pawn.setLayoutX(players.getLocalizationOnBoard().entrySet().stream().filter(n -> n.getValue() == pawn.getLocation()).map(n -> n.getKey().getX()).findFirst().get());
        pawn.setLayoutY(players.getLocalizationOnBoard().entrySet().stream().filter(n -> n.getValue() == pawn.getLocation()).map(n -> n.getKey().getY()).findFirst().get());
    }

    public void checkIfPawnHaveToBackToTheBox() {
        if (pawn.getLocation() > 4 && pawn.getLocation() < 13) {
            if (isUserPawn(pawn)) {
                capturePawn(getComputerPawns());
            } else {
                capturePawn(getUserPawns());
            }
        }
        movePawnWhenCaptured(board.boxWithPlayerPawns, board.boxWithComputerPawns); //czy zbito pionek przeciwnika
    }

    private void capturePawn(List<Pawns> pawns) {
        if (isPawnToCapture(pawns)) {
            capturePawn = pawns.stream().filter(enemyPawn -> enemyPawn.getLocation() == pawn.getLocation()).findFirst().get();
        }
    }

    private boolean isPawnToCapture(List<Pawns> pawns) {
        return (isPawnOnSpecificLocation(pawns, pawn.getLocation()));
    }

    public boolean isBonusRoll(int oldLocation, int newLocation) {
        if ((pawn.getLocation() == 4 || pawn.getLocation() == 8 || pawn.getLocation() == 14) && oldLocation != newLocation){
            bonusRoll = true;
        } else {
            bonusRoll = false;
        }
        return bonusRoll;
    }

    public void changeTurn() {
        board.setUserTurn(!board.isUserTurn());
    }

    private void movePawnWhenCaptured(FlowPane boxWithPlayerPawns, FlowPane boxWithComputerPawns) {
        if (capturePawn != null) {
            movePawnFromGameBoardToBoxWithPawns(boxWithPlayerPawns, boxWithComputerPawns);
        }
    }

    private void movePawnFromGameBoardToBoxWithPawns(FlowPane boxWithPlayerPawns, FlowPane boxWithComputerPawns) {
        capturePawn.setLocation(0);
        board.pane.getChildren().remove(capturePawn);
        if (isUserPawn(capturePawn)) {
            boxWithPlayerPawns.getChildren().add(capturePawn);
        } else {
            boxWithComputerPawns.getChildren().add(capturePawn);
        }
        capturePawn = null;
    }

    private void checkIfIsEndOfTheGame() {
        if (board.getUser().getPoints() == 7 || board.getComputer().getPoints() == 7) {
            board.isGameEnded = true;
            sendMessage("THE WINNER IS " + whoWon());
        }
    }

    private String whoWon() {
        String player;
        if (board.getComputer().getPoints() == 7) {
            player = "COMPUTER";
        } else {
            player = "USER";
        }
        return player;
    }

    private void writeWhoseTurn() {
        if (board.isUserTurn()) {
            sendMessage("USER TURN");
        } else {
            sendMessage("COMPUTER TURN");
        }
    }

    public void sendMessage(String message) {
        board.messageLabel.setText(message);
    }

    public boolean isThereAnyPossibleMove() {
        // spr czy został wybrany pionek gracza którego jest kolej
        if (pawn.getPlayerEnum() != whoseTurn()) {
            return true;
        }
        List<Pawns> pawns = whichListOfPawnIsTurn();
        pawns = pawns.stream().filter(pawn -> pawn.getLocation() < 15).collect(Collectors.toList());
        return pawns.stream().filter(pawn -> canSelectedPawnChangeLocation(pawn.getLocation() + roll.getRolledNumber(), pawn)).findFirst().isPresent();
    }

    private List<Pawns> getUserPawns(){
        return board.getUser().getPawns();
    }

    public List<Pawns> getComputerPawns(){
        return board.getComputer().getPawns();
    }

    public void resetRoll(){
        if (!board.isGameEnded) {
            roll.wasRolled = false;
            roll.setRolledNumber(0);
            board.rolledNumberLabel.setText(String.valueOf(roll.getRolledNumber()));
            if (!bonusRoll) {
                writeWhoseTurn();
            }
        }
    }

    abstract void specificMove(int oldLocationOfPawn);
}
