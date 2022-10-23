package it.polimi.ingsw.shared.EventsRecivedFromClient.Updates;

import it.polimi.ingsw.shared.EventsRecivedFromClient.ClientVisitor;
import it.polimi.ingsw.shared.Colour;

import java.io.Serializable;

public class EventUpdateOnPlayerAttributes extends EventClientUpdate implements Serializable {
    private String nickname;
    private int age;
    private Colour workersColour;
    private String divinity;

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
    }

    public Colour getWorkersColour() {
        return workersColour;
    }

    public String getDivinity() {
        return divinity;
    }

    public EventUpdateOnPlayerAttributes(String nickname, int age, Colour workersColour, String divinity){
        this.nickname = nickname;
        this.age = age;
        this.workersColour = workersColour;
        this.divinity = divinity;
    }

    @Override
    public void accept(ClientVisitor visitor) {
        visitor.handleMessage(this);
    }
}
