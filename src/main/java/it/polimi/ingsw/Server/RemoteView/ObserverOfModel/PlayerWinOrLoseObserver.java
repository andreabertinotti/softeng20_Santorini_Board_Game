package it.polimi.ingsw.Server.RemoteView.ObserverOfModel;

/**
 * This Observer is called whenever a player wins or loses
 */

public interface PlayerWinOrLoseObserver {

    public void onPlayerWin(String nickname, int age);
    //passo coordinate delle posizioni dei lavoratori ( del giocatore perdente) che vengono rimossi dalla board
    public void onPlayerLose(String nickname, int age, int firstCoordX, int firstCoordY, int secondCoordX, int secondCoordY);
    public void onPlayerRemoved(String nickaname, int age);

}
