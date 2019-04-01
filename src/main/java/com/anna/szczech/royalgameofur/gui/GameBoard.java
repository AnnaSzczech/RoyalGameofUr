package com.anna.szczech.royalgameofur.gui;

import com.anna.szczech.royalgameofur.logic.Game;
import com.anna.szczech.royalgameofur.player.Player;
import com.anna.szczech.royalgameofur.player.PlayerEnum;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    public GameBoard(Pane pane){
        Image whitePawnImage = new Image("file:src/main/resources/white.png");
        Image blackPawnImage = new Image("file:src/main/resources/black.png");
        Board gameBoard = new Board(pane);
        Game game = new Game();
        List<Pawns> userPawns = createPawns(whitePawnImage, PlayerEnum.USER, game);
        List<Pawns> computerPawns = createPawns(blackPawnImage, PlayerEnum.COMPUTER, game);
        Player computer = new Player(computerPawns);
        Player user = new Player(userPawns);

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
}
