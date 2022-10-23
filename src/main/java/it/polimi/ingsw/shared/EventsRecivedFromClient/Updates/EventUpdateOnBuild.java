package it.polimi.ingsw.shared.EventsRecivedFromClient.Updates;

import it.polimi.ingsw.shared.EventsRecivedFromClient.ClientVisitor;

import java.io.Serializable;

public class EventUpdateOnBuild extends EventClientUpdate implements Serializable {
    private int x;
    private int y;
    private int level;
    private boolean hasDome;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLevel() {
        return level;
    }

    public boolean isHasDome() {
        return hasDome;
    }

    public EventUpdateOnBuild(int x, int y, int level, boolean hasDome) {
        this.x = x;
        this.y = y;
        this.level = level;
        this.hasDome = hasDome;
    }

    @Override
    public void accept(ClientVisitor visitor) {
        visitor.handleMessage(this);
    }
}
