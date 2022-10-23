package it.polimi.ingsw.gui;

import it.polimi.ingsw.Client.NetworkHandeler.UserInterface;
import it.polimi.ingsw.Client.View.View;
import it.polimi.ingsw.shared.Colour;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * JavaFX App
 */
public class GUI extends Application implements UserInterface {

    private static int width = 1000;
    private static int height = 550;
    public static Stage openingStage;
    private static View view;

    private BackgroundImage backgroundStarting;
    private BackgroundImage backgroundGaming;


    ArrayList<WorkerSprite> worker = new ArrayList<>();

    //up left x:300 y:82
    private int up = 82;
    private int left = 300;
    //down right x:700 y:474
    private int down = 482;
    private int right = 700;

    private Stage window;
    private Stage primary;

    private Scene primaryScene;

    GraphicsContext gc;

    //private ArrayList<ButtonCustom> buttonCustoms = new ArrayList<>();
    private ArrayList<ButtonCreator> buttonCreators = new ArrayList<>();
    private ArrayList<WorkerSprite> workers = new ArrayList<>();
    boolean moveRequest = false;
    boolean buildRequest = false;


    public static void launchGui(View currentView) {
        view = currentView;
        launch();
    }





    @Override
    public void start(Stage primary) {
        view.setUserInterface(this);
        openingStage = primary;
        connectionScene();

    }



