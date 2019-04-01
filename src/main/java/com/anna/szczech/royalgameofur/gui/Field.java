package com.anna.szczech.royalgameofur.gui;

import com.anna.szczech.royalgameofur.player.PlayerEnum;

import java.util.Arrays;

public enum Field {
    FIELD_1_USER(720, 315, 1, PlayerEnum.USER),
    FIELD_2_USER(620, 315, 2, PlayerEnum.USER),
    FIELD_3_USER(523, 315, 3, PlayerEnum.USER),
    FIELD_4_USER(423, 315, 4, PlayerEnum.USER),
    FIELD_13_USER(1117, 315, 13, PlayerEnum.USER),
    FIELD_14_USER(1018, 315, 14, PlayerEnum.USER),

    FIELD_1_COMPUTER(720, 513, 1, PlayerEnum.COMPUTER),
    FIELD_2_COMPUTER(620, 513, 2, PlayerEnum.COMPUTER),
    FIELD_3_COMPUTER(523, 513, 3, PlayerEnum.COMPUTER),
    FIELD_4_COMPUTER(423, 513, 4, PlayerEnum.COMPUTER),
    FIELD_13_COMPUTER(1117, 513, 13, PlayerEnum.COMPUTER),
    FIELD_14_COMPUTER(1018, 513, 14, PlayerEnum.COMPUTER),

    FIELD_5(423, 415, 5, null),
    FIELD_6(523, 415, 6, null),
    FIELD_7(620, 415, 7, null),
    FIELD_8(720, 415, 8, null),
    FIELD_9(820, 415, 9, null),
    FIELD_10(918, 415, 10, null),
    FIELD_11(1018, 415, 11, null),
    FIELD_12(1117, 415, 12, null);

    private double x;
    private double y;
    private int number;
    private PlayerEnum playerEnum;

    Field(double x, double y, int number, PlayerEnum playerEnum) {
        this.x = x;
        this.y = y;
        this.number = number;
        this.playerEnum = playerEnum;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getNumber() {
        return number;
    }

    public PlayerEnum getPlayerEnum() {
        return playerEnum;
    }

    public static Field getFieldFor(int number, PlayerEnum player) {
        return Arrays.stream(values())
                .filter(f -> f.getNumber() == number)
                .filter(f -> f.getPlayerEnum() == player)
                .findFirst().get();
    }
}
