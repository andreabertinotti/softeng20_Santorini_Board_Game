package it.polimi.ingsw.Server.Model.Root.ObservableFromView;

import it.polimi.ingsw.Server.RemoteView.ObserverOfModel.WorkerSelectionObserver;

public interface WorkerSelectionObservable {
    void registerWorkerSelectionObserver(WorkerSelectionObserver workerSelectionObserver);
    void unregisterWorkerSelectionObserver(WorkerSelectionObserver workerSelectionObserver);
    void notifyWorkerSelectionObserver();
}
