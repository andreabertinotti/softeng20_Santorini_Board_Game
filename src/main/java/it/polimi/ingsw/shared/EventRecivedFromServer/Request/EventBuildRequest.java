package it.polimi.ingsw.shared.EventRecivedFromServer.Request;

import it.polimi.ingsw.shared.EventRecivedFromServer.ServerVisitor;

import java.io.Serializable;

public class EventBuildRequest extends EventServerRequest implements Serializable {

    private int clientID;
    private String identifier;
    private int coordX;
    private int coordY;
    private boolean buildDome;

    public int getClientID() {
        return clientID;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public boolean isBuildDome() {
        return buildDome;
    }

    public String getIdentifier() {
        return identifier;
    }

    public EventBuildRequest(int clientID, String identifier, int coordX, int coordY, boolean buildDome) {
        this.clientID = clientID;
        this.identifier = identifier;
        this.coordX = coordX;
        this.coordY = coordY;
        this.buildDome = buildDome;
    }

    @Override
    public void accept(ServerVisitor visitor) {
        visitor.handleMessage(this);
    }
}
