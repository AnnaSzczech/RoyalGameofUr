package com.anna.szczech.royalgameofur.gui;

import com.anna.szczech.royalgameofur.logic.ComputerRound;
import com.anna.szczech.royalgameofur.logic.PlayerRound;
import com.anna.szczech.royalgameofur.player.Player;
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
    private boolean isUserTurn = true;
    private Label resultLabel;
    public Button newRoundButton;
    public boolean isGameEnded = false;
    public Pane pane;
    public Label rolledNumberLabel;
    public Label messageLabel;
    public FlowPane boxWithPlayerPawns;
    public FlowPane boxWithComputerPawns;
    private Roll roll;

    public Board(Pane pane){
        this.pane = pane;
    }

    public void createBoard(Player computer, Player user, Roll roll){
        this.computer = computer;
        this.user = user;
        this.roll = roll;
        resultLabel = createNewLabel(String.valueOf(resultForAllPlayers()), 500, 120, 40);
        createMessageLabel();
        createNewRoundButton();
        createRollButton();
        createBoxesWithPlayersPawns();
        pane.getChildren().add(getResultLabel());
    }

    public String resultForAllPlayers(){
        return "USER POINTS : COMPUTER POINTS\n                   "
                + user.getPoints() + "  :  " + computer.getPoints();
    }

    private void createRollButton(){
        Button rollButton = createNewButton("ROLL DICE", 650, 660);
        rolledNumberLabel = createNewLabel("", 790, 650, 35);
        rollButton.setOnAction(event -> {
            int oldRolledNumber = roll.getRolledNumber();
            roll.diceRoll(isGameEnded, isUserTurn);
            rolledNumberLabel.setText(String.valueOf(roll.getRolledNumber()));
            if (oldRolledNumber != roll.getRolledNumber()) {
                createFakeRound();
            }
        });
        pane.getChildren().add(rolledNumberLabel);
        pane.getChildren().add(rollButton);
    }

    private void createFakeRound(){
        PlayerRound playerRound = new PlayerRound(this, getUser().getPawns().get(0), roll);
        if (!playerRound.isThereAnyPossibleMove()) {
            newRoundButton.setVisible(true);
            messageLabel.setText("There is no move to make, click NEW ROUND");
        }
    }

    private void createBoxesWithPlayersPawns(){
        boxWithPlayerPawns = createBoxWithPawns(150, getUser().getPawns());
        boxWithComputerPawns = createBoxWithPawns(1350, getComputer().getPawns());
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

    private void createNewRoundButton(){
        newRoundButton = createNewButton("NEW ROUND", 450.0, 660.0);
        newRoundButton.setVisible(false);
        newRoundButton.setOnAction(event -> startNewRound());
        pane.getChildren().add(newRoundButton);
    }

    private void startNewRound(){
        if (!isGameEnded) {
            isUserTurn = !isUserTurn;
            newRoundButton.setVisible(false);
            ComputerRound computerRound = new ComputerRound(this, roll);
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

    private Label createNewLabel(String name, double x, double y, int fontSize){
        Label label = new Label(name);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setFont(new Font(fontSize));
        label.setTextFill(Color.web("#FFF"));
        return label;
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
}
