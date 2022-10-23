package it.polimi.ingsw.Client.View;

import it.polimi.ingsw.Client.NetworkHandeler.EchoClient;
import it.polimi.ingsw.Client.NetworkHandeler.Cli;
import it.polimi.ingsw.Client.NetworkHandeler.UserInterface;
import it.polimi.ingsw.gui.GUI;
import it.polimi.ingsw.shared.Colour;
import it.polimi.ingsw.shared.EventRecivedFromServer.Request.*;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static it.polimi.ingsw.gui.GUI.launchGui;


public class View {

    private EchoClient client;
    private String nickname;
    private int nPlayer;
    private Colour workerColour;
    private String hostName;
    private int portNumber;
    private UserInterface userInterface;
    private int choosenUI;
    private boolean connectionActive;




    public View(String hostName, int portNumber){

        this.hostName = hostName;
        this.portNumber = portNumber;
        ExecutorService executor = Executors.newCachedThreadPool();

        chooseUI();

        if(choosenUI == 1){
            launchGui(this);


        }else {
            userInterface = new Cli(this);
            executor.submit(userInterface);
        }

    }

    public int chooseUI(){
        System.out.print("Digita 0 per avviare CLI, 1 per GUI: ");
        boolean flag = false;

        do {
            Scanner in = new Scanner(System.in);
            try {
                choosenUI = in.nextInt();
                if (!(choosenUI < 0 || choosenUI > 1))
                    flag = true;
            } catch (InputMismatchException ex) {
                in.next();
            }
        } while (!flag);

        return choosenUI;
    }

    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setConnectionActive(boolean connectionActive) {
        this.connectionActive = connectionActive;
    }

    public boolean isConnectionActive() {
        return connectionActive;
    }

    public int getnPlayer() {
        return nPlayer;
    }

    public void connectToServer(){

        System.out.println("connecting...");

        ExecutorService executor = Executors.newCachedThreadPool();
        client = new EchoClient(this, hostName, portNumber);
        executor.submit(client);

    }


    public void disconnectFromServer(){

        client.closeConnection();
        client=null;
        nickname = null;
        workerColour = null;
        nPlayer = 0;
        connectionActive = false;
    }





    public void createGame(int nPlayer, int tmp, boolean withDivinities) {

            EventServerRequest createGame = new EventNewGameRequest(client.getClientID(), nPlayer, withDivinities);
            client.sendToServer(createGame);
    }

    public void insertNickname(String nickname, int age){
            EventServerRequest addPlayer = new EventAddPlayerRequest(nickname, client.getClientID(), age);
            client.sendToServer(addPlayer);
            }

    public void build(int coordX, int coordY, boolean buildDome){

        EventServerRequest eventBuildRequest = new EventBuildRequest(client.getClientID(),nickname, coordX, coordY, buildDome);

        client.sendToServer(eventBuildRequest);


    }

    public void move(int coordX, int coordY){

        EventServerRequest eventMoveRequest = new EventMoveRequest(client.getClientID(),nickname,coordX, coordY);

        client.sendToServer(eventMoveRequest);


    }

    public void challengerChooseCards(ArrayList<String> chosenDeck){

        EventServerRequest eventChallengerChooseCardsRequest = new EventChallengerChooseCardsRequest(client.getClientID(),nickname,chosenDeck);

        client.sendToServer(eventChallengerChooseCardsRequest);
    }

    public void challengerChooseFirst(String firstPlayerNickname){

        EventServerRequest eventChallengerChoseFirstRequest = new EventChallengerChoseFirstRequest(client.getClientID(), nickname, firstPlayerNickname);

        client.sendToServer(eventChallengerChoseFirstRequest);


    }


    public void possibilityChosen(boolean move, boolean build, boolean endTurn){

        EventServerRequest eventPossibilityChosenRequest = new EventPossibilityChosenRequest(client.getClientID(),nickname, move, build, endTurn);

        client.sendToServer(eventPossibilityChosenRequest);

    }


    public void selectWorker(int ID){

        EventServerRequest eventSelectWorkerRequest = new EventSelectWorkerRequest(client.getClientID(),nickname,ID, workerColour);

        client.sendToServer(eventSelectWorkerRequest);
    }

    public void setCardChosen(String cardChosen){

        EventServerRequest eventSetCardChosenRequest = new EventSetCardChosenRequest(client.getClientID(),nickname, cardChosen);

        client.sendToServer(eventSetCardChosenRequest);

    }

    public void setWorkerPosition(int firstCoordX, int firstCoordY, int secondCoordX, int secondCoordY){

        EventServerRequest eventSetWorkerPositionRequest = new EventSetWorkerPositionRequest(client.getClientID(),nickname, firstCoordX, firstCoordY, secondCoordX, secondCoordY);

        client.sendToServer(eventSetWorkerPositionRequest);
    }


    //pong va spedito come pacchetto normale
    public void pongServer(){

       // System.out.println(client.getClientID());
       // System.out.println("ping ricevuto");
        EventServerRequest eventPong = new EventPong(client.getClientID());

        client.sendToServer(eventPong);
    }


    //_______________________________________________________________________________________________________________________

    //----------------------------------------------------------UPDATES FOR CLIENT'S INTERFACE----------------------------------------------------------------

    //_______________________________________________________________________________________________________________________





    public void showBeginningOfTurn(String popUpMessage) {
        userInterface.showBeginningOfTurn(popUpMessage);
    }

    public void showBuild(int x, int y, int level, boolean hasDome) {
        userInterface.showBuild(x,y,level, hasDome);
    }

    //ricevo showChallenger solo se sono il challenger
    public void showChallenger(String nickname, ArrayList<String> deck) {
        userInterface.showChallenger(nickname,deck);
    }

    public void showDeck(ArrayList<String> deck) {
        userInterface.showDeck(deck);
    }

    public void showGameAttributes(int nPlayer, boolean withDivinities, int boardDimension, ArrayList<String> opponents) {
        this.nPlayer = nPlayer;
        userInterface.showGameAttributes(nPlayer, withDivinities, boardDimension, opponents);
    }

    public void showInvalidAction(String whatToFix) {
        userInterface.showInvalidAction(whatToFix);
    }

    public void showLose(String nickname) {
        userInterface.showLose(nickname);
    }

    public void showMove(Colour colour, int ID, int coordX, int coordY) {
        userInterface.showMove(colour, ID, coordX, coordY);
    }

    public void showPlayerAttribute(String nickname, int age, Colour workerColour, String divinity) {
        if (nickname.equals(this.nickname))
            this.workerColour = workerColour;
        userInterface.showPlayerAttribute(nickname, age, workerColour, divinity);
    }

    public void showPlayerDecision(ArrayList<Integer> validPos) {
        userInterface.showPlayerDecision(validPos);
    }

    public void showTurn(String nickname, int age, String whatToDo) {
        boolean yourTurn;
        if(this.nickname.equals(nickname) && whatToDo != null){
            yourTurn = true;
        } else {
            yourTurn = false;
        }
        userInterface.showTurn(nickname,age,whatToDo,yourTurn);

    }
    

    public void showWin(String nickname) {
        boolean winner;
        if(this.nickname.equals(nickname)){
            winner = true;
        }else{
            winner = false;
        }
        userInterface.showWin(nickname,winner);

    }

    public void showWorkerSelection(ArrayList<Boolean> possibilities) {
        userInterface.showWorkerSelection(possibilities);
    }

    public void showPopUp(String message){
        userInterface.showPopUp(message);
    }

}
