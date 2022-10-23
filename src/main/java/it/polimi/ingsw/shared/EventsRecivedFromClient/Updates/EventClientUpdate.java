package it.polimi.ingsw.shared.EventsRecivedFromClient.Updates;

import it.polimi.ingsw.shared.EventsRecivedFromClient.ClientVisitor;

public abstract class EventClientUpdate {

    public abstract void accept(ClientVisitor visitor);
}
