package it.polimi.ingsw.shared.EventRecivedFromServer.Request;

import it.polimi.ingsw.shared.EventRecivedFromServer.ServerVisitor;

public abstract class EventServerRequest {

    public abstract void accept(ServerVisitor visitor);
}
