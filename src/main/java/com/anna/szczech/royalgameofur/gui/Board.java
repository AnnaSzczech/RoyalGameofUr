package com.anna.szczech.royalgameofur.gui;

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
        resultLabel = createNewLabel(String.valueOf(result), 500, 120, 40);
        createMessageLabel();
        createNewRoundButton();
        createRollButton();
    }

    public void run(){
        boxWithPlayerPawns = createBoxWithPawns(150, getUser().getPawns());
        boxWithComputerPawns = createBoxWithPawns(1350, getComputer().getPawns());
        pane.getChildren().add(getResultLabel());
    }

    public void rollNumberOnDice(){move = (int) (Math.random()*4 + 1);}

    public void createRollButton(){
        Button roll = createNewButton("ROLL DICE", 650, 660);
        rolledNumber = createNewLabel("", 790, 650, 35);
        roll.setOnAction(event -> roll());
        pane.getChildren().add(rolledNumber);
        pane.getChildren().add(roll);
    }

    public void roll(){
        if (!wasRolled && !isEndGame) {
            rollNumberOnDice();
            rolledNumber.setText(String.valueOf(move));
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
        messageLabel = createNewLabel("", 600, 720, 35);
        pane.getChildren().add(messageLabel);
    }

    public void createNewRoundButton(){
        newRound = createNewButton("NEW ROUND", 450.0, 660.0);
        newRound.setVisible(false);
        newRound.setOnAction(event -> startNewRound());
        pane.getChildren().add(newRound);
    }

    private void startNewRound(){
        if (!isEndGame) {
            isUserTurn = !isUserTurn;
            newRound.setVisible(false);
            ComputerRound computerRound = new ComputerRound(this);
            computerRound.newRound();
        }
    }

    private Button createNewButton(String name, double x, double y){
        Button button = new Button(name);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setScaleX(2);
        button.setScaleY(2);
        return button;
    }

    private Label createNewLabel(String name, double x, double y, int fintSize){
        Label label = new Label(name);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setFont(new Font(fintSize));
        label.setTextFill(Color.web("#FFF"));
        return label;
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
}
