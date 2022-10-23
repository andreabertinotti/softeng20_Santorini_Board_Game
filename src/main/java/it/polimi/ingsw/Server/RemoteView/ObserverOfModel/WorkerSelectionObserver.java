package it.polimi.ingsw.Server.RemoteView.ObserverOfModel;

import java.util.ArrayList;

/**
 * This Observer is called whenever a worker is selected
 */

public interface WorkerSelectionObserver {

    public void onWorkerSelection(String nickname, ArrayList<Boolean> possibilities);
}
