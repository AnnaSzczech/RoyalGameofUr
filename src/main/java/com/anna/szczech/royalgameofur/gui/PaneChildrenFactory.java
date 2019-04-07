package com.anna.szczech.royalgameofur.gui;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class PaneChildrenFactory {

    public Button createNewButton(String name, double x, double y){
        Button button = new Button(name);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setScaleX(2);
        button.setScaleY(2);
        return button;
    }

    public Label createNewLabel(String name, double x, double y, int fontSize){
        Label label = new Label(name);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setFont(new Font(fontSize));
        label.setTextFill(Color.web("#FFF"));
        return label;
    }

    public FlowPane createFlowPane(double x, double y, double minHeight){
        FlowPane boxWithPawns = new FlowPane(Orientation.VERTICAL);
        boxWithPawns.setLayoutX(x);
        boxWithPawns.setLayoutY(y);
        boxWithPawns.setMinHeight(minHeight);
        return boxWithPawns;
    }
}
