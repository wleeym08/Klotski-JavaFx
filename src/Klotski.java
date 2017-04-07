/**
 * Program Name: Klotski.java
 * Discussion:   Driver for the Klotski game
 * Written By:   Zhiying Li
 * Date:         2016/12/12
 */

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class Klotski extends Application {
    @Override
    public void start(Stage primaryStage) {
        GameBoard gameBoard = new GameBoard();
        BorderPane borderPane = new BorderPane();
        HBox buttonBar = new HBox();
        Button resetBt = new Button();
        Button undoBt = new Button();
        ImageView resetImageBig = new ImageView(
            new Image("bt_reset_big_out.jpg"));
        ImageView resetImage = new ImageView(
            new Image("bt_reset_out.jpg"));
        ImageView undoImageBig = new ImageView(
            new Image("bt_undo_big_out.jpg"));
        ImageView undoImage = new ImageView(
            new Image("bt_undo_out.jpg"));
        
        borderPane.setStyle(
            "-fx-background-image: url('background.png');");

        resetBt.setGraphic(resetImageBig);
        resetBt.setStyle("-fx-background-color: transparent;");
        resetBt.setOnMousePressed(
            e -> resetBt.setGraphic(resetImage));
        resetBt.setOnMouseReleased(
            e -> resetBt.setGraphic(resetImageBig));
        resetBt.setOnAction(e -> {
            gameBoard.reset();
            gameBoard.getInfoStack().clear();
        });
        
        undoBt.setGraphic(undoImageBig);
        undoBt.setStyle("-fx-background-color: transparent;");
        undoBt.setOnMousePressed(
            e -> undoBt.setGraphic(undoImage));
        undoBt.setOnMouseReleased(
            e -> undoBt.setGraphic(undoImageBig));
        undoBt.setOnAction(e -> gameBoard.undo());

        buttonBar.getChildren().addAll(resetBt, undoBt); 
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setPadding(new Insets(10, 10, 80, 10));
        buttonBar.setSpacing(100);

        borderPane.setCenter(gameBoard);
        borderPane.setBottom(buttonBar);

        primaryStage.setTitle("Klotski");
        primaryStage.setScene(new Scene(borderPane, 600, 800));
        primaryStage.setResizable(false);
        primaryStage.show();
        gameBoard.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/* PROGRAM OUTPUT
See documents
*/

/* COMMENTS
See documents
*/