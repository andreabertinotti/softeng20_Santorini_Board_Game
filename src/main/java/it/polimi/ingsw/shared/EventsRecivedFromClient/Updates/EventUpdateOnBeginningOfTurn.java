package it.polimi.ingsw.shared.EventsRecivedFromClient.Updates;


import it.polimi.ingsw.shared.EventsRecivedFromClient.ClientVisitor;

import java.io.Serializable;

public class EventUpdateOnBeginningOfTurn extends EventClientUpdate implements Serializable {

    private String message = "it is your turn! select a worker to star playing";

    public String getMessage() {
        return message;
    }

    @Override
    public void accept(ClientVisitor visitor) {
        visitor.handleMessage(this);
    }
}
