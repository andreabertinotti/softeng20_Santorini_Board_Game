package it.polimi.ingsw.Server.RemoteView.ObserverOfModel;

import java.util.ArrayList;

/**
 * This Observer is called every time that the deck is updated
 */

public interface DeckObserver {
    public void onDeckChange(ArrayList<String> cards);
}
