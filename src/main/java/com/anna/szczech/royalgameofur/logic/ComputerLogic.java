package com.anna.szczech.royalgameofur.logic;

import com.anna.szczech.royalgameofur.GUI.Pawns;

public class ComputerLogic {
    private Board board;

    public ComputerLogic(Board board){
        this.board = board;
    }

//    public void run(){
//        if (!board.isUserTurn()){
//            board.rollNumberOnDice();
//            int rollNumber = (int) Math.random()*7+1;
//            Pawns pawn = board.getComputerPawns().getPawns().get(rollNumber);
//            pawn.movePawn();
//            pawn.checkIfPawnHaveToBackToTheBox();
//        }
//    }
}
