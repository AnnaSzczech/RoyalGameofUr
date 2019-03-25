package com.anna.szczech.royalgameofur.gui;

import com.anna.szczech.royalgameofur.logic.Game;
import com.anna.szczech.royalgameofur.player.Player;
import com.anna.szczech.royalgameofur.player.PlayerEnum;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoard {

    public GameBoard(Pane pane){
        Image whitePawnImage = new Image("file:src/main/resources/white.png");
        Image blackPawnImage = new Image("file:src/main/resources/black.png");
        Board gameBoard = new Board(pane);
        Game game = new Game();
        List<Pawns> userPawns = createPawns(whitePawnImage, PlayerEnum.USER, game);
        List<Pawns> computerPawns = createPawns(blackPawnImage, PlayerEnum.COMPUTER, game);
        Player computer = new Player(setFieldFreeFromBattleForComputerPawns(), computerPawns);
        Player user = new Player(setFieldFreeFromBattleForUserPawns(), userPawns);
        game.addUserAndComputer(user, computer);
        gameBoard.createBoard(game);
    }

    private List<Pawns> createPawns(Image image, PlayerEnum playerEnum, Game game){
        int numberOfPawns = 7;
        List<Pawns> pawns = new ArrayList<>();
        for (int i = 0; i < numberOfPawns; i++){
            Pawns pawn = new Pawns(image, playerEnum, game);
            pawns.add(pawn);
        }
        return pawns;
    }

    private Map<LocalizationOnBoard, Integer> setFieldFreeFromBattleForComputerPawns() {
        Map<LocalizationOnBoard, Integer> fieldFreeFromBattle = new HashMap<>();
        fieldFreeFromBattle.put(new LocalizationOnBoard(720, 513), 1);
        fieldFreeFromBattle.put(new LocalizationOnBoard(620, 513), 2);
        fieldFreeFromBattle.put(new LocalizationOnBoard(523, 513), 3);
        fieldFreeFromBattle.put(new LocalizationOnBoard(423, 513), 4);
        fieldFreeFromBattle.put(new LocalizationOnBoard(1117, 513), 13);
        fieldFreeFromBattle.put(new LocalizationOnBoard(1018, 513), 14);
        return fieldFreeFromBattle;
    }

    private Map<LocalizationOnBoard, Integer> setFieldFreeFromBattleForUserPawns() {
        Map<LocalizationOnBoard, Integer> fieldFreeFromBattle = new HashMap<>();
        fieldFreeFromBattle.put(new LocalizationOnBoard(720, 315), 1);
        fieldFreeFromBattle.put(new LocalizationOnBoard(620, 315), 2);
        fieldFreeFromBattle.put(new LocalizationOnBoard(523, 315), 3);
        fieldFreeFromBattle.put(new LocalizationOnBoard(423, 315), 4);
        fieldFreeFromBattle.put(new LocalizationOnBoard(1117, 315), 13);
        fieldFreeFromBattle.put(new LocalizationOnBoard(1018, 315), 14);
        return fieldFreeFromBattle;
    }


}
