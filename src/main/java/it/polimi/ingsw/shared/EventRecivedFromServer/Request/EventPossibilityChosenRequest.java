package it.polimi.ingsw.shared.EventRecivedFromServer.Request;


import it.polimi.ingsw.shared.EventRecivedFromServer.ServerVisitor;

import java.io.Serializable;

public class EventPossibilityChosenRequest extends EventServerRequest implements Serializable {

    private int clientID;
    private String identifier;
    private boolean move;
    private boolean build;
    private boolean endTurn;

    public int getClientID() {
        return clientID;
    }

    public boolean isMove() {
        return move;
    }

    public boolean isBuild() {
        return build;
    }

    public boolean isEndTurn() {
        return endTurn;
    }

    public String getIdentifier() {
        return identifier;
    }

    public EventPossibilityChosenRequest(int clientID, String identifier, boolean move, boolean build, boolean endTurn) {
        this.clientID = clientID;
        this.identifier = identifier;
        this.move = move;
        this.build = build;
        this.endTurn = endTurn;
    }

    @Override
    public void accept(ServerVisitor visitor) {
        visitor.handleMessage(this);
    }
}
