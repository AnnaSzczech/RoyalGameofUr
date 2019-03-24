package com.anna.szczech.royalgameofur.logic;

public class Roll {
    private int rolledNumber = 0;
    public boolean wasRolled = false;

    private void rollNumberOnDice(){rolledNumber = (int) (Math.random()*4 + 1);}

    public void diceRoll(boolean isGameEnded){
        if (!wasRolled && !isGameEnded) {
            rollNumberOnDice();
            wasRolled = true;
        }
    }

    public void setRolledNumber(int rolledNumber) {
        this.rolledNumber = rolledNumber;
    }

    public int getRolledNumber() {
        return rolledNumber;
    }
}
