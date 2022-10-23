package it.polimi.ingsw.Server.RemoteView.ObserverOfModel;

/**
 * This Observer is called when a build happens
 */

public interface WorkerBuildObserver {

    public void updateOnBuild(int x, int y, int level, boolean hasDome);
}
