package it.polimi.ingsw.Server.Model.Root.ObservableFromView;

import it.polimi.ingsw.Server.RemoteView.ObserverOfModel.PlayerDecisionObserver;

public interface PlayerDecisionObservable {

    void registerPlayerDecisionObserver( PlayerDecisionObserver playerDecisionObserver);
    void unregisterPlayerDecisionObserver(PlayerDecisionObserver playerDecisionObserver);
    void notifyPlayerDecisionObserver();
}
