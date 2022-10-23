package it.polimi.ingsw.shared.EventsRecivedFromClient.Updates;



import it.polimi.ingsw.shared.EventsRecivedFromClient.ClientVisitor;

import java.io.Serializable;

public class EventUpdatePopUp extends EventClientUpdate implements Serializable {

    private String message;

    public String getMessage() {
        return message;
    }

    public EventUpdatePopUp(String message) {
        this.message = message;
    }

    @Override
    public void accept(ClientVisitor visitor) {
        visitor.handleMessage(this);
    }
}
