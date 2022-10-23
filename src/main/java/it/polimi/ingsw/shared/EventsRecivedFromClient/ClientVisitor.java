package it.polimi.ingsw.shared.EventsRecivedFromClient;


import it.polimi.ingsw.shared.EventsRecivedFromClient.Updates.*;

public interface ClientVisitor {

    public void handleMessage(EventUpdateOnBeginningOfTurn item);
    public void handleMessage(EventUpdateOnBuild item);
    public void handleMessage(EventUpdateOnChallenger item);
    public void handleMessage(EventUpdateOnDeck item);
    public void handleMessage(EventUpdateOnGameCreation item);
    public void handleMessage(EventUpdateOnInvalidAction item);
    public void handleMessage(EventUpdateOnLose item);
    public void handleMessage(EventUpdateOnMove item);
    public void handleMessage(EventUpdateOnPlayerAttributes item);
    public void handleMessage(EventUpdateOnPlayerDecision item);
    public void handleMessage(EventUpdateOnTurn item);
    public void handleMessage(EventUpdateOnWin item);
    public void handleMessage(EventUpdateOnWorkerSelection item);
    public void handleMessage(EventUpdatePopUp item);
    public void handleMessage(EventUpdateOnRemoved item);

    public void handleMessage(EventPing item);
}
