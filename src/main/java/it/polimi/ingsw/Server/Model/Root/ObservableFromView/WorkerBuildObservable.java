package it.polimi.ingsw.Server.Model.Root.ObservableFromView;

import it.polimi.ingsw.Server.RemoteView.ObserverOfModel.WorkerBuildObserver;

public interface WorkerBuildObservable {

    void registerBuildObserver (WorkerBuildObserver buildObserver);
    void unregisterBuildObserver (WorkerBuildObserver buildObserver);
    void notifyBuildObservers(int x, int y);
}
