package com.anna.szczech.royalgameofur.logic;

import com.anna.szczech.royalgameofur.gui.Board;
import com.anna.szczech.royalgameofur.gui.Field;
import com.anna.szczech.royalgameofur.gui.Pawn;
import com.anna.szczech.royalgameofur.logic.round.ComputerRound;
import com.anna.szczech.royalgameofur.logic.round.PlayerRound;
import com.anna.szczech.royalgameofur.player.Player;
import com.anna.szczech.royalgameofur.player.PlayerEnum;

import java.util.List;
import java.util.stream.Collectors;

public class Game {
    private Roll roll = new Roll();
    private Player user;
    private Player computer;
    private Board board;
    private boolean isGameEnded = false;
    public boolean bonusRoll = false;

    public void movePawn(Pawn pawn) {
        if (!isGameEnded && roll.getRolledNumber() != 0) {
            PlayerRound playerRound = new PlayerRound(this, pawn);
            playerRound.newRound();
            if (!user.isPlayerTurn() && !isGameEnded) {
                resetRoll();
                makeARoll(board);
            }
        }
    }

    public void addUserAndComputer(Player user, Player computer) {
        this.user = user;
        this.computer = computer;
    }

    public void makeARoll(Board board) {
        this.board = board;
        int oldRolledNumber = roll.getRolledNumber();
        roll.diceRoll(isGameEnded);
        board.rolledNumberLabel.setText(String.valueOf(roll.getRolledNumber()));
        if (oldRolledNumber != roll.getRolledNumber()) {
            checkIfThereIsAnyMove();
        }
    }

    private void checkIfThereIsAnyMove(){
        PosibleMoveChecker posibleMoveChecker = new PosibleMoveChecker(computer.getPawns(), user.getPawns(), roll.getRolledNumber(), isUserTurn());
        if (!posibleMoveChecker.isThereAnyPossibleMove()){
            if (isUserTurn()) {
                board.newRoundButton.setVisible(true);
                sendMessage("There is no move to make, click NEW ROUND");
            } else {
                System.out.println("no move 1");
                changeTurn();
                resetRoll();
            }
        } else {
            if (!isUserTurn()) {
                List<Pawn> userPawns = getUser().getPawns().stream()
                        .filter(pawn -> pawn.getLocation() > 4 && pawn.getLocation() <12)
                        .collect(Collectors.toList());
                List<Pawn> computerPawns = getComputer().getPawns().stream()
                        .filter(pawn -> pawn.getLocation() <15)
                        .collect(Collectors.toList());
                ComputePawnSelector computePawnSelector = new ComputePawnSelector(computerPawns, userPawns, this);
                createNewComputerRound(new ComputerRound(this, computePawnSelector.selectThePawn()));
            }
        }

    }

    public void createNewComputerRound(ComputerRound computerRound) {
            computerRound.newRound();
            computerRound.makeBonusRoll(computerRound.getOldLocation());
    }

    public void changeTurn() {
        user.changeTurn();
        computer.changeTurn();
    }

    public void startNewRound() {
        if (!isGameEnded) {
            changeTurn();
            board.newRoundButton.setVisible(false);
            resetRoll();
            makeARoll(board);
        }
    }

    public void printResult() {
        board.printResult(user.getPoints(), computer.getPoints());
    }

    public void movePawnFromBoxToGameBoard(Pawn pawn) {
        board.movePawnFromBoxToGameBoard(pawn, isUserPawn(pawn));
    }

    public void moveCapturedPawn(Pawn capturePawn) {
            board.movePawnIfCaptured(capturePawn, isUserPawn(capturePawn));
    }

    public boolean isUserPawn(Pawn pawn) {
        return pawn.getPlayerEnum() == PlayerEnum.USER;
    }

    public void resetRoll(){
        if (!isGameEnded()) {
            roll.wasRolled = false;
            roll.setRolledNumber(0);
            board.rolledNumberLabel.setText(String.valueOf(roll.getRolledNumber()));
            if (!bonusRoll) {
                writeWhoseTurn();
            }
        }
    }
    private void writeWhoseTurn() {
        if (isUserTurn()) {
            sendMessage("USER TURN");
        } else {
            sendMessage("COMPUTER TURN");
        }
    }

    public void moveFromTheGameBoard(Pawn pawn) {
        board.moveFromTheGameBoard(pawn, user.getPoints(), computer.getPoints(), user.isPlayerTurn());
    }

    public void changePawnLocalizationOnBoard(Pawn pawn) {
        int location = pawn.getLocation();
        PlayerEnum playerEnum = pawn.getPlayerEnum();
        if (location > 4 && location < 13) {
            playerEnum = PlayerEnum.ALL_PLAYERS;
        }
        Field changedField = Field.getFieldFor(location, playerEnum);
        pawn.setLayoutX(changedField.getX());
        pawn.setLayoutY(changedField.getY());
    }

    public boolean isUserTurn(){
        return user.isPlayerTurn();
    }

    public void sendMessage(String message) {
        board.messageLabel.setText(message);
    }

    public Player getUser() {
        return user;
    }

    public Player getComputer() {
        return computer;
    }

    public Roll getRoll() {
        return roll;
    }

    public boolean isGameEnded() {
        return isGameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        isGameEnded = gameEnded;
    }

    public Board getBoard() {
        return board;
    }

}
