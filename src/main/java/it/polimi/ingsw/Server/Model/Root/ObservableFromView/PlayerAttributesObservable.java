package it.polimi.ingsw.Server.Model.Root.ObservableFromView;


import it.polimi.ingsw.Server.RemoteView.ObserverOfModel.PlayerAttributesObserver;

public interface PlayerAttributesObservable {

    void registerPlayerAttributesObserver (PlayerAttributesObserver playerAttributesObserver);
    void unregisterPlayerAttributesObserver ( PlayerAttributesObserver playerAttributesObserver);
    void notifyPlayerAttributesObservers();
}
