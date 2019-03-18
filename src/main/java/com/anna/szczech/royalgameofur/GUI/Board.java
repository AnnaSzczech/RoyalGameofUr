package com.anna.szczech.royalgameofur.GUI;

import com.anna.szczech.royalgameofur.logic.ComputerRound;
import com.anna.szczech.royalgameofur.logic.PlayerRound;
import com.anna.szczech.royalgameofur.player.Computer;
import com.anna.szczech.royalgameofur.player.Player;
import com.anna.szczech.royalgameofur.player.User;
import com.anna.szczech.royalgameofur.result.Points;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Label;

import java.util.List;

public class Board {
    private Player computer;
    private Player user;
    private int move = 0;
    private boolean isUserTurn = true;
    private Points result = new Points();
    private Label resultLabel;
    public boolean isEndGame = false;
    public Pane pane;
    public Label rolledNumber;
    public Label messageLabel;
    public FlowPane boxWithPlayerPawns;
    public FlowPane boxWithComputerPawns;
    public boolean wasRolled = false;
    public Button newRound;

    public Board(Pane pane){
        this.pane = pane;
        createBoard();
    }

    private void createBoard(){
        computer = new Computer(this);
        user = new User(this);
        resultLabel = printResult();
        createMessageLabel();
        createStandButton();
        createRollButton();
    }

    public void run(){
        boxWithPlayerPawns = createBoxWithPawns(150, getUser().getPawns());
        boxWithComputerPawns = createBoxWithPawns(1350, getComputer().getPawns());
        pane.getChildren().add(getResultLabel());
    }

    public Label printResult(){
        Label resultLabel = new Label(String.valueOf(result));
        resultLabel.setLayoutX(500);
        resultLabel.setLayoutY(120);
        resultLabel.setFont(new Font(40));
        resultLabel.setTextFill(Color.web("#FFF"));
        return resultLabel;
    }

    public void rollNumberOnDice(){move = (int) (Math.random()*4 + 1);}

    public void createRollButton(){
        Button roll = new Button("ROLL DICE");
        roll.setLayoutX(650);
        roll.setLayoutY(660);
        roll.setScaleX(2);
        roll.setScaleY(2);
        rolledNumber = writeRolledNumberOnLabel();
        roll.setOnAction(event -> roll());
        pane.getChildren().add(rolledNumber);
        pane.getChildren().add(roll);
    }

    public void roll(){
        if (!wasRolled && !isEndGame) {
            rollNumberOnDice();
            rolledNumber.setText(String.valueOf(getMove()));
            wasRolled = true;
            if (isUserTurn) {
                createFakeRound();
            }
        }
    }

    private void createFakeRound(){
        PlayerRound playerRound = new PlayerRound(this, getUser().getPawns().get(0));
        if (!playerRound.isThereAnyPossibleMove()) {
            newRound.setVisible(true);
            messageLabel.setText("There is no move to make, click NEW ROUND");
        }
    }

    public Label writeRolledNumberOnLabel(){
        Label rolledNumber = new Label();
        rolledNumber.setLayoutX(790);
        rolledNumber.setLayoutY(650);
        rolledNumber.setFont(new Font(35));
        rolledNumber.setTextFill(Color.web("#FFF"));
        return rolledNumber;
    }

    private FlowPane createBoxWithPawns(double x, List<Pawns> pawns){
        FlowPane boxWithPawns = new FlowPane(Orientation.VERTICAL);
        boxWithPawns.setLayoutX(x);
        boxWithPawns.setLayoutY(150);
        boxWithPawns.setMinHeight(616);
        addPawnsToBox(boxWithPawns, pawns);
        return boxWithPawns;
    }

    private void addPawnsToBox(FlowPane box, List<Pawns> pawns){
        pawns.stream().forEach((n) -> box.getChildren().add(n));
        pane.getChildren().add(box);
    }

    private void createMessageLabel(){
        messageLabel = new Label();
        messageLabel.setLayoutX(600);
        messageLabel.setLayoutY(720);
        messageLabel.setFont(new Font(35));
        messageLabel.setTextFill(Color.web("#FFF"));
        pane.getChildren().add(messageLabel);
    }

    public int getMove() {
        return move;
    }

    public Player getComputer() {
        return computer;
    }

    public Player getUser() {
        return user;
    }

    public boolean isUserTurn() {
        return isUserTurn;
    }

    public void setUserTurn(boolean userTurn) {
        isUserTurn = userTurn;
    }

    public Label getResultLabel() {
        return resultLabel;
    }

    public Points getResult() {
        return result;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public void createStandButton(){
        newRound = new Button("NEW ROUND");
        newRound.setLayoutX(450);
        newRound.setLayoutY(660);
        newRound.setScaleX(2);
        newRound.setScaleY(2);
        newRound.setVisible(false);

        newRound.setOnAction(event -> {
            if (!isEndGame) {
                isUserTurn = !isUserTurn;
                newRound.setVisible(false);
                ComputerRound computerRound = new ComputerRound(this);
                computerRound.newRound();
            }
        });
        pane.getChildren().add(newRound);
    }
}
