package it.polimi.ingsw.Server.RemoteView.ObserverOfModel;

/**
 * This Observer is called every time that the currentPlayer is updated (therefore even when a player pass the turn)
 */
public interface TurnObserver {
    public void onNextPlayerTurn(String nickname, int age, String whatToDo);
}
