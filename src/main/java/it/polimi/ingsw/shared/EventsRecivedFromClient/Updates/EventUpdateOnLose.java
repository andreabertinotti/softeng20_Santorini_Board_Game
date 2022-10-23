package it.polimi.ingsw.shared.EventsRecivedFromClient.Updates;



import it.polimi.ingsw.shared.EventsRecivedFromClient.ClientVisitor;

import java.io.Serializable;

public class EventUpdateOnLose extends EventClientUpdate implements Serializable {
    private String nickname;
    private int age;
    private int firstCoordX;
    private int firstCoordY;
    private int secondCoordX;
    private int secondCoordY;

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
    }

    public int getFirstCoordX() {
        return firstCoordX;
    }

    public int getFirstCoordY() {
        return firstCoordY;
    }

    public int getSecondCoordX() {
        return secondCoordX;
    }

    public int getSecondCoordY() {
        return secondCoordY;
    }

    public EventUpdateOnLose(String nickname, int age, int firstCoordX, int firstCoordY, int secondCoordX, int secondCoordY){
        this.nickname = nickname;
        this.age = age;
        this.firstCoordX = firstCoordX;
        this.firstCoordY = firstCoordY;
        this.secondCoordX = secondCoordX;
        this.secondCoordY = secondCoordY;
    }

    @Override
    public void accept(ClientVisitor visitor) {
        visitor.handleMessage(this);
    }
}
