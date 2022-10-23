package it.polimi.ingsw.gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopUp{


    private static int width = 250;
    private static int height = 150;






    public static void popUpScene(String message){

        final Stage popUpStage = new Stage(StageStyle.TRANSPARENT);
        popUpStage.initModality(Modality.APPLICATION_MODAL);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(20);
        gridPane.setHgap(20);
        gridPane.setPadding(new Insets(0, 0, 0, 0));
        try {
            Image background = new Image("clp_bg2.png", width, height, true, true);
            BackgroundImage backgroundStarting = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
            gridPane.setBackground(new Background(backgroundStarting));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        Scene createScene = new Scene(gridPane, width, height);

        //label suggesting what to press
        Label messageLabel = new Label(message);
        GridPane.setConstraints(messageLabel, 0, 0);

        HBox decisionHBox = new HBox();
        decisionHBox.setSpacing(20);

        final Rectangle okRectangle = new Rectangle();
        okRectangle.setWidth(90);
        okRectangle.setHeight(50);
        Image file1 = new Image("btn_blue.png");
        okRectangle.setFill(new ImagePattern(file1));

        decisionHBox.getChildren().addAll(okRectangle);
        GridPane.setConstraints(decisionHBox, 0, 2);




        okRectangle.setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        System.out.println("Create Game");
                        Image file = new Image("btn_blue_pressed.png");
                        okRectangle.setFill(new ImagePattern(file));
                        popUpStage.close();
                        //chiudi questo stage
                    }
                });





        gridPane.getChildren().addAll(messageLabel, decisionHBox);
        popUpStage.setScene(createScene);
        popUpStage.showAndWait();
    }
}