    private void connectionScene(){

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(0, 0, 0, 0));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);
        Scene connectScene = new Scene(grid, width, height);
        openingStage.setScene(connectScene);
        openingStage.setTitle("Santorini");

        try {
            Image background = new Image("title_water.png", openingStage.getWidth(), openingStage.getHeight(), true, true);
            backgroundStarting = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
            grid.setBackground(new Background(backgroundStarting));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

/*
        Rectangle btnPLay = new Rectangle();
        btnPLay.setWidth(height/2);
        btnPLay.setHeight(height/2);
        Image play = new Image("button-play-normal.png");
        btnPLay.setFill(new ImagePattern(play));

        btnPLay.setOnMouseClicked(
                new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent e)
                    {
                        System.out.println("Connect");
                        SetUpStage.setUpGameScene("whatToDo");
                        //PopUp.popUpScene("messaggio");

                    }
                });

 */
        final ButtonCreator playBtnCreator = new ButtonCreator();
        Button playBtn = playBtnCreator.ButtonCreator(true,height/2, height/2,"button-play-normal.png","button-play-normal.png","button-play-normal.png","",0);
        playBtn.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        playBtn.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent e)
            {
                System.out.println("Connect");

                playBtnCreator.setPressed();

                view.connectToServer();


            }
        });

        grid.getChildren().add(playBtn);
        openingStage.show();
    }


    private void gameBoard() {
        GridPane board = new GridPane();
        board.setAlignment(Pos.CENTER);
        board.setVgap(10);
        board.setHgap(10);
        board.setPadding(new Insets(0, 0, 0, 0));
        primaryScene = new Scene(board, width, height);

        try {
            Image background = new Image("SantoriniBoard.png", primary.getWidth(), primary.getHeight(), true, true);
            backgroundGaming = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
            board.setBackground(new Background(backgroundGaming));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        Canvas canvas = new Canvas(right, down);
        gc = canvas.getGraphicsContext2D();
        board.getChildren().add(canvas);

        GridPane buttons = new GridPane();
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        buttons.setVgap(10);
        buttons.setHgap(10);
        buttons.setPadding(new Insets(5 * height / 8, 0, 0, 4 * width / 5));
        board.getChildren().add(buttons);

        ButtonCreator moveBtnCreator = new ButtonCreator();
        ButtonCreator buildBtnCreator = new ButtonCreator();
        ButtonCreator endTurnBtnCreator = new ButtonCreator();


        Button moveBtn = moveBtnCreator.ButtonCreator(false, 190, 50, "btn_blue.png","btn_blue_pressed.png", "btn_small_gray.png", "Move", 12);
        moveBtnCreator.setUtility("move");
        buttonCreators.add(moveBtnCreator);
        GridPane.setConstraints(moveBtn, 0, 0);
        buttons.getChildren().add(moveBtn);

        Button buildBtn = buildBtnCreator.ButtonCreator(false, 190, 50, "btn_blue.png","btn_blue_pressed.png", "btn_small_gray.png", "Build", 12);
        buildBtnCreator.setUtility("build");
        buttonCreators.add(buildBtnCreator);
        GridPane.setConstraints(buildBtn, 0, 1);
        buttons.getChildren().add(buildBtn);

        Button endTurnBtn = endTurnBtnCreator.ButtonCreator(false, 190, 50, "btn_blue.png","btn_blue_pressed.png", "btn_small_gray.png", "End turn", 12);
        endTurnBtnCreator.setUtility("endTurn");
        buttonCreators.add(endTurnBtnCreator);
        GridPane.setConstraints(endTurnBtn, 0, 2);
        buttons.getChildren().add(endTurnBtn);


/*
        ButtonCustom btnMove = new ButtonCustom("btn_small_gray.png", "move");
        btnMove.setUsable(false);
        buttonCustoms.add(btnMove);
        GridPane.setConstraints(btnMove, 0, 0);
        buttons.getChildren().add(btnMove);

        ButtonCustom btnBuild = new ButtonCustom("btn_small_gray.png", "build");
        btnBuild.setUsable(false);
        buttonCustoms.add(btnBuild);
        GridPane.setConstraints(btnBuild, 0, 1);
        buttons.getChildren().add(btnBuild);

        ButtonCustom btnEndTurn = new ButtonCustom("btn_small_gray.png", "endTurn");
        btnEndTurn.setUsable(false);
        buttonCustoms.add(btnEndTurn);
        GridPane.setConstraints(btnEndTurn, 0, 2);
        buttons.getChildren().add(btnEndTurn);

 */

        primaryScene.setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        double x = (e.getY() - up) / ((down - up) / 5);
                        int row = (int) x;
                        double y = (e.getX() - left) / ((right - left) / 5);
                        int column = (int) y;
                        if (y >= 0 && y <= 5 && x >= 0 && x <= 5) {
                            if (moveRequest) {
                                //manda un pacchetto di move con il worker
                            } else if (buildRequest) {
                                //chiedi se vuole una dome
                                //manda un pacchheto con le coordinate di build
                            }
                            System.out.println(row);
                            System.out.println(column);
                            //invia i dati della posizione selezionata
                        }
                    }
                });

        moveBtn.setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        if (moveBtnCreator.getEnabled()) {
                            moveRequest = true;
                            System.out.println("move");
                            moveBtnCreator.setPressed();
                            //invia i dati della scelta
                        }
                    }
                });

        buildBtn.setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        if (buildBtnCreator.getEnabled()) {
                            buildRequest = true;
                            System.out.println("build");
                            buildBtnCreator.setPressed();
                            //invia i dati della scelta
                        }
                    }
                });

        endTurnBtn.setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        if (endTurnBtnCreator.getEnabled()) {
                            System.out.println("end");
                            endTurnBtnCreator.setPressed();
                            //invia i dati della scelta
                        }
                    }
                });

        for (WorkerSprite worker : workers) {
            worker.setOnMouseClicked(
                    new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent e) {
                            System.out.println("Select worker");
                            //invia i dati di select worker
                        }
                    });
        }

        window.setScene(primaryScene);
        window.show();
    }


    private void possibilities(ArrayList<Boolean> possibilities) {

        for (ButtonCreator buttonCreator : buttonCreators) {
            if (buttonCreator.getUtility().equals("move")) {
                if (possibilities.get(0)) {
                    buttonCreator.setEnabled(true);
                } else {
                    buttonCreator.setEnabled(false);
                }
            } else if (buttonCreator.getUtility().equals("build")) {
                if (possibilities.get(1)) {
                    buttonCreator.setEnabled(true);
                } else {
                    buttonCreator.setEnabled(false);
                }
            } else if (buttonCreator.getUtility().equals("endTurn")) {
                if (possibilities.get(2)) {
                    buttonCreator.setEnabled(true);
                } else {
                    buttonCreator.setEnabled(false);
                }
            }
        }


        /*
        for (ButtonCustom button : buttonCustoms) {
            if (button.getUtility().equals("move")) {
                if (possibilities.get(0)) {
                    button.setUsable(true);
                    button.changeImageButton("btn_blue.png");
                } else {
                    button.setUsable(false);
                    button.changeImageButton("btn_small_gray.png");
                }
            } else if (button.getUtility().equals("build")) {
                if (possibilities.get(1)) {
                    button.setUsable(true);
                    button.changeImageButton("btn_blue.png");
                } else {
                    button.setUsable(false);
                    button.changeImageButton("btn_small_gray.png");
                }
            } else if (button.getUtility().equals("endTurn")) {
                if (possibilities.get(2)) {
                    button.setUsable(true);
                    button.changeImageButton("btn_blue.png");
                } else {
                    button.setUsable(false);
                    button.changeImageButton("btn_small_gray.png");
                }
            }
        }

         */
        window.setScene(primaryScene);
        window.show();
    }

    private void drawBuilding(int row, int column, int level, boolean buildDome) {

        int coordX = (left + ((right-left)/5)*column);
        int coordY = (up + ((down-up)/5)*row);

        if (buildDome) {
            //costruisci una dome in posizione coordX e coordY
            Image dome = new Image("dome.png");
            gc.drawImage(dome, coordX+15, coordY-20, 40,40);
        } else {
            //costruisci blocco di dimensioni 1,2,3 in posizione coordX e coordY
            Image build = new Image("build.png");
            if (level == 1)
                gc.drawImage(build, coordX, coordY-35, 70,70);
            else if (level == 2)
                gc.drawImage(build, coordX+5, coordY-30, 60,60);
            else if (level == 3)
                gc.drawImage(build, coordX+10, coordY-25, 50,50);
        }
    }

    private void drawPossibleCell(ArrayList<Integer> possibleCell) {
        for (int i=0; i<possibleCell.size(); i=i+2) {

            int row = possibleCell.get(i);
            int column = possibleCell.get(i+1);

            int coordX = (left + ((right - left)/5) * column);
            int coordY = (up + ((down - up)/5) * row);

            //costruisci una dome in posizione coordX e coordY
            Image select = new Image("cellSelection.png");
            gc.drawImage(select, coordX-5, coordY-40, 80,80);
        }
    }



    private void drawNewWorker(int ID, Color color) {
        WorkerSprite worker = new WorkerSprite(ID, color);
        workers.add(worker);


    }




    private void drawMove(int row, int column, int ID, Color color) {

        int coordX = (left + ((right-left)/5)*column);
        int coordY = (up + ((down-up)/5)*row);

        for (WorkerSprite worker : workers) {
            if (worker.getID() == ID && worker.getColor().equals(color)) {
                worker.setCoordX(row);
                worker.setCoordY(column);
                //aggiorna posizione
                //gc.drawImage(worker.getImage(), coordX+15, coordY-20, 40,40);
            }
        }
    }



















    public void showBeginningOfTurn(String popUpMessage) {

    }

    public void showBuild(int x, int y, int level, boolean hasDome) {

    }

    public void showChallenger(String nickname, ArrayList<String> deck) {

    }

    public void showDeck(ArrayList<String> deck) {

    }

    public void showGameAttributes(int nPlayer, boolean withDivinities, int boardDimension, ArrayList<String> opponents) {

    }

    public void showInvalidAction(String whatToFix) {

    }

    public void showLose(String nickname) {

    }

    public void showMove(Colour colour, int ID, int coordX, int coordY) {

    }

    public void showPlayerAttribute(String nickname, int age, Colour workerColour, String divinity) {

    }

    public void showPlayerDecision(ArrayList<Integer> validPos) {

    }

    public void showTurn(String nickname, int age, String whatToDo, Boolean yourTurn) {

    }

    public void showWin(String nickname, Boolean winner) {

    }

    public void showWorkerSelection(ArrayList<Boolean> possibilities) {

    }

    public void showPopUp(String message) {

        final String messageFromServer = message;
        if(message.equals("CREATE A GAME")){
            System.out.println("OKOKOK");
            Platform.runLater(new Runnable() {

                @Override public void run() {
                    SetUpStage.setUpGameScene(messageFromServer);
                }
            });

            //Platform.runLater( () -> SetUpStage.setUpGameScene(message) );
            //SetUpStage.setUpGameScene(message);

        }
        
    }

    @Override
    public void run() {

    }







    /*














    private void gameBoard() {
        GridPane board = new GridPane();
        board.setAlignment(Pos.CENTER);
        board.setVgap(10);
        board.setHgap(10);
        board.setPadding(new Insets(0, 0, 0, 0));
        Scene playScene = new Scene(board, width, height);

        try {
            Image background = new Image("SantoriniBoard.png", openingStage.getWidth(), openingStage.getHeight(), true, true);
            backgroundGaming = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
            board.setBackground(new Background(backgroundGaming));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        TilePane pos = new TilePane();
        pos.setAlignment(Pos.CENTER);
        pos.setPrefColumns(5);
        pos.setPrefRows(5);
        pos.setPrefTileWidth(40);
        pos.setPrefTileHeight(40);

        Image img = new Image("Apollo");
        ImageView imgV = new ImageView(img);
        pos.getChildren().add(imgV);
        pos.getChildren().add(imgV);
        pos.getChildren().add(imgV);
        pos.getChildren().add(imgV);

        board.getChildren().add(pos);
        openingStage.setScene(playScene);
        openingStage.show();
    }



    private void possibilities() {
        GridPane playGrid = new GridPane();
        playGrid.setAlignment(Pos.CENTER);
        playGrid.setVgap(10);
        playGrid.setHgap(10);
        playGrid.setPadding(new Insets(0, 0, 0, 0));
        playGrid.setBackground(new Background(backgroundGaming));
        Scene playScene = new Scene(playGrid, width, height);

        GridPane buttons = new GridPane();
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        buttons.setVgap(10);
        buttons.setHgap(10);
        buttons.setPadding(new Insets(height/2, 0, 0, 3*width/4));
        playGrid.getChildren().add(buttons);

        Image file = new Image("btn_blue.png");

        Rectangle moveButton = new Rectangle();
        moveButton.setWidth(190);
        moveButton.setHeight(50);
        moveButton.setFill(new ImagePattern(file));
        GridPane.setConstraints(moveButton, 0, 0);

        moveButton.setOnMouseClicked(
                new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent e)
                    {
                        System.out.println("move");
                        Image file = new Image("btn_blue_pressed.png");
                        moveButton.setFill(new ImagePattern(file));
                    }
                });

        Rectangle buildButton = new Rectangle();
        buildButton.setWidth(190);
        buildButton.setHeight(50);
        buildButton.setFill(new ImagePattern(file));
        GridPane.setConstraints(buildButton, 0, 1);

        buildButton.setOnMouseClicked(
                new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent e)
                    {
                        System.out.println("build");
                        Image file = new Image("btn_blue_pressed.png");
                        buildButton.setFill(new ImagePattern(file));
                    }
                });

        Rectangle endTurnButton = new Rectangle();
        endTurnButton.setWidth(190);
        endTurnButton.setHeight(50);
        endTurnButton.setFill(new ImagePattern(file));
        GridPane.setConstraints(endTurnButton, 0, 2);

        endTurnButton.setOnMouseClicked(
                new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent e)
                    {
                        System.out.println("end");
                        Image file = new Image("btn_blue_pressed.png");
                        endTurnButton.setFill(new ImagePattern(file));
                    }
                });

        buttons.getChildren().addAll(moveButton, buildButton, endTurnButton);

        openingStage.setScene(playScene);
        openingStage.show();
    }





 */


    /*
    private void drawBoard() { //prima cella 300 asse x
        Image panel = new Image("bg_panelEdgeLeft.png");
        gc.drawImage(panel, 0, 0, width/5, height);

        Image board = new Image("SantoriniBoard.png");
        gc.drawImage(board, width/5, 0, 4*width/5, height);

        //Image godFrame = new Image("gameGodFrame.png");
        //gc.drawImage(godFrame,width/5,height/2,width/6,height/2);

        Image btn = new Image("btn_small_gray.png");

        gc.drawImage(btn,5*width/6,5*height/8,180,50);
        gc.drawImage(btn,5*width/6,6*height/8,180,50);
        gc.drawImage(btn,5*width/6,7*height/8,180,50);

        theStage.show();
    } */


