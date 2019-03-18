package com.anna.szczech.royalgameofur.player;

import com.anna.szczech.royalgameofur.gui.LocalizationOnBoard;
import com.anna.szczech.royalgameofur.gui.Pawns;
import com.anna.szczech.royalgameofur.gui.Board;
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
        pawns = createPawns(image, 7, board, this.getClass());
        setLocalizationOnBoard();
    }

    public List<Pawns> getPawns() {
        return pawns;
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
