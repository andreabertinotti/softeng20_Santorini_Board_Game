package it.polimi.ingsw.shared.EventRecivedFromServer;


import it.polimi.ingsw.shared.EventRecivedFromServer.Request.*;
import it.polimi.ingsw.Server.NetworkHandeler.MultiEchoServer;
import it.polimi.ingsw.Server.RemoteView.RemoteView;

//gestione messagi ricevuti dal server
public class EventServerVisitor implements ServerVisitor {

    RemoteView remoteView;
    MultiEchoServer server;

    public EventServerVisitor(RemoteView remoteView, MultiEchoServer server) {
        this.remoteView = remoteView;
        this.server = server;
    }

    public void handleMessage(EventNewGameRequest item) {
        remoteView.newGameRequest(item.getnPlayer(), item.isWithDivinities(), item.getClientID());
    }


    public void handleMessage(EventAddPlayerRequest item) {
        remoteView.addPlayerRequest(item.getClientID(),item.getNickname(), item.getAge());
    }

    //______________________________________________________________

    public void handleMessage(EventBuildRequest item) {
        remoteView.buildRequest(item.getClientID(),item.getIdentifier(), item.getCoordX(), item.getCoordY(), item.isBuildDome());
    }

    public void handleMessage(EventChallengerChooseCardsRequest item) {
        remoteView.challengerChoseCardsRequest(item.getClientID(),item.getIdentifier(), item.getChosenDeck());
    }

    public void handleMessage(EventChallengerChoseFirstRequest item) {
        remoteView.challengerChoseFirstRequest(item.getClientID(),item.getIdentifier(), item.getFirstPlayerNickname());
    }

    public void handleMessage(EventMoveRequest item) {
        remoteView.moveRequest(item.getClientID(),item.getIdentifier(), item.getX(), item.getY());
    }

    public void handleMessage(EventPossibilityChosenRequest item) {
        remoteView.possibilityChosenRequest(item.getClientID(),item.getIdentifier(), item.isMove(), item.isBuild(), item.isEndTurn());
    }

    public void handleMessage(EventSelectWorkerRequest item) {
        remoteView.selectWorkerRequest(item.getClientID(),item.getIdentifier(), item.getID(), item.getColour());
    }

    public void handleMessage(EventSetCardChosenRequest item) {
        remoteView.setCardChosenRequest(item.getClientID(),item.getIdentifier(), item.getCardChosen());
    }

    public void handleMessage(EventSetWorkerPositionRequest item) {
        remoteView.setWorkersPositionRequest(item.getClientID(),item.getIdentifier(), item.getFirstCoordX(), item.getFirstCoordY(), item.getSecondCoordX(), item.getSecondCoordY());
    }


    public void handleMessage(EventPong item){

        server.pongReceiver(item.getClientID());
    }

    public void handleMessage(EventDisconnectionRequest item) {
        remoteView.onConnectionCut(item.getClientID());
    }


}
