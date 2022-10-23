package it.polimi.ingsw.Server.RemoteView.ObserverOfModel;

import java.util.ArrayList;

/**
 * This method is called whenever a player decide that wants to move or to build
 */
public interface PlayerDecisionObserver {
    public void onDecisionTaken(String nickname, ArrayList<Integer> validMovesPos);
}
