package it.polimi.ingsw.Server.Model.Root.ObservableFromView;


import it.polimi.ingsw.Server.RemoteView.ObserverOfModel.DeckObserver;

public interface DeckObservable {

    void registerDeckObserver (DeckObserver deckObserver);
    void unregisterDeckObserver ( DeckObserver deckObserver);
    void notifyDeckObservers();
}
