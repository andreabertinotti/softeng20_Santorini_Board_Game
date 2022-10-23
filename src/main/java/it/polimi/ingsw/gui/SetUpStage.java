package it.polimi.ingsw.gui;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SetUpStage{


    private static int width = 300;
    private static int height = 300;

    private static BackgroundImage backgroundStarting;





    static void setUpGameScene(String whatToDo){

        // StageStyle.TRANSPARENT

        final Stage gameCreationStage = new Stage();
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(20);
        gridPane.setHgap(20);
        gridPane.setPadding(new Insets(0, 0, 0, 0));

        try {
            Image background = new Image("clp_bg2.png", width, height, true, true);
            backgroundStarting = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
            gridPane.setBackground(new Background(backgroundStarting));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        Scene createScene = new Scene(gridPane, width, height);

        //label suggesting what to press
        //Label whatToDoLabel = new Label(whatToDo);
        //GridPane.setConstraints(whatToDoLabel, 0, 0);

        HBox decisionHBox = new HBox();
        decisionHBox.setSpacing(20);


        //bottone create game
        final ButtonCreator createGameBtnCreator = new ButtonCreator();
        Button createGameBtn = createGameBtnCreator.ButtonCreator(true,90,50, "btn_blue.png","btn_blue_pressed.png", "btn_small_gray.png","Create game", 10);
        createGameBtn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        createGameBtn.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent e)
            {
                System.out.println("Create Game");
                createGameBtnCreator.setPressed();
                gameCreationScene(gameCreationStage);

            }
        });

/*
        StackPane stackPaneForCreateGame = new StackPane();
        final Rectangle createGameRectangle = new Rectangle();
        createGameRectangle.setWidth(90);
        createGameRectangle.setHeight(50);
        Image file1 = new Image("btn_blue.png");
        createGameRectangle.setFill(new ImagePattern(file1));
        Text text = new Text("create game");
        text.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 15));
        text.setFill(Color.WHITE);
        ObservableList observableList = stackPaneForCreateGame.getChildren();
        observableList.addAll(createGameRectangle,text);

 */

        final ButtonCreator loginBtnCreator = new ButtonCreator();
        Button loginBtn = loginBtnCreator.ButtonCreator(true, 90, 50, "btn_blue.png","btn_blue_pressed.png", "btn_small_gray.png","Login", 10);
        loginBtn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        loginBtn.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent e)
            {
                System.out.println("Create Game");
                loginBtnCreator.setPressed();
                //loginScene(gameCreationStage);

            }
        });

/*
        StackPane stackPaneForLogin = new StackPane();
        final Rectangle logInRectangle = new Rectangle();
        logInRectangle.setWidth(90);
        logInRectangle.setHeight(50);
        Image file2 = new Image("btn_blue.png");
        logInRectangle.setFill(new ImagePattern(file2));
        Text text2 = new Text("login");
        ObservableList observableList2 = stackPaneForLogin.getChildren();
        observableList2.addAll(logInRectangle,text2);

*/



        decisionHBox.getChildren().addAll( loginBtn, createGameBtn);
        GridPane.setConstraints(decisionHBox, 0, 2);



