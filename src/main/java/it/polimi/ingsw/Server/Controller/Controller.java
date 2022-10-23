package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.shared.Colour;
import it.polimi.ingsw.Server.Model.Root.Game;
import it.polimi.ingsw.Server.Parser.ParserJson;
import it.polimi.ingsw.Server.RemoteView.RemoteView;

import java.util.ArrayList;

public class  Controller {

    private Game game;
    private RemoteView remoteView;
    private ParserJson parserJson;
    private boolean initialization;
    private int firstClientID;
    private boolean creatingGame = false;
    private int addedPlayer = 0;
    private boolean gameReady = false;
    private boolean firstPlayerChoosen = false;
    private boolean deckChoosen = false;
    private String challenger = null;
    private boolean cardsChoosen = false;

    public Game getGame() {
        return game;
    }

    public int getFirstClientID() {
        return firstClientID;
    }

    public void deleteGame() {
        this.game = null;
        creatingGame = false;
        addedPlayer = 0;
        gameReady = false;
        firstPlayerChoosen = false;
        deckChoosen = false;
        challenger = null;
        cardsChoosen = false;

    }

    public void setChallenger(String challenger) {
        this.challenger = challenger;
    }

    public Controller(RemoteView remoteView) {
        this.remoteView = remoteView;
    }

    //******************************************************************************************************************************************

    // METODI RELATIVI ALLA FASE DI INIZIALIZZAZIONE

    //******************************************************************************************************************************************
    /**
     * This method only checks if a new connection with a client can or cannot be established
     * @return true if a new connection can be established false otherwise
     */
    public boolean canAcceptNewClient(int nConnection) {
        if( game == null || (game != null && nConnection < game.getMaxPlayers())   ){
            return true;
        }

        return false;
    }


    public synchronized void instructClient(int clientID){
        if (game == null && creatingGame == false) {
            creatingGame = true;
            firstClientID = clientID;
            remoteView.createGamePopUp(clientID);
        } else if(creatingGame == true){
            remoteView.waitForGameCreationPopUp(clientID);
        } else {
            ArrayList<String> oldPlayers = new ArrayList<>();
            for (int i=0; i<game.getPlayers().size(); i++)
                oldPlayers.add(game.getPlayers().get(i).getNickname());
            remoteView.onNewClientEnteringGame(game.getMaxPlayers(), game.isWithDivinities(), game.getBoard().getDimension(), oldPlayers, clientID);
        }
    }


    /**
     * This method it's invoked by the remote view when a client request to create a new game
     * @param nPlayer int, numbers of players that will be in the game
     * @param withDivinities boolean, game with or without divinities
     */
    public void newGame(int nPlayer, boolean withDivinities, int clientID, int nConnections) {
        if(clientID== firstClientID && game == null) {
            if (withDivinities)
                parserJson = new ParserJson(nPlayer);
            if (nPlayer <= 3 && nPlayer >= 2) {
                game = new Game(nPlayer, withDivinities, parserJson, remoteView);
                initialization = true;
                creatingGame = false;
                //disconnetto clients fino a quando ne ho il numero giusto per la partita creata
                while(nConnections > nPlayer) {
                    remoteView.disconnectClient(clientID);
                    nConnections--;
                }

            } else remoteView.invalidActionPopUp(clientID,"can't create a game with this many players");
        }else remoteView.invalidActionPopUp(clientID,"you can't create a game");
    }



    public boolean addPlayer(int clientID,String nickname, int age) {
        //TODO as right now we can not differentiate from 2 errors (same nickname or lobby full)
        if(initialization) {
            if(game!= null) {
                if (!game.addPlayer(nickname, age)) {
                    remoteView.invalidActionPopUp(nickname, "invalid Nickname, age or lobby full");
                    return false;
                } else {
                    addedPlayer++;

                    if (addedPlayer == game.getPlayers().size()) {
                        gameReady = true;
                        if (!game.isWithDivinities())
                            firstPlayerChoosen = true;
                    }
                    return true;
                }

            }else
                remoteView.invalidActionPopUp(clientID,"request ignored by server!");
            return false;

        }else
            remoteView.invalidActionPopUp(clientID,"invalid request, end initialization!");
        return false;
    }


    public void challengerChosenCards(int clientID,String senderNickname, ArrayList<String> chosenDeck) {
        if (initialization) {
            if(senderNickname == challenger && !deckChoosen && gameReady) {
                if (!game.challengerChooseCards(chosenDeck))
                    remoteView.invalidActionPopUp(senderNickname, "select a valid deck of divinities");
                else
                    deckChoosen = true;
            }
            else
                remoteView.invalidActionPopUp(clientID, "request ignored by server!");
        } else
            remoteView.invalidActionPopUp(clientID, "invalid request, initialization is over! can't perform this action.");
    }


    public void setCardChosen(int clientID,String senderNickname, String cardChosen) {
            if (initialization) {
                if(deckChoosen && senderNickname == game.getCurrentPlayer().getNickname()  ) {
                    if (!game.setPlayerDivinity(cardChosen)) {
                        remoteView.invalidActionPopUp(senderNickname, "chose one of the available divinities");
                    }else if( game.getDeck().getCardsInDeck().size() == 0 )
                        cardsChoosen = true;
                }else
                    remoteView.invalidActionPopUp(clientID, "request ignored by server!");
            }else
                remoteView.invalidActionPopUp(clientID,"invalid request, initialization is over! can't perform this action.");

    }

