package it.polimi.ingsw.Server.Model.Root.ObservableFromView;

import it.polimi.ingsw.Server.RemoteView.ObserverOfModel.BeginningOfTurnObserver;

public interface BeginningOfTurnObservable {

    void registerBeginningOfTurnObserver(BeginningOfTurnObserver beginningOfTurnObserver);

    void unregisterBeginningOfTurnObserver(BeginningOfTurnObserver beginningOfTurnObserver);

    void notifyBeginningOfTurnObserver();

}