/*
        stackPaneForLogin.setOnMouseClicked(
                new EventHandler<MouseEvent>(){
                    public void handle(MouseEvent e) {
                        System.out.println("login");
                        Image file = new Image("btn_blue_pressed.png");
                        logInRectangle.setFill(new ImagePattern(file));
                        loginScene(gameCreationStage);


                    }
                });

        stackPaneForCreateGame.setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        System.out.println("Create Game");
                        Image file = new Image("btn_blue_pressed.png");
                        createGameRectangle.setFill(new ImagePattern(file));
                        gameCreationScene(gameCreationStage);
                    }
                });

 */
        gridPane.getChildren().addAll(decisionHBox);
        gameCreationStage.setScene(createScene);
        gameCreationStage.showAndWait();




    }







    private static void gameCreationScene(Stage gameCreationStage) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(0, 0, 0, 0));

        final ButtonCreator twoPBtnCreator = new ButtonCreator();
        final ButtonCreator threePBtnCreator = new ButtonCreator();
        final ButtonCreator withDBtnCreator = new ButtonCreator();
        final ButtonCreator withoutDBtnCreator = new ButtonCreator();
        final ButtonCreator logBtnCreator = new ButtonCreator();


        try {
            Image background = new Image("clp_bg2.png", width, height, true, true);
            backgroundStarting = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
            gridPane.setBackground(new Background(backgroundStarting));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        Scene createScene = new Scene(gridPane, width, height);

        //nPlayer label
        //Label playerLabel = new Label("How many player: ");
        //GridPane.setConstraints(playerLabel, 0, 0);

        HBox nPlayer = new HBox();

        //bottone two players
        Button twoPBtn = twoPBtnCreator.ButtonCreator(true,90, 50, "btn_blue.png","btn_blue_pressed.png", "btn_small_gray.png","Two", 10);
        twoPBtn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        twoPBtn.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent e)
            {
                System.out.println("Two players");
                twoPBtnCreator.setPressed();
                threePBtnCreator.setUnpressed();
                if((withDBtnCreator.getStatus() || withoutDBtnCreator.getStatus()) && (twoPBtnCreator.getStatus() || threePBtnCreator.getStatus())) {
                    logBtnCreator.setEnabled(true);
                }

            }
        });

        //bottone three players
        Button threePBtn = threePBtnCreator.ButtonCreator(true,90, 50, "btn_blue.png","btn_blue_pressed.png", "btn_small_gray.png","Three", 10);
        threePBtn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        threePBtn.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent e)
            {
                System.out.println("Three players");
                threePBtnCreator.setPressed();
                twoPBtnCreator.setUnpressed();
                if((withDBtnCreator.getStatus() || withoutDBtnCreator.getStatus()) && (twoPBtnCreator.getStatus() || threePBtnCreator.getStatus())) {
                    logBtnCreator.setEnabled(true);
                }

            }
        });


/*
        Rectangle twoP = new Rectangle();
        twoP.setWidth(90);
        twoP.setHeight(50);
        Image file1 = new Image("btn_blue.png");
        twoP.setFill(new ImagePattern(file1));
        Rectangle threeP = new Rectangle();
        threeP.setWidth(90);
        threeP.setHeight(50);
        Image file2 = new Image("btn_blue.png");
        threeP.setFill(new ImagePattern(file2));

 */

        nPlayer.getChildren().addAll(twoPBtn, threePBtn);
        GridPane.setConstraints(nPlayer, 0, 1);

        //divinity label
        Label divinityLabel = new Label("With divinity?: ");
        GridPane.setConstraints(divinityLabel, 0, 2);

        HBox divinitySet = new HBox();
        divinitySet.setSpacing(10);

        //bottone with divinities
        Button withDBtn = withDBtnCreator.ButtonCreator(true,90, 50, "btn_blue.png","btn_blue_pressed.png", "btn_small_gray.png","With", 10);
        withDBtn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        withDBtn.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent e)
            {
                System.out.println("with divinities");
                withDBtnCreator.setPressed();
                withoutDBtnCreator.setUnpressed();
                if((withDBtnCreator.getStatus() || withoutDBtnCreator.getStatus()) && (twoPBtnCreator.getStatus() || threePBtnCreator.getStatus())) {
                    logBtnCreator.setEnabled(true);
                }

            }
        });

        //bottone without divinities
        Button withoutDBtn = withoutDBtnCreator.ButtonCreator(true,90, 50, "btn_blue.png","btn_blue_pressed.png", "btn_small_gray.png","Without", 10);
        withoutDBtn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        withoutDBtn.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent e)
            {
                System.out.println("without divinities");
                withoutDBtnCreator.setPressed();
                withDBtnCreator.setUnpressed();
                if((withDBtnCreator.getStatus() || withoutDBtnCreator.getStatus()) && (twoPBtnCreator.getStatus() || threePBtnCreator.getStatus())) {
                    logBtnCreator.setEnabled(true);
                }

            }
        });


        /*
        Rectangle withD = new Rectangle();
        withD.setWidth(90);
        withD.setHeight(50);
        Image file3 = new Image("btn_blue.png");
        withD.setFill(new ImagePattern(file3));
        Rectangle withoutD = new Rectangle();
        withoutD.setWidth(90);
        withoutD.setHeight(50);
        Image file4 = new Image("btn_blue.png");
        withoutD.setFill(new ImagePattern(file4));

         */

        divinitySet.getChildren().addAll(withDBtn, withoutDBtn);
        GridPane.setConstraints(divinitySet, 0, 3);


        //bottone log
        Button logBtn = logBtnCreator.ButtonCreator(false,190, 50, "btn_blue.png","btn_blue_pressed.png", "btn_small_gray.png","Login", 10);
        logBtn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        logBtn.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent e)
            {
                if(logBtnCreator.getEnabled()) {
                    System.out.println("create game");
                    logBtnCreator.setPressed();
                    int nPlayers = 0;               //valore non accettabile --> se non viene settato correttamente il model respinge il pacchetto
                    if (twoPBtnCreator.getStatus()) {
                        nPlayers = 2;
                    } else if (threePBtnCreator.getStatus()) {
                        nPlayers = 3;
                    }
                    int withDivinities = 2;         //valore non accettabile --> se non viene settato correttamente il model respinge il pacchetto
                    if (withDBtnCreator.getStatus()) {
                        withDivinities = 1;
                    } else if (withoutDBtnCreator.getStatus()) {
                        withDivinities = 0;
                    }

                    System.out.println("game parametes set.  number of players: " + nPlayers + "   with divinities: " + withDivinities);
                }

            }
        });



