package it.polimi.ingsw.shared.EventsRecivedFromClient.Updates;

import it.polimi.ingsw.shared.EventsRecivedFromClient.ClientVisitor;

import java.io.Serializable;

public class EventPing extends EventClientUpdate implements Serializable {



    @Override
    public void accept(ClientVisitor visitor) {
        visitor.handleMessage(this);
    }
}
