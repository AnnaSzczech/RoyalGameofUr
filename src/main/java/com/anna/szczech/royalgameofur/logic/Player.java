package com.anna.szczech.royalgameofur.logic;

import com.anna.szczech.royalgameofur.GUI.LocalizationOnBoard;
import com.anna.szczech.royalgameofur.GUI.Pawns;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Player {
    Image getImage();
    List<Pawns> getPawns();

    void createPlayerPawns(Board board);
    Map<LocalizationOnBoard, Integer> getLocalizationOnBoard();

    default List<Pawns> createPawns(Image image, int numberOfPawns, double locationX, Board board){
        List<Pawns> pawns = new ArrayList<>();
        double locationY = 110;
        for (int i = 0; i < numberOfPawns; i++){
            Pawns pawn = new Pawns(new ImageView(image), board);
            pawn.setLayoutX(locationX);
            pawn.setLayoutY(locationY);
            pawns.add(pawn);
            locationY += 100;
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
