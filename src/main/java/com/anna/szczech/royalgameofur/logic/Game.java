package com.anna.szczech.royalgameofur.logic;

import com.anna.szczech.royalgameofur.gui.Board;
import com.anna.szczech.royalgameofur.gui.Pawns;
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

    public void movePawn(Pawns pawn) {
        if (!isGameEnded && roll.getRolledNumber() != 0) {
            PlayerRound playerRound = new PlayerRound(pawn);
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
        PlayerRound playerRound = new PlayerRound(user.getPawns().get(0));
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
            ComputerRound computerRound = new ComputerRound(getComputer().getPawns().get(0));
            computerRound.newRound();
        }
    }

    public void printResult() {
        board.printResult(user.getPoints(), computer.getPoints());
    }

    public void movePawnFromBoxToGameBoard(Pawns pawn) {
        board.movePawnFromBoxToGameBoard(pawn, isUserPawn(pawn));
    }

    public void moveCapturedPawn(Pawns capturePawn) {
            board.movePawnIfCaptured(capturePawn, isUserPawn(capturePawn));
    }

    public boolean isUserPawn(Pawns pawn) {
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

    public void moveFromTheGameBoard(Pawns pawn) {
        board.moveFromTheGameBoard(pawn, user.getPoints(), computer.getPoints(), user.isPlayerTurn());
    }

    public void changePawnLocalizationOnBoard(Player players, Pawns pawn) {
        pawn.setLayoutX(players.getLocalizationOnBoard().entrySet().stream().filter(n -> n.getValue() == pawn.getLocation()).map(n -> n.getKey().getX()).findFirst().get());
        pawn.setLayoutY(players.getLocalizationOnBoard().entrySet().stream().filter(n -> n.getValue() == pawn.getLocation()).map(n -> n.getKey().getY()).findFirst().get());
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
