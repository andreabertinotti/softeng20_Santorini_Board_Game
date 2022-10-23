package it.polimi.ingsw.shared.EventsRecivedFromClient;


import it.polimi.ingsw.Client.View.View;
import it.polimi.ingsw.shared.EventsRecivedFromClient.Updates.*;

public class EventClientVisitor implements ClientVisitor {

    //gestione messaggi ricevuti dal client
    //@TODO rivedere se istanziare remoteView è soluzione accettabile
    View view;

    public EventClientVisitor(View view) {
        this.view = view;
    }


    public void handleMessage(EventUpdateOnBeginningOfTurn item){
        view.showBeginningOfTurn(item.getMessage());
    }

    public void handleMessage(EventUpdateOnBuild item){
        view.showBuild(item.getX(), item.getY(), item.getLevel(), item.isHasDome());
    }

    public void handleMessage(EventUpdateOnChallenger item) {
        view.showChallenger(item.getNickname(), item.getDeck());
    }

    public void handleMessage(EventUpdateOnDeck item){
        view.showDeck(item.getCards());
    }

    public void handleMessage(EventUpdateOnGameCreation item){
        view.showGameAttributes(item.getnPlayer(), item.isWithDivinities(), item.getBoardDimension(), item.getPlayers());
    }

    public void handleMessage(EventUpdateOnInvalidAction item){
        view.showInvalidAction(item.getWhatToFix());
    }

    public void handleMessage(EventUpdateOnLose item){
        view.showLose(item.getNickname());
    }

    public void handleMessage(EventUpdateOnMove item){
        view.showMove(item.getColour(), item.getID(), item.getX(), item.getY());
    }

    public void handleMessage(EventUpdateOnPlayerAttributes item){
        view.showPlayerAttribute(item.getNickname(), item.getAge(), item.getWorkersColour(), item.getDivinity());
    }

    public void handleMessage(EventUpdateOnPlayerDecision item){
        view.showPlayerDecision(item.getValidMovesPos());
    }

    public void handleMessage(EventUpdateOnTurn item) {
        view.showTurn(item.getNickname(), item.getAge(), item.getWhatToDo());
    }

    public void handleMessage(EventUpdateOnWin item){
        view.showWin(item.getNickname());
    }

    public void handleMessage(EventUpdateOnWorkerSelection item){
        view.showWorkerSelection(item.getPossibilities());
    }


    public void handleMessage(EventUpdatePopUp item){
        view.showPopUp(item.getMessage());
    }

    //TODO: creare classe nella view
    public void handleMessage(EventUpdateOnRemoved item) {System.out.println("Removed");}




    public void handleMessage(EventPing item){
        view.pongServer();
    }

}
