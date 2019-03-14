package com.anna.szczech.royalgameofur.logic;

import com.anna.szczech.royalgameofur.GUI.Pawns;

import java.util.List;

public class Move {
    private Board board;
    private Pawns pawn;

    public Move(Board board, Pawns pawn){
        this.board = board;
        this.pawn = pawn;
        movePawn();
    }

    public void movePawn() {
        board.selectedPawn = pawn;
        int newLocation = pawn.getLocation() + board.getMove();
        if (!isPawnOffTheBordAfterMove(newLocation)) {
            pawn.setLocation(pawn.getLocation() + board.getMove());

            if (willPawnGetPoint()) {
                if (board.isUserTurn()) {
                    board.getResult().addPointToUser();
                } else {
                    board.getResult().addPointToComputer();
                }
                printResult();
                moveToSaveSpot();
            } else {
                if (board.isUserTurn()) {
                    changePawnLocalizationOnBoard(board.getUserPawns());
                } else {
                    changePawnLocalizationOnBoard(board.getComputerPawns());
                }
                checkIfPawnHaveToBackToTheBox();
            }
            changeTurn();
//        if (board.isUserTurn()) {
////            ComputerLogic computerLogic = new ComputerLogic(board);
////            computerLogic.run();
////        }
            isEndOfTheGame();
        }
    }

    private boolean isPawnOffTheBordAfterMove(int newLocation){
        return newLocation > 15;
    }

    private boolean willPawnGetPoint(){
        return pawn.getLocation() == 15;
    }

    private void printResult(){
        board.getResultLabel().setText(String.valueOf(board.getResult()));
    }

    private void moveToSaveSpot(){
        pawn.setScaleX(0.5);
        pawn.setScaleY(0.5);
        if (board.isUserTurn()) {
            pawn.setLayoutX(640-(board.getResult().getUserPoints()-1)*30);
            pawn.setLayoutY(160);
        } else {
            pawn.setLayoutX(790+(board.getResult().getComputerPoints()-1)*30);
            pawn.setLayoutY(160);
        }
    }

    private void changePawnLocalizationOnBoard(Player player){
        pawn.setLayoutX(player.getLocalizationOnBoard().entrySet().stream().filter(n -> n.getValue() == pawn.getLocation()).map(n -> n.getKey().getX()).findFirst().get());
        pawn.setLayoutY(player.getLocalizationOnBoard().entrySet().stream().filter(n -> n.getValue() == pawn.getLocation()).map(n -> n.getKey().getY()).findFirst().get());
    }

    public void checkIfPawnHaveToBackToTheBox(){
        if (pawn.getLocation() > 4 && pawn.getLocation() < 13) {
            if (pawn.getPlayerClass() == board.getUserPawns().getClass()) {
                capturePawn(board.getComputerPawns().getPawns());
            } else {
                capturePawn(board.getUserPawns().getPawns());
            }
        }
    }

    private void capturePawn(List<Pawns> pawns){
        if (isPawnToCapture(pawns)) {
            board.capturedPawn = pawns.stream().filter(enemyPawn -> enemyPawn.getLocation() == pawn.getLocation()).findFirst().get();
        }
    }

    private boolean isPawnToCapture(List<Pawns> pawns){
        return pawns.stream().filter(enemyPawn -> enemyPawn.getLocation() == pawn.getLocation()).findFirst().isPresent();
    }

    private void changeTurn(){
        board.setUserTurn(!board.isUserTurn());
    }

    private void isEndOfTheGame(){
        if (board.getResult().getComputerPoints() == 7 || board.getResult().getUserPoints() == 7){
            board.isEndGame = true;
        }
    }
}
