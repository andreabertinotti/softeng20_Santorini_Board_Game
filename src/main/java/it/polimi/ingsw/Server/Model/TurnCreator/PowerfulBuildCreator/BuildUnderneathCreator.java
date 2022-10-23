package it.polimi.ingsw.Server.Model.TurnCreator.PowerfulBuildCreator;

import it.polimi.ingsw.Server.Model.Decorator.PowerfullBuild.BuildUnderneath;
import it.polimi.ingsw.Server.Model.Decorator.SimpleTurn;
import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.TurnCreator.Effect;

public class BuildUnderneathCreator implements Effect {

    /**
     * This class creates the constructor for the turn related to Zeus' ability
     * @return is the decorated turn
     */

    @Override
    public Turn getTurn(){

        Turn turn = new SimpleTurn();
        Turn decorated = new BuildUnderneath(turn);

        return decorated;
    };

}
