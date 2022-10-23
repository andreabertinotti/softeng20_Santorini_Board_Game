package it.polimi.ingsw.shared.EventRecivedFromServer;

import it.polimi.ingsw.shared.EventRecivedFromServer.Request.*;

public interface ServerVisitor {

    public void handleMessage(EventAddPlayerRequest item);
    public void handleMessage(EventBuildRequest item);
    public void handleMessage(EventChallengerChooseCardsRequest item);
    public void handleMessage(EventChallengerChoseFirstRequest item);
    public void handleMessage(EventMoveRequest item);
    public void handleMessage(EventPossibilityChosenRequest item);
    public void handleMessage(EventSelectWorkerRequest item);
    public void handleMessage(EventSetCardChosenRequest item);
    public void handleMessage(EventSetWorkerPositionRequest item);
    public void handleMessage(EventNewGameRequest item);
    public void handleMessage(EventPong item);

    public void handleMessage(EventDisconnectionRequest item);
}
