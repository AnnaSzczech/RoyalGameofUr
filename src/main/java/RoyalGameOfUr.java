import com.anna.szczech.royalgameofur.logic.Board;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RoyalGameOfUr extends Application {
    private Image imageBackground = new Image("file:src/main/resources/Background.jpg");


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Board board = new Board();
        Pane pane = new Pane();

        Button roll = createRollButton();
        Label rolledNumber = writeRolledNumber();

        roll.setOnAction(event -> {
            board.rollNumberOnDice();
            rolledNumber.setText(String.valueOf(board.getMove()));
        });
        pane.getChildren().add(rolledNumber);
        pane.getChildren().add(roll);

        board.getUserPawns().getPawns().stream().forEach((n) -> pane.getChildren().add(n));
        board.getComputerPawns().getPawns().stream().forEach((n) -> pane.getChildren().add(n));
        pane.getChildren().add(board.getResultLabel());

        pane.setBackground(createBackground());
        Scene scene = new Scene(pane, 1600, 900, Color.BLACK);


        primaryStage.setTitle("Royal Game Of Ur");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();


    }

    public Button createRollButton(){
        Button roll = new Button("ROLL DICE");
        roll.setLayoutX(650);
        roll.setLayoutY(730);
        roll.setScaleX(2);
        roll.setScaleY(2);
        return roll;
    }

    public Label writeRolledNumber(){
        Label rolledNumber = new Label();
        rolledNumber.setLayoutX(790);
        rolledNumber.setLayoutY(720);
        rolledNumber.setFont(new Font(35));
        rolledNumber.setTextFill(Color.web("#FFF"));
        return rolledNumber;
    }

    private Background createBackground(){
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, false, false, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        return new Background(backgroundImage);
    }
}
