package com.anna.szczech.royalgameofur.player;

import com.anna.szczech.royalgameofur.gui.Board;
import com.anna.szczech.royalgameofur.gui.LocalizationOnBoard;
import com.anna.szczech.royalgameofur.gui.Pawns;
import com.anna.szczech.royalgameofur.gui.Roll;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private List<Pawns> pawns = new ArrayList<>();
    private Map<LocalizationOnBoard, Integer> localizationOnBoard = new HashMap<>();
    private int points = 0;


    public Player(Board board, Roll roll, Image image, PlayerEnum playerEnum, Map<LocalizationOnBoard, Integer> fieldFreeFromBattle) {
        pawns = createPawns(image, 7, board, playerEnum, roll);
        setLocalizationOnBoard(fieldFreeFromBattle);
    }

    private List<Pawns> createPawns(Image image, int numberOfPawns, Board board, PlayerEnum playerEnum, Roll roll){
        List<Pawns> pawns = new ArrayList<>();
        for (int i = 0; i < numberOfPawns; i++){
            Pawns pawn = new Pawns(image, board, playerEnum, roll);
            pawns.add(pawn);
        }
        return pawns;
    }

    private void setLocalizationOnBoard(Map<LocalizationOnBoard, Integer> fieldFreeFromBattle){
        localizationOnBoard.put(new LocalizationOnBoard(423, 415), 5);
        localizationOnBoard.put(new LocalizationOnBoard(523, 415), 6);
        localizationOnBoard.put(new LocalizationOnBoard(620, 415), 7);
        localizationOnBoard.put(new LocalizationOnBoard(720, 415), 8);
        localizationOnBoard.put(new LocalizationOnBoard(820, 415), 9);
        localizationOnBoard.put(new LocalizationOnBoard(918, 415), 10);
        localizationOnBoard.put(new LocalizationOnBoard(1018, 415), 11);
        localizationOnBoard.put(new LocalizationOnBoard(1117, 415), 12);
        fieldFreeFromBattle.entrySet().stream().forEach(n -> localizationOnBoard.put(n.getKey(), n.getValue()));
    }

    public Map<LocalizationOnBoard, Integer> getLocalizationOnBoard() {
        return localizationOnBoard;
    }

    public List<Pawns> getPawns() {
        return pawns;
    }

    public int getPoints() {
        return points;
    }

    public void addPoint() {
        this.points++;
    }
}
