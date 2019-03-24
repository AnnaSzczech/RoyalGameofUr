package com.anna.szczech.royalgameofur.gui;

import com.anna.szczech.royalgameofur.logic.Game;
import com.anna.szczech.royalgameofur.player.Player;
import com.anna.szczech.royalgameofur.player.PlayerEnum;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class GameBoard {

    public GameBoard(Pane pane){
        Image whitePawnImage = new Image("file:src/main/resources/white.png");
        Image blackPawnImage = new Image("file:src/main/resources/black.png");
        Board gameBoard = new Board(pane);
        Game game = new Game();
        Player computer = new Player(blackPawnImage, PlayerEnum.COMPUTER, setFieldFreeFromBattleForComputerPawns(), game);
        Player user = new Player(whitePawnImage, PlayerEnum.USER, setFieldFreeFromBattleForUserPawns(), game);
        game.addUserAndComputer(user, computer);
        gameBoard.createBoard(game);
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
