package com.anna.szczech.royalgameofur.result;

public class Points {
    private int userPoints = 0;
    private int computerPoints = 0;

    public void addPointToUser(){
        userPoints++;
    }

    public void addPointToComputer(){
        computerPoints++;
    }

    public int getUserPoints() {
        return userPoints;
    }

    public int getComputerPoints() {
        return computerPoints;
    }

    @Override
    public String toString() {
        return "USER POINTS : COMPUTER POINTS\n                   "
                + userPoints + "  :  " + computerPoints;
    }
}
