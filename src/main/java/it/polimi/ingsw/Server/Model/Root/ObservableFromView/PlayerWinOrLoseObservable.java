package it.polimi.ingsw.Server.Model.Root.ObservableFromView;

import it.polimi.ingsw.Server.RemoteView.ObserverOfModel.PlayerWinOrLoseObserver;

public interface PlayerWinOrLoseObservable {


    void registerPlayerWinOrLoseObserver (PlayerWinOrLoseObserver playerObserver);
    void unregisterPlayerWinOrLoseObserver ( PlayerWinOrLoseObserver playerObserver);
    void notifyPlayerWinOrLoseObservers();
}
