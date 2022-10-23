package it.polimi.ingsw.Server.Model.TurnCreator.PowerfulBuildCreator;

import it.polimi.ingsw.Server.Model.Decorator.PowerfullBuild.BuildDome;
import it.polimi.ingsw.Server.Model.Decorator.SimpleTurn;
import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.TurnCreator.Effect;

public class BuildDomeCreator implements Effect {

    /**
     * This class creates the constructor for the turn related to Atlas' ability
     * @return is the decorated turn
     */

    @Override
    public Turn getTurn(){

        Turn turn = new SimpleTurn();
        Turn decorated = new BuildDome(turn);

        return decorated;
    };

}
