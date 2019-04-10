package com.anna.szczech.royalgameofur.gui;

import com.anna.szczech.royalgameofur.logic.Game;
import com.anna.szczech.royalgameofur.player.Player;
import com.anna.szczech.royalgameofur.player.PlayerEnum;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private Game game;

    public GameBoard(){
        game = new Game();
    }

    public void createBoard(Pane pane){
        Board gameBoard = new Board(pane);
        Player computer = createComputerPlayer();
        Player user = createUserPlayer();
        game.addUserAndComputer(user, computer);
        gameBoard.createBoard(game);
    }

    private Player createUserPlayer(){
        Image whitePawnImage = new Image("file:src/main/resources/white.png");
        List<Pawn> userPawns = createPawns(whitePawnImage, PlayerEnum.USER);
        return new Player(userPawns);
    }

    private Player createComputerPlayer(){
        Image blackPawnImage = new Image("file:src/main/resources/black.png");
        List<Pawn> computerPawns = createPawns(blackPawnImage, PlayerEnum.COMPUTER);
        return new Player(computerPawns);
    }

    private List<Pawn> createPawns(Image image, PlayerEnum playerEnum){
        int numberOfPawns = 7;
        List<Pawn> pawns = new ArrayList<>();
        for (int i = 0; i < numberOfPawns; i++){
            pawns.add(createPawn(image, playerEnum));
        }
        return pawns;
    }

    private Pawn createPawn(Image image, PlayerEnum playerEnum){
        Pawn pawn = new Pawn(image, playerEnum);
        pawn.setOnMouseClicked(event -> game.movePawn(pawn));
        return pawn;
    }
}
