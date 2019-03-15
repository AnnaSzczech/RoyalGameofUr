package com.anna.szczech.royalgameofur.logic;

import com.anna.szczech.royalgameofur.GUI.Board;
import com.anna.szczech.royalgameofur.GUI.Pawns;
import com.anna.szczech.royalgameofur.player.Computer;
import com.anna.szczech.royalgameofur.player.Player;
import com.anna.szczech.royalgameofur.player.User;
import javafx.scene.layout.FlowPane;

import java.util.List;

public class Round {
    private Board board;
    private Pawns pawn;
    private Pawns capturePawn = null;

    public Round(Board board, Pawns pawn) {
        this.board = board;
        this.pawn = pawn;
        newRound();
    }

    public void newRound() {
        int oldLocation = pawn.getLocation();
        int newLocation = oldLocation + board.getMove();
        if (canSelectedPawnChangeLocation(newLocation)) {
            pawn.setLocation(pawn.getLocation() + board.getMove());
            if (willPawnGetPoint()) {
                moveToWin();
            } else {
                movePawnOnTheBoard(oldLocation);
                checkIfPawnIsCaptured(board.boxWithPlayerPawns, board.boxWithComputerPawns); //czy zbito pionek przeciwnika
            }
            isEndOfTheGame();
            if (!isBonusRoll()) {
                changeTurn();
                computerMove(board.boxWithComputerPawns);
                if (!board.isEndGame) {
                    writeWhoseTurn();
                }
            } else {
                sendMessage("BONUS ROLL!");
            }
        } else {
            sendMessage("REPEAT MOVE, CHOOSE PAWN");
        }
        board.wasRolled = false;
        board.setMove(0);
        board.rolledNumber.setText(String.valueOf(board.getMove()));
    }

    private boolean canSelectedPawnChangeLocation(int newLocation){
        return (!isPawnOffTheBordAfterMove(newLocation) && !isTheFieldFree(newLocation) && pawn.getPlayerClass() == whoseTurn() && !isEnemyOnSafeSpot(newLocation));
    }

    private boolean isPawnInTheBox(int location) {
        return location == 0;
    }

    private boolean isPawnOffTheBordAfterMove(int newLocation) {
        return newLocation > 15;
    }

    private boolean isTheFieldFree(int location){
        List<Pawns> pawns = whichListOfPawnIsTurn();
        return pawns.stream().filter(pawn -> pawn.getLocation() == location && pawn.getLocation() != 15).findFirst().isPresent();
    }

    private Object whoseTurn(){
        Object whoseTurn;
        if (board.isUserTurn()){
            whoseTurn = User.class;
        } else {
            whoseTurn = Computer.class;
        }
        return whoseTurn;
    }
    private boolean isEnemyOnSafeSpot(int newLocation){
        boolean isEnemyOnSafeSpot = false;
        if (newLocation == 8) {
            if (pawn.getPlayerClass() == User.class){
                isEnemyOnSafeSpot = board.getComputerPawns().getPawns().stream().filter(enemyPawn -> enemyPawn.getLocation() == newLocation).findFirst().isPresent();
            } else {
                isEnemyOnSafeSpot = board.getUserPawns().getPawns().stream().filter(enemyPawn -> enemyPawn.getLocation() == newLocation).findFirst().isPresent();
            }
        }
        return  isEnemyOnSafeSpot;
    }

    private List<Pawns> whichListOfPawnIsTurn(){
        List<Pawns> pawns;
        if (pawn.getPlayerClass() == User.class){
            pawns = board.getUserPawns().getPawns();
        } else {
            pawns = board.getComputerPawns().getPawns();
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

    private void movePawnOnTheBoard(int oldLocation) {
        if (board.isUserTurn()) {
            changePawnLocalizationOnBoard(board.getUserPawns());
        } else {
            changePawnLocalizationOnBoard(board.getComputerPawns());
        }
        if (isPawnInTheBox(oldLocation)) {
            movePawnFromBoxToGameBoard();
        }
        checkIfPawnHaveToBackToTheBox();
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
            if (pawn.getPlayerClass() == board.getUserPawns().getClass()) {
                capturePawn(board.getComputerPawns().getPawns());
            } else {
                capturePawn(board.getUserPawns().getPawns());
            }
        }
    }

    private void capturePawn(List<Pawns> pawns) {
        if (isPawnToCapture(pawns)) {
            capturePawn = pawns.stream().filter(enemyPawn -> enemyPawn.getLocation() == pawn.getLocation()).findFirst().get();
        }
    }

    private boolean isPawnToCapture(List<Pawns> pawns) {
        return pawns.stream().filter(enemyPawn -> enemyPawn.getLocation() == pawn.getLocation()).findFirst().isPresent();
    }

    public boolean isBonusRoll(){
        return (pawn.getLocation() == 4 || pawn.getLocation() == 8 || pawn.getLocation() == 14);
    }

    private void changeTurn() {
        board.setUserTurn(!board.isUserTurn());
    }

    private void checkIfPawnIsCaptured(FlowPane boxWithPlayerPawns, FlowPane boxWithComputerPawns) {
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

    private void isEndOfTheGame() {
        if (board.getResult().getComputerPoints() == 7 || board.getResult().getUserPoints() == 7) {
            board.isEndGame = true;
            sendMessage("The winner is " + whoWon());
        }
    }

    private String whoWon(){
        String player;
        if (board.getResult().getComputerPoints() == 7) {
            player = "Computer";
        } else {
            player = "User";
        }
        return player;
    }

    private void computerMove(FlowPane boxWithComputerPawns) {
        if (!board.isUserTurn()) {
            ComputerLogic computerLogic = new ComputerLogic(board);
            computerLogic.run(boxWithComputerPawns, board.getComputerPawns().getPawns());
        }
    }

    private void writeWhoseTurn() {
        if (board.isUserTurn()) {
            sendMessage("USER TURN");
        } else {
            sendMessage("COMPUTER TURN");
        }
    }

    private void sendMessage(String message) {
        board.messageLabel.setText(message);
    }
}
