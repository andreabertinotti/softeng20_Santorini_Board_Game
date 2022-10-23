package it.polimi.ingsw.shared.EventsRecivedFromClient.Updates;



import it.polimi.ingsw.shared.EventsRecivedFromClient.ClientVisitor;

import java.io.Serializable;

public class EventUpdateOnInvalidAction extends EventClientUpdate implements Serializable {

    private String whatToFix;

    public String getWhatToFix() {
        return whatToFix;
    }

    public EventUpdateOnInvalidAction(String whatToFix) {
        this.whatToFix = whatToFix;
    }

    @Override
    public void accept(ClientVisitor visitor) {
        visitor.handleMessage(this);
    }
}