/*
        final Rectangle log = new Rectangle();
        log.setWidth(190);
        log.setHeight(50);
        Image file = new Image("btn_blue.png");
        log.setFill(new ImagePattern(file));
        GridPane.setConstraints(log, 0, 4);

        log.setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        System.out.println("Create Game");
                        Image file = new Image("btn_blue_pressed.png");
                        log.setFill(new ImagePattern(file));
                    }
                });

 */

        gridPane.getChildren().addAll( nPlayer, divinityLabel, divinitySet, logBtn);
        gameCreationStage.setScene(createScene);
        gameCreationStage.showAndWait();
    }







    private static void loginScene(Stage gameCreationStage) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(0, 0, 0, 0));

        final ButtonCreator logBtnCreator = new ButtonCreator();

        try {
            Image background = new Image("clp_bg2.png", width, height, true, true);
            backgroundStarting = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
            gridPane.setBackground(new Background(backgroundStarting));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


        Scene loginScene = new Scene(gridPane, width, height);

        //nickname label
        //Label nicknameLabel = new Label("Nickname: ");
        //GridPane.setConstraints(nicknameLabel, 0, 0);

        //nickname input
        final TextField nicknameTextField = new TextField();
        GridPane.setConstraints(nicknameTextField, 1, 0);

        //age label
        Label ageLabel = new Label("Age: ");
        GridPane.setConstraints(ageLabel, 0, 1);

        //age input
        final TextField ageTextField = new TextField();
        GridPane.setConstraints(ageTextField, 1, 1);


        //bottone log
        Button logBtn = logBtnCreator.ButtonCreator(true,190, 50, "btn_blue.png","btn_blue_pressed.png", "btn_small_gray.png","Login", 10);
        logBtn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        logBtn.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent e)
            {
                System.out.println("login");
                logBtnCreator.setPressed();
                String nickname = nicknameTextField.getText();
                int age = Integer.parseInt(ageTextField.getText());
                System.out.println(nickname + " has " + age + " years old");

            }
        });

/*
        final Rectangle log = new Rectangle();
        log.setWidth(190);
        log.setHeight(50);
        Image file = new Image("btn_blue.png");
        log.setFill(new ImagePattern(file));
        GridPane.setConstraints(log, 1, 2);

        log.setOnMouseClicked(
                new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent e)
                    {
                        System.out.println("Login");
                        String nickname = nicknameTextField.getText();
                        int age = Integer.parseInt(ageTextField.getText());
                        System.out.println(nickname + " has " + age + " years old");
                        Image file = new Image("btn_blue_pressed.png");
                        log.setFill(new ImagePattern(file));
                    }
                });

 */

        gridPane.getChildren().addAll( nicknameTextField, ageLabel, ageTextField, logBtn);
        gameCreationStage.setScene(loginScene);
        gameCreationStage.show();
    }




}
