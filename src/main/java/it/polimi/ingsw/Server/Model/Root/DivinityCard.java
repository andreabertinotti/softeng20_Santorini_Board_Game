package it.polimi.ingsw.Server.Model.Root;


import it.polimi.ingsw.Server.Model.TurnCreator.Effect;

/**
 * This class represents the divinity
 */

public class DivinityCard {

    private String name;
    private Effect effect;
    private String info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
