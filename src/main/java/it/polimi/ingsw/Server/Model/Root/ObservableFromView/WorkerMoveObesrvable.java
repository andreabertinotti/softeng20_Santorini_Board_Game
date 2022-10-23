package it.polimi.ingsw.Server.Model.Root.ObservableFromView;


import it.polimi.ingsw.Server.Model.Root.Worker;
import it.polimi.ingsw.Server.RemoteView.ObserverOfModel.WorkerMoveObserver;

public interface WorkerMoveObesrvable {

    void registerMoveObserver (WorkerMoveObserver moveObserver );
    void unregisterMoveObserver (WorkerMoveObserver moveObserver);
    void notifyMoveObservers(Worker movedWorker);
}
