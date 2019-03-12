package com.anna.szczech.royalgameofur.logic;

import com.anna.szczech.royalgameofur.GUI.LocalizationOnBoard;
import com.anna.szczech.royalgameofur.GUI.Pawns;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Player {
    private Image image = new Image("file:src/main/resources/white.png");
    private List<Pawns> pawns = new ArrayList<>();
    private Map<LocalizationOnBoard, Integer> localizationOnBoard = new HashMap<>();

    public User(Board board) {
        createPlayerPawns(board);
    }

    public void createPlayerPawns(Board board){
        int numberOfPawns = 7;
        double locationX = 150;
        pawns = createPawns(image, numberOfPawns, locationX, board);
        setLocalizationOnBoard();
    }

    public List<Pawns> getPawns() {
        return pawns;
    }

    public Image getImage() {
        return image;
    }

    public void setLocalizationOnBoard(){
        localizationOnBoard.put(new LocalizationOnBoard(720, 315), 1);
        localizationOnBoard.put(new LocalizationOnBoard(620, 315), 2);
        localizationOnBoard.put(new LocalizationOnBoard(523, 315), 3);
        localizationOnBoard.put(new LocalizationOnBoard(423, 315), 4);
        localizationOnBoard.put(new LocalizationOnBoard(1117, 315), 13);
        localizationOnBoard.put(new LocalizationOnBoard(1018, 315), 14);

        Map<LocalizationOnBoard, Integer> battlefield = setBattlefield();
        battlefield.entrySet().stream().forEach(n -> localizationOnBoard.put(n.getKey(), n.getValue()));
    }

    public Map<LocalizationOnBoard, Integer> getLocalizationOnBoard() {
        return localizationOnBoard;
    }
}
