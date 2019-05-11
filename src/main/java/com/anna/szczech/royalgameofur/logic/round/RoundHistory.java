package com.anna.szczech.royalgameofur.logic.round;

public class RoundHistory {
    private String text;

    public RoundHistory() {
        text = "XXXXXXXXXXXXXXXXXXXXXXX\n";
    }

    public String getText() {
        return text;
    }

    public void addText(String text) {
        this.text += text;
    }
}
