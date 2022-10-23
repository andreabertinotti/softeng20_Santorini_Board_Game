package it.polimi.ingsw.Server.Model.Root.ObservableFromView;

import it.polimi.ingsw.Server.RemoteView.ObserverOfModel.TurnObserver;

public interface TurnObservable {

    void registerTurnObserver(TurnObserver turnObserver);
    void unregisterTurnObserver(TurnObserver turnObserver);
    void notifyTurnObserver(String whatToDo);
}
