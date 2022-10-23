package it.polimi.ingsw.Server.Model.TurnCreator.MultipleBuildCreator;

import it.polimi.ingsw.Server.Model.Decorator.MultipleBuild.OffBorderOneMoreBuild;
import it.polimi.ingsw.Server.Model.Decorator.SimpleTurn;
import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.TurnCreator.Effect;

public class OffBorderOneMoreBuildCreator implements Effect {

    /**
     * This class creates the constructor for the turn related to Hestia's ability
     * @return is the decorated turn
     */
    @Override
    public Turn getTurn(){

        Turn turn = new SimpleTurn();
        Turn decorated = new OffBorderOneMoreBuild(turn);

        return decorated;
    };

}
