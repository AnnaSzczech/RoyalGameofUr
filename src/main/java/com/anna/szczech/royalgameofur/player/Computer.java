package com.anna.szczech.royalgameofur.player;

import com.anna.szczech.royalgameofur.GUI.LocalizationOnBoard;
import com.anna.szczech.royalgameofur.GUI.Pawns;
import com.anna.szczech.royalgameofur.GUI.Board;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Computer implements Player {
    private Image image = new Image("file:src/main/resources/black.png");
    private List<Pawns> pawns = new ArrayList<>();
    private Map<LocalizationOnBoard, Integer> localizationOnBoard = new HashMap<>();


    public Computer(Board board) {
        pawns = createPawns(image, 7, board, this.getClass());
        setLocalizationOnBoard();
    }

    public void setLocalizationOnBoard(){
        localizationOnBoard.put(new LocalizationOnBoard(720, 513), 1);
        localizationOnBoard.put(new LocalizationOnBoard(620, 513), 2);
        localizationOnBoard.put(new LocalizationOnBoard(523, 513), 3);
        localizationOnBoard.put(new LocalizationOnBoard(423, 513), 4);
        localizationOnBoard.put(new LocalizationOnBoard(1117, 513), 13);
        localizationOnBoard.put(new LocalizationOnBoard(1018, 513), 14);

        Map<LocalizationOnBoard, Integer> battlefield = setBattlefield();
        battlefield.entrySet().stream().forEach(n -> localizationOnBoard.put(n.getKey(), n.getValue()));
    }

    @Override
    public Map<LocalizationOnBoard, Integer> getLocalizationOnBoard() {
        return localizationOnBoard;
    }

    public List<Pawns> getPawns() {
        return pawns;
    }
}
