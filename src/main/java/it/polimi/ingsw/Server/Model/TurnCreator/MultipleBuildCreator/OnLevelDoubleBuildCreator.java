package it.polimi.ingsw.Server.Model.TurnCreator.MultipleBuildCreator;

import it.polimi.ingsw.Server.Model.Decorator.MultipleBuild.OnLevelDoubleBuild;
import it.polimi.ingsw.Server.Model.Decorator.SimpleTurn;
import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.TurnCreator.Effect;

public class OnLevelDoubleBuildCreator implements Effect {

    /**
     * This class creates the constructor for the turn related to Prometheus' ability
     * @return is the decorated turn
     */

    @Override
    public Turn getTurn(){

        Turn turn = new SimpleTurn();
        Turn decorated = new OnLevelDoubleBuild(turn);

        return decorated;
    };

}
