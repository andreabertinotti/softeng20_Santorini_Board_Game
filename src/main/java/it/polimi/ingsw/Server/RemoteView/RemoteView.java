package it.polimi.ingsw.Server.RemoteView;

import it.polimi.ingsw.shared.EventsRecivedFromClient.Updates.*;
import it.polimi.ingsw.Server.Controller.Controller;
import it.polimi.ingsw.shared.Colour;
import it.polimi.ingsw.Server.RemoteView.ObserverOfModel.*;
import it.polimi.ingsw.Server.NetworkHandeler.MultiEchoServer;

import java.util.ArrayList;

public class RemoteView implements  BeginningOfTurnObserver, ChallengerObserver, DeckObserver,GameCreationObserver,
        PlayerAttributesObserver,PlayerDecisionObserver,PlayerWinOrLoseObserver,TurnObserver,
        WorkerMoveObserver, WorkerBuildObserver, WorkerSelectionObserver {

    private MultiEchoServer server;

    private Controller controller;

    private boolean testing = false;

    /**
     * This is the class constructor that also creates a new instance of the class Controller
     */
    public RemoteView(){
        controller = new Controller(this);
    }


    public void setTesting(boolean testing) {
        this.testing = testing;
    }


    public void setServer(MultiEchoServer server) {
        this.server = server;
    }

    //Note: an instance of the remote view will be created (calling the default contructor) when the Net Proxy will begin a connection with a client that creates a game
    //successive clients will report to the same virtual wiew (therefore the same controller)

    // serie di REQUEST che arrivano dalla rete e chiamano metodi del controller


    /**
     * This method it's invoked from the EchoServer every time that a client tries to establish a new connection
     * it itself calls a controller's method that applies it's logic to determine whether tha action is or isn't possible
     * @return true if the connection can be excepted false otherwise
     */
    public boolean newClientRequest (int nConnection) {
        return controller.canAcceptNewClient(nConnection);
    }


    /**
     * This method calls a controller's method that creates a new game
     * @param nPlayer int, numbers of players that will be in the game
     * @param withDivinities boolean, game with or without divinities
     */
    public void newGameRequest(int nPlayer, boolean withDivinities, int clientID) {
        controller.newGame(nPlayer,withDivinities, clientID,server.getConnections().size());
    }


    /**
     * This method sets connection's nickname. From this point on events are recognized by nickname and not clientID
     * @param clientID
     * @param nickname
     * @param age
     */
    public void addPlayerRequest(int clientID, String nickname, int age) {
        if(controller.addPlayer(clientID,nickname, age)) {
            if (server.getConnectionByID(clientID) != null)
                server.getConnectionByID(clientID).setIdentifier(nickname);
        }
    }

    public void challengerChoseCardsRequest(int clientID, String identifier, ArrayList<String> chosenDeck){
        controller.challengerChosenCards(clientID,identifier, chosenDeck);
    }

    public void setCardChosenRequest(int clientID,String identifier, String cardChosen){
        controller.setCardChosen(clientID,identifier, cardChosen);
    }

    public void challengerChoseFirstRequest(int clientID,String identifier, String firstPlayerNickname){
        controller.firstPlayerChosen(clientID,identifier, firstPlayerNickname);
    }

    public void setWorkersPositionRequest(int clientID,String identifier, int firstCoordX, int firstCoordY, int secondCoordX, int secondCoordY){
        controller.setWorkerPositions(clientID,identifier, firstCoordX, firstCoordY, secondCoordX, secondCoordY);
    }

    public void selectWorkerRequest(int clientID,String identifier, int ID, Colour colour){
        controller.selectWorker(clientID,identifier, ID, colour);
    }

    public void possibilityChosenRequest(int clientID,String identifier, boolean move, boolean build, boolean endTurn){
        controller.possibilityChosen(clientID,identifier, move, build, endTurn);
    }

    public void moveRequest(int clientID,String identifier, int coordX, int coordY){
        controller.move(clientID,identifier, coordX, coordY);
    }

    public void buildRequest(int clientID,String identifier, int coordX, int coordY, boolean buildDome){
        controller.build(clientID,identifier, coordX, coordY, buildDome);
    }




    /**
     * This method instruct the newly added client on what to do (create a new game or insert his credentials)
     * @param clientID it is the connection (thread) dedicated to the client that has to be instructed
     */
    public synchronized void instructClientRequest(int clientID) {
        //int nConnections = server.getConnections().size();
        //controller.instructClient(connection, nConnections);
        controller.instructClient(clientID);
    }


    public void createGamePopUp(int clientID) {

        //invia al primo client la richiesta di creare il gioco
        EventClientUpdate event = new EventUpdatePopUp("CREATE A GAME");
        server.sendToClient(event, server.getConnectionByID(clientID));
    }

    public void waitForGameCreationPopUp(int clientID) {
        EventClientUpdate event = new EventUpdatePopUp("WAIT, A PLAYER IS CREATING A GAME");
        server.sendToClient(event, server.getConnectionByID(clientID));
    }

    public void insertNicknamePopUp(int clientID) {
        //invia al client la richiesta di inserire le credenziali
        EventClientUpdate event = new EventUpdatePopUp("INSERT YOUR NICKNAME AND AGE");
        server.sendToClient(event, server.getConnectionByID(clientID));

    }



    public void insertNicknamePopUp() {
        //invia al client la richiesta di inserire le credenziali
        EventClientUpdate event = new EventUpdatePopUp("INSERT YOUR NICKNAME AND AGE");
        server.sendBroadcast(event);
    }


    public void onNewClientEnteringGame(int nPlayer, boolean withDivinities, int boardDimension, ArrayList<String> players, int clientID){
        EventClientUpdate event = new EventUpdateOnGameCreation(nPlayer, withDivinities, boardDimension, players);
        server.sendToClient(event, server.getConnectionByID(clientID));
        insertNicknamePopUp(clientID);
    }





    @Override
    public void onGameCreation(int nPlayer, boolean withDivinities, int boardDimension, ArrayList<String> players) {
        //creates a board, the bold interface for the players and the initial deck
        //pop up to the players telling theme to put in their info
        EventClientUpdate event = new EventUpdateOnGameCreation(nPlayer, withDivinities, boardDimension, players);
        server.sendBroadcast(event);
        insertNicknamePopUp();
        //if(testing)
            System.out.println("\nNumber of Players in game? "+ nPlayer+ " withDivinities? "+withDivinities+" bordDimension? "+boardDimension+" DecK ");
    }

    @Override
    public void onBeginningOfTurn(String nickname) {
        //serializzi il messaggio e lo invii al currentPlayer
        EventClientUpdate event = new EventUpdateOnBeginningOfTurn();
        server.sendToClient(event,server.getConnectionByIdentifier(nickname));

        //pop up the the player that becomes the current player inviting him to select a worker
        if(testing)
            System.out.println("\nit is your turn! select a worker to star playing");
    }

    @Override
    public void onChallengerChosen(String challengerNickname, int age, ArrayList<String> deck) {
        controller.setChallenger(challengerNickname);
        EventClientUpdate event = new EventUpdateOnChallenger(challengerNickname, age, deck);
        server.sendBroadcast(event);

        //change the graphic marking the player that becomes the challenger
        //tell the challenger to choose the cards of the deck
        if(testing)
            System.out.println("\nthe challenger is: " + challengerNickname + age);
    }

    @Override
    public void onDeckChange(ArrayList<String> cards) {
        //serializzi il messaggio e lo invii al currentPlayer
        EventClientUpdate event = new EventUpdateOnDeck(cards);
        server.sendBroadcast(event);

        //change in the graphic displaying the updated deck
        if(testing)
            for( String c: cards)
                System.out.println(c + "\n");
    }

    @Override
    public void onPlayerAttributesUpdate(String nickname, int age, Colour workersColour, String divinity) {

        //serializzi il messaggio e lo invii a tutti per dire chi si è aggiunto
        EventClientUpdate event = new EventUpdateOnPlayerAttributes(nickname, age, workersColour, divinity);
        server.sendBroadcast(event);

        //change in the graphic of the current player attributes
        if(testing)
            System.out.println("\n player: " + nickname +"," + age +"," + workersColour + ","+ divinity);
    }

    @Override
    public void onDecisionTaken(String nickname, ArrayList<Integer> validPos) {
        //serializzi il messaggio e lo invii al currentPlayer
        EventClientUpdate eventRecived = new EventUpdateOnPlayerDecision(validPos);
        server.sendToClient(eventRecived,server.getConnectionByIdentifier(nickname));

        //pop up displayed only to the current player
        //depending on what decision he has taken he is shown the different positions over which he can move or build
        if(testing) {
            int i, j;
            for( i = 0, j = 1; j < validPos.size(); i++, j++) {
                System.out.println("x:" + validPos.get(i) + "   y:" + validPos.get(j));
                i++;
                j++;
            }
        }

    }

    @Override
    public void onNextPlayerTurn(String nickname, int age, String whatToDo) {

        //serializzi il messaggio e lo invii a tutti
        EventClientUpdate event = new EventUpdateOnTurn(nickname, age, whatToDo);
        server.sendBroadcast(event);

        //change in everybody graphic highlighting the player that has become the current player
        if(testing) {
            System.out.println("\ncurrent player" + nickname + age);
            System.out.println(whatToDo);
        }
    }

    @Override
    public void onPlayerWin(String nickname, int age) {
        //serializzi il messaggio e lo invii a tutti
        EventClientUpdate event = new EventUpdateOnWin(nickname, age);
        server.sendBroadcast(event);

        //change in everybody graphic showing the winner
        if(testing){
            System.out.println("\nthe winner is:"  +  nickname + age);
        }

    }

    @Override
    public void onPlayerLose(String nickname, int age, int firstCoordX, int firstCoordY, int secondCoordX, int secondCoordY) {
        //serializzi il messaggio e lo invii a tutti
        EventClientUpdate event = new EventUpdateOnLose(nickname, age, firstCoordX, firstCoordY, secondCoordX, secondCoordY);
        server.sendBroadcast(event);

        //change in everybody graphic removing the loser's workers and darkening its image
        if(testing) {
            System.out.println("\nthe player" + nickname + age + "has lost");
            System.out.println("\nremove the workers in pos:" + firstCoordX + firstCoordY + secondCoordX + secondCoordY);
        }
    }

    @Override
    public void onPlayerRemoved(String nickname, int age) {
        //EventMessage eventMessage = new EventUpdateOnLose(nickname, age);
        //server.sendToAll(eventMessage);

        if(testing) {
            System.out.println("\nthe player" + nickname + age + "has been removed");
        }

    }


    @Override
    public void updateOnBuild(int x, int y, int level, boolean hasDome) {
        //serializzi il messaggio e lo invii a tutti
        EventClientUpdate event = new EventUpdateOnBuild(x, y, level, hasDome);
        server.sendBroadcast(event);

        //change in everybody graphic updating the construction made
        //perhaps it should also suggest to the current player to newly select a worker
        if(testing)
            System.out.println("\non the position: "+ x+y+" the level becomes "+ level+ "and hasDome?"+ hasDome);

    }

    @Override
    public void updateOnMove(int x, int y, int ID, Colour colour) {
        //serializzi il messaggio e lo invii a tutti
        EventClientUpdate event = new EventUpdateOnMove(x, y, ID, colour);
        server.sendBroadcast(event);

        //change in everybody graphic updating the movement made
        //perhaps it should also suggest to the current player to newly select a worker
        if(testing)
            System.out.println("\nmove the worker:"+ ID+colour+ " to the position "+x+y);
    }

    @Override
    public void onWorkerSelection(String nickname, ArrayList<Boolean> possibilities) {
        //serializzi il messaggio e lo invii al currentPlayer
        EventClientUpdate event = new EventUpdateOnWorkerSelection(possibilities);
        server.sendToClient(event,server.getConnectionByIdentifier(nickname));

        if(testing)
            System.out.println("the selceted worker can: " +"Move:"+possibilities.get(0)+" Build: "+possibilities.get(1)+" End Turn: "+ possibilities.get(2));

    }

    public void invalidActionPopUp(String nickname, String whatToFix) {
        //serializzi il messaggio e lo invii al currentPlayer
        EventClientUpdate event = new EventUpdateOnInvalidAction(whatToFix);
        server.sendToClient(event,server.getConnectionByIdentifier(nickname));

        //dico all'utente che l'azione richiesta non è possibile
        if(testing)
            System.out.println(whatToFix);
    }

    public void invalidActionPopUp(int clientID, String whatToFix) {
        //serializzi il messaggio e lo invii al currentPlayer
        EventClientUpdate event = new EventUpdateOnInvalidAction(whatToFix);
        server.sendToClient(event, server.getConnectionByID(clientID));
    }

    public void onConnectionCut(int clientID) {
        server.getConnectionByID(clientID).close();
        int nConnections = server.getConnections().size();
        int firstConnectionID = 0;
        if(nConnections > 0)
            firstConnectionID = server.getConnections().get(0).getClientID();
        controller.connectionFailureHandler(clientID, nConnections, firstConnectionID);

    }


    public void disconnectAll() {
        String whatToFix = "disconnected from server";
        EventClientUpdate event = new EventUpdateOnInvalidAction(whatToFix);
        server.sendBroadcast(event);
        server.deregisterAllConnection();
    }


    public void disconnectClient(int clientID) {
        String whatToFix = "disconnected from server";
        EventClientUpdate event = new EventUpdateOnInvalidAction(whatToFix);
        for(int i=0; i < server.getConnections().size(); i++) {
            if(server.getConnections().get(i).getClientID() != clientID) {
                int connectionToClose = server.getConnections().get(i).getClientID();
                server.sendToClient(event, server.getConnections().get(i));
                server.getConnectionByID(connectionToClose).close();
            }
        }
    }




}

