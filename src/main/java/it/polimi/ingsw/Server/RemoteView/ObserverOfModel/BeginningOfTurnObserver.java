package it.polimi.ingsw.Server.RemoteView.ObserverOfModel;

/**
 * This Observer is called every time a new turn starts
 */
public interface BeginningOfTurnObserver {

    public void onBeginningOfTurn(String nickname); //pop che invita a selezionare un worker
}
