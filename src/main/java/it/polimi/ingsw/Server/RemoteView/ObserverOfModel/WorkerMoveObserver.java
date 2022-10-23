package it.polimi.ingsw.Server.RemoteView.ObserverOfModel;


import it.polimi.ingsw.shared.Colour;

/**
 * This Observer is called when a move happens
 */

public interface WorkerMoveObserver {

    public void updateOnMove(int x, int y, int ID, Colour colour);

}
