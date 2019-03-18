package com.anna.szczech.royalgameofur.logic;

import com.anna.szczech.royalgameofur.gui.Board;
import com.anna.szczech.royalgameofur.gui.Pawns;
import com.anna.szczech.royalgameofur.player.Computer;
import com.anna.szczech.royalgameofur.player.Player;
import com.anna.szczech.royalgameofur.player.User;
import javafx.scene.layout.FlowPane;

import java.util.List;
import java.util.stream.Collectors;

abstract class Round {
    public Board board;
    public Pawns pawn;
    private Pawns capturePawn = null;
    public boolean bonusRoll = false;

    public Round(Board board) {
        this.board = board;
    }

    public void newRound() {
            int oldLocation = pawn.getLocation();
            int newLocation = oldLocation + board.getMove();
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
        return (!isPawnOffTheBordAfterMove(newLocation) && isTheFieldFree(newLocation) && pawn.getPlayerClass() == whoseTurn() && !isEnemyOnSafeSpot(newLocation));
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

    private Object whoseTurn() {
        Object whoseTurn;
        if (board.isUserTurn()) {
            whoseTurn = User.class;
        } else {
            whoseTurn = Computer.class;
        }
        return whoseTurn;
    }

    private boolean isEnemyOnSafeSpot(int newLocation) {
        boolean isEnemyOnSafeSpot = false;
        if (newLocation == 8) {
            if (pawn.getPlayerClass() == User.class) {
                isEnemyOnSafeSpot = isPawnOnSpecificLocation(getComputerPawns(), newLocation);
            } else {
                isEnemyOnSafeSpot = isPawnOnSpecificLocation(getUserPawns(), newLocation);
            }
        }
        return isEnemyOnSafeSpot;
    }

    private boolean isPawnOnSpecificLocation(List<Pawns> pawns, int newLocation){
        return pawns.stream().filter(enemyPawn -> enemyPawn.getLocation() == newLocation).findFirst().isPresent();
    }

    private List<Pawns> whichListOfPawnIsTurn() {
        List<Pawns> pawns;
        if (pawn.getPlayerClass() == User.class) {
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
            board.getResult().addPointToUser();
        } else {
            board.getResult().addPointToComputer();
        }
        printResult();
        moveToSaveSpot();
    }

    private void printResult() {
        board.getResultLabel().setText(String.valueOf(board.getResult()));
    }

    private void moveToSaveSpot() {
        pawn.setScaleX(0.5);
        pawn.setScaleY(0.5);
        if (board.isUserTurn()) {
            pawn.setLayoutX(640 - (board.getResult().getUserPoints() - 1) * 30);
            pawn.setLayoutY(160);
        } else {
            pawn.setLayoutX(790 + (board.getResult().getComputerPoints() - 1) * 30);
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
        if (!board.isEndGame && board.getMove() != 0) {
            FlowPane boxWithPawns = fromWhichBoxGetPawn();
            boxWithPawns.getChildren().remove(pawn);
            board.pane.getChildren().add(pawn);
        }
    }

    private FlowPane fromWhichBoxGetPawn() {
        FlowPane boxWithPawns;
        if (pawn.getPlayerClass() == User.class) {
            boxWithPawns = board.boxWithPlayerPawns;
        } else {
            boxWithPawns = board.boxWithComputerPawns;
        }
        return boxWithPawns;
    }

    private void changePawnLocalizationOnBoard(Player player) {
        pawn.setLayoutX(player.getLocalizationOnBoard().entrySet().stream().filter(n -> n.getValue() == pawn.getLocation()).map(n -> n.getKey().getX()).findFirst().get());
        pawn.setLayoutY(player.getLocalizationOnBoard().entrySet().stream().filter(n -> n.getValue() == pawn.getLocation()).map(n -> n.getKey().getY()).findFirst().get());
    }

    public void checkIfPawnHaveToBackToTheBox() {
        if (pawn.getLocation() > 4 && pawn.getLocation() < 13) {
            if (pawn.getPlayerClass() == board.getUser().getClass()) {
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
        if (capturePawn.getPlayerClass() == User.class) {
            boxWithPlayerPawns.getChildren().add(capturePawn);
        } else {
            boxWithComputerPawns.getChildren().add(capturePawn);
        }
        capturePawn = null;
    }

    private void checkIfIsEndOfTheGame() {
        if (board.getResult().getComputerPoints() == 7 || board.getResult().getUserPoints() == 7) {
            board.isEndGame = true;
            sendMessage("THE WINNER IS " + whoWon());
        }
    }

    private String whoWon() {
        String player;
        if (board.getResult().getComputerPoints() == 7) {
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
        if (pawn.getPlayerClass() != whoseTurn()) {
            return true;
        }
        List<Pawns> pawns = whichListOfPawnIsTurn();
        pawns = pawns.stream().filter(pawn -> pawn.getLocation() < 15).collect(Collectors.toList());
        return pawns.stream().filter(pawn -> canSelectedPawnChangeLocation(pawn.getLocation() + board.getMove(), pawn)).findFirst().isPresent();
    }

    private List<Pawns> getUserPawns(){
        return board.getUser().getPawns();
    }

    public List<Pawns> getComputerPawns(){
        return board.getComputer().getPawns();
    }

    public void resetRoll(){
        if (!board.isEndGame) {
            board.wasRolled = false;
            board.setMove(0);
            board.rolledNumber.setText(String.valueOf(board.getMove()));
            if (!bonusRoll) {
                writeWhoseTurn();
            }
        }
    }

    abstract void specificMove(int oldLocationOfPawn);
}
