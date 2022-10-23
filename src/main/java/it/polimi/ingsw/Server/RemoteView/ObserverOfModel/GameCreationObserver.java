package it.polimi.ingsw.Server.RemoteView.ObserverOfModel;

import java.util.ArrayList;

/**
 * This observer is called every time a new instance of game is create via the constructor
 */
public interface GameCreationObserver {

    //pop up to the players telling theme to put in their info
    public void onGameCreation(int nPlayer, boolean withDivinities, int boardDimension, ArrayList<String> players);
}