/*
    public void drawPossibilities(ArrayList<Boolean> possibilities) {

        ArrayList<Buttons> buttons = new ArrayList<>();
        buttons.add(new Buttons("move",760+panelLeftDimension, 370));
        buttons.add(new Buttons("build",760+panelLeftDimension, 440));
        buttons.add(new Buttons("endTurn",760+panelLeftDimension, 510));
        Buttons pressed = null;

        theScene.setOnMouseClicked(
                new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent e)
                    {
                        while (pressed == null) {
                            for (Buttons button : buttons)
                                if (buttons.containsCoord(e.getX(), e.getY()))
                                    pressed = buttons
                        }
                    }
                });

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                if (possibilities.get(0)) {
                    if (input.contains("M")) {
                        gc.drawImage(btnMove.buttonPressed(), 760 + panelLeftDimension, 370, 190, 50);
                        //è stato cliccato quindi rimetti tutto grigio perchè ora dovrà fare altro
                        //gc.drawImage(btnMove.buttonNull(), 760 + panelLeftDimension, 370, 190, 50);
                        //gc.drawImage(btnBuild.buttonNull(), 760 + panelLeftDimension, 440, 190, 50);
                        //gc.drawImage(btnEnd.buttonNull(), 760 + panelLeftDimension, 510, 190, 50);
                    } else gc.drawImage(btnMove.buttonReleased(), 760 + panelLeftDimension, 370, 190, 50);
                } else gc.drawImage(btnMove.buttonNull(), 760 + panelLeftDimension, 370, 190, 50);

                if (possibilities.get(1)) {
                    if (input.contains("B")) {
                        gc.drawImage(btnBuild.buttonPressed(), 760 + panelLeftDimension, 440, 190, 50);
                        //è stato cliccato quindi rimetti tutto grigio perchè ora dovrà fare altro
                        //gc.drawImage(btnMove.buttonNull(), 760 + panelLeftDimension, 370, 190, 50);
                        //gc.drawImage(btnBuild.buttonNull(), 760 + panelLeftDimension, 440, 190, 50);
                        //gc.drawImage(btnEnd.buttonNull(), 760 + panelLeftDimension, 510, 190, 50);
                    } else gc.drawImage(btnBuild.buttonReleased(), 760 + panelLeftDimension, 440, 190, 50);
                } else gc.drawImage(btnBuild.buttonNull(),760+ panelLeftDimension,440,190,50);

                if (possibilities.get(2)) {
                    if (input.contains("E")) {
                        gc.drawImage(btnEnd.buttonPressed(), 760 + panelLeftDimension, 510, 190, 50);
                        //è stato cliccato quindi rimetti tutto grigio perchè ora dovrà fare altro
                        //gc.drawImage(btnMove.buttonNull(), 760 + panelLeftDimension, 370, 190, 50);
                        //gc.drawImage(btnBuild.buttonNull(), 760 + panelLeftDimension, 440, 190, 50);
                        //gc.drawImage(btnEnd.buttonNull(), 760 + panelLeftDimension, 510, 190, 50);
                    } else gc.drawImage(btnEnd.buttonReleased(), 760 + panelLeftDimension, 510, 190, 50);
                } else gc.drawImage(btnEnd.buttonNull(),760+ panelLeftDimension,510,190,50);
            }
        }.start();

        theStage.show();
    }

 */
/*
    public void drawInvalidAcion() {

        Image popUp = new Image("clp_bg2.png");
        gc.drawImage(popUp,panelLeftDimension+300,150,370,300);

        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
        gc.setFont( theFont );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(1);

        Image warning = new Image("WarningSign.png");
        gc.drawImage(warning,panelLeftDimension+360, 250, 50, 50);

        String text = "Invalid Action";
        gc.fillText(text, panelLeftDimension+430, 285);
        gc.strokeText(text, panelLeftDimension+430, 285);

        theStage.show();
    }


/*


    /*
    private void drawCardOption(ArrayList<String> cards) {
        //ArrayList<String> cards = new ArrayList<>(Arrays.asList("Apollo", "Artemis", "Athena"));
        //drawCardOption(cards);
        int i=0;

        for (String card : cards) {
            String nameCard = card + ".png";
            Image img = new Image(nameCard);
            gc.drawImage(img, (280+(i*(width-400)/cards.size())), height/2-100, 120, 200);
            i++;
        }
    } */

}