package it.polimi.ingsw.Server.Model.Root.ObservableFromView;


import it.polimi.ingsw.Server.RemoteView.ObserverOfModel.ChallengerObserver;

public interface ChallengerObservable {
    void registerChallengerObserver (ChallengerObserver challengerObserver);
    void unregisterChallengerObserver (ChallengerObserver challengerObserver);
    void notifyChallengerObservers();
}
