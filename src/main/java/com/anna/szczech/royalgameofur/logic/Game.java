package com.anna.szczech.royalgameofur.logic;

import com.anna.szczech.royalgameofur.gui.Board;
import com.anna.szczech.royalgameofur.gui.Field;
import com.anna.szczech.royalgameofur.gui.Pawn;
import com.anna.szczech.royalgameofur.logic.round.ComputerRound;
import com.anna.szczech.royalgameofur.logic.round.PlayerRound;
import com.anna.szczech.royalgameofur.player.Player;
import com.anna.szczech.royalgameofur.player.PlayerEnum;

public class Game {
    private Roll roll = new Roll();
    private Player user;
    private Player computer;
    private Board board;
    private boolean isGameEnded = false;
    public boolean bonusRoll = false;

    public void movePawn(Pawn pawn) {
        if (!isGameEnded && roll.getRolledNumber() != 0) {
            PlayerRound playerRound = new PlayerRound(pawn, this);
            playerRound.newRound();
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
            createFakeRound();
        }
    }

    private void createFakeRound() {
        PlayerRound playerRound = new PlayerRound(user.getPawns().get(0), this);
        if (!playerRound.isThereAnyPossibleMove()) {
            board.newRoundButton.setVisible(true);
           sendMessage("There is no move to make, click NEW ROUND");
        }
    }

    public void createNewComputerRound(ComputerRound computerRound) {
        if (computerRound.isThereAnyPossibleMove()) {
            computerRound.newRound();
        } else {
            System.out.println("no move 1");
            changeTurn();
            resetRoll();
        }

    }

    public void changeTurn() {
        user.changeTurn();
        computer.changeTurn();
    }

    public void startNewRound() {
        if (!isGameEnded) {
            user.changeTurn();
            computer.changeTurn();
            board.newRoundButton.setVisible(false);
            ComputerRound computerRound = new ComputerRound(this);
            computerRound.newRound();
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

    public PlayerEnum whoseTurn() {
        PlayerEnum whoseTurn;
        if (isUserTurn()) {
            whoseTurn = PlayerEnum.USER;
        } else {
            whoseTurn = PlayerEnum.COMPUTER;
        }
        return whoseTurn;
    }

    public void moveFromTheGameBoard(Pawn pawn) {
        board.moveFromTheGameBoard(pawn, user.getPoints(), computer.getPoints(), user.isPlayerTurn());
    }

    public void changePawnLocalizationOnBoard(Pawn pawn) {
        int location = pawn.getLocation();
        PlayerEnum playerEnum = pawn.getPlayerEnum();
        if (location > 4 && location < 13) {
            playerEnum = PlayerEnum.USER.ALL_PLAYERS;
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
}