    public void firstPlayerChosen(int clientID,String senderNickname, String nickname) {
            if (initialization) {
                if(senderNickname == challenger && cardsChoosen) {
                    if (!game.chooseFirst(nickname)) {
                        remoteView.invalidActionPopUp(senderNickname, "choose a valid player to be the first");
                    }else
                        firstPlayerChoosen = true;
                }else
                    remoteView.invalidActionPopUp(clientID, "request ignored by server!");
            } else
                remoteView.invalidActionPopUp(clientID, "invalid request, end initialization!");

    }


    public void setWorkerPositions(int clientID,String senderNickname, int firstCoordX, int firstCoordY, int secondCoordX, int secondCoordY) {
            if (initialization) {
                if(senderNickname == game.getCurrentPlayer().getNickname() && firstPlayerChoosen) {
                    if (game.setWorkersInitialPosition(firstCoordX, firstCoordY, secondCoordX, secondCoordY) == 1)
                        remoteView.invalidActionPopUp(senderNickname, "invalid position for workers");
                    else if (game.setWorkersInitialPosition(firstCoordX, firstCoordY, secondCoordX, secondCoordY) == 3)
                        remoteView.invalidActionPopUp(clientID, "you have to choose the divinity before positioning workers!");
                    else if (game.setWorkersInitialPosition(firstCoordX, firstCoordY, secondCoordX, secondCoordY) == 2){
                            initialization = false;
                    }

                }else
                    remoteView.invalidActionPopUp(clientID, "request ignored by server!");


            } else
                remoteView.invalidActionPopUp(clientID, "invalid request, end initialization!");

    }



    //******************************************************************************************************************************************

    //METODI UTILIZZAZTI DURANTE IL GIOCO VERO E PROPRIO (non l'inizializzazione)

    //******************************************************************************************************************************************

    public void selectWorker(int clientID,String senderNickname, int ID, Colour colour) {
        System.out.println(senderNickname +" "+ ID +" "+colour);
        if (validatePlayerRequest(senderNickname)) {
            if (!initialization) {
                if (!game.selectWorker(ID, colour))
                    remoteView.invalidActionPopUp(senderNickname, "select one of your workers");
            } else
                remoteView.invalidActionPopUp(clientID, "invalid request, initialization is over!");
        } else
            remoteView.invalidActionPopUp(clientID, "invalid request, it's not your turn! can't perform this action.");
    }

    public void possibilityChosen(int clientID,String senderNickname, boolean move, boolean build, boolean endTurn) {
        if (validatePlayerRequest(senderNickname)) {
            if (!initialization) {
                int count = 0;
                if (move)
                    count++;
                if (build)
                    count++;
                if (endTurn)
                    count++;
                if (count == 0)
                    remoteView.invalidActionPopUp(senderNickname, "select a valid option");
                if (count != 1)
                    remoteView.invalidActionPopUp(senderNickname, "select ONE option");
                else if (move) {
                    if (!game.setWantsToMove())
                        remoteView.invalidActionPopUp(senderNickname, "select a valid option");
                } else if (build) {
                    if (!game.setWantsToBuild())
                        remoteView.invalidActionPopUp(senderNickname, "select a valid option");
                } else if (endTurn) {
                    if (!game.setWantsToEndTurn())
                        remoteView.invalidActionPopUp(senderNickname, "select a valid option");
                }
            } else
                remoteView.invalidActionPopUp(clientID, "invalid request, end initialization!");
        } else
            remoteView.invalidActionPopUp(clientID, "invalid request, it's not your turn! can't perform this action.");
    }

    public void build(int clientID,String senderNickname, int coordX, int coordY, boolean buildDome) {
        if (validatePlayerRequest(senderNickname)) {
            if (!initialization) {
                if (!game.build(coordX, coordY, buildDome))
                    remoteView.invalidActionPopUp(senderNickname, "can't build in this position");
            } else
                remoteView.invalidActionPopUp(clientID, "invalid request, end initialization");
        } else
            remoteView.invalidActionPopUp(clientID, "invalid request, it's not your turn! can't perform this action.");
    }


    public void move(int clientID,String senderNickname, int coordX, int coordY) {
        if (validatePlayerRequest(senderNickname)) {
            if (!initialization) {
                if (!game.move(coordX, coordY))
                    remoteView.invalidActionPopUp(senderNickname, "can't move to this position");
            } else
                remoteView.invalidActionPopUp(clientID, "invalid request, end initialization");
        } else
            remoteView.invalidActionPopUp(clientID, "invalid request, it's not your turn! can't perform this action.");
    }






    //******************************************************************************************************************************************

    //METODI DI UTILITà DEL CONSTROLLER PER DECIDERE SE INVOCARE METODI SUL MODEL O RESPINGERE IL PACCHETTO E PER GESTIRE LA CADUTA DI CONNESSIONE

    //******************************************************************************************************************************************


    public boolean validatePlayerRequest(String senderNickname){
       if(game.getCurrentPlayer().getNickname().equals(senderNickname))
           return true;
       else
           return false;

    }



    public void connectionFailureHandler(int clientID, int nConnections, int firstConnectionID){
        if(game!=null){
            deleteGame();
            remoteView.disconnectAll();
        }else if( game == null){
            if(creatingGame) {

                if (clientID == firstClientID) {
                    creatingGame = false;
                    //se c'è ancora qualcuno nella lobby
                    if (nConnections > 0)
                        //qualcuno d'altro crei game
                        instructClient(firstConnectionID);
                }

            }

        }
    }








}
