package it.polimi.ingsw.Server.Model.Root.ObservableFromView;

import it.polimi.ingsw.Server.RemoteView.ObserverOfModel.GameCreationObserver;

public interface GameCreationObservable {

    public void registerGameCreationObserver(GameCreationObserver gameCreationObserver);
    public void unregisterGameCreationObserver(GameCreationObserver gameCreationObserver);
    public void notifyGameCreationObserver();
}
