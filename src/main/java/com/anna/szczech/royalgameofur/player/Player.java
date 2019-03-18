package com.anna.szczech.royalgameofur.player;

import com.anna.szczech.royalgameofur.gui.LocalizationOnBoard;
import com.anna.szczech.royalgameofur.gui.Pawns;
import com.anna.szczech.royalgameofur.gui.Board;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Player {
    List<Pawns> getPawns();

    Map<LocalizationOnBoard, Integer> getLocalizationOnBoard();


    default List<Pawns> createPawns(Image image, int numberOfPawns, Board board, Object playerClass){
        List<Pawns> pawns = new ArrayList<>();
        for (int i = 0; i < numberOfPawns; i++){
            Pawns pawn = new Pawns(new ImageView(image), board, playerClass);
            pawns.add(pawn);
        }
        return pawns;
    }

    default Map<LocalizationOnBoard, Integer> setBattlefield(){
        Map<LocalizationOnBoard, Integer> battlefield = new HashMap<>();
        battlefield.put(new LocalizationOnBoard(423, 415), 5);
        battlefield.put(new LocalizationOnBoard(523, 415), 6);
        battlefield.put(new LocalizationOnBoard(620, 415), 7);
        battlefield.put(new LocalizationOnBoard(720, 415), 8);
        battlefield.put(new LocalizationOnBoard(820, 415), 9);
        battlefield.put(new LocalizationOnBoard(918, 415), 10);
        battlefield.put(new LocalizationOnBoard(1018, 415), 11);
        battlefield.put(new LocalizationOnBoard(1117, 415), 12);
        return battlefield;
    }

}
