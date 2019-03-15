package com.anna.szczech.royalgameofur.logic;

import com.anna.szczech.royalgameofur.GUI.Board;
import com.anna.szczech.royalgameofur.GUI.Pawns;
import javafx.scene.layout.FlowPane;

import java.util.List;

public class ComputerLogic {
    private Board board;

    public ComputerLogic(Board board){
        this.board = board;
    }

    public void run(FlowPane boxWithPawns, List<Pawns> pawns){
        board.wasRolled = false;
        if (!board.isUserTurn()){
            board.rollNumberOnDice();
            board.rolledNumber.setText(String.valueOf(board.getMove()));
            int rollNumber = (int) (Math.random()*7);
            Pawns pawn = board.getComputerPawns().getPawns().get(rollNumber);
            int oldLocationOfPawn = pawn.getLocation();
            new Round(board, pawn);
            repeatIfPawnDidntMakeMove(oldLocationOfPawn, pawn.getLocation(), boxWithPawns, pawns);
            if (isBonusRoll(pawn)) {
                repeatMove(boxWithPawns, pawns);
            }
        }
    }

    public void repeatIfPawnDidntMakeMove(int oldLocationOfPawn, int newLocationOfPawn, FlowPane boxWithPawns, List<Pawns> pawns){
        if (oldLocationOfPawn == newLocationOfPawn) {
            repeatMove(boxWithPawns, pawns);
        }
    }

    private void repeatMove(FlowPane boxWithPawns, List<Pawns> pawns){
        run(boxWithPawns, pawns);
    }

    public boolean isBonusRoll(Pawns pawn){
        return (pawn.getLocation() == 4 || pawn.getLocation() == 8 || pawn.getLocation() == 14);
    }
}
