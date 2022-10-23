package it.polimi.ingsw.Server.Model.TurnCreator.MultipleBuildCreator;

import it.polimi.ingsw.Server.Model.Decorator.MultipleBuild.OnGroundThreeBuild;
import it.polimi.ingsw.Server.Model.Decorator.SimpleTurn;
import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.TurnCreator.Effect;

public class OnGroundThreeBuildCreator implements Effect {

    /**
     * This class creates the constructor for the turn related to Poseidon's ability
     * @return is the decorated turn
     */

    @Override
    public Turn getTurn(){

        Turn turn = new SimpleTurn();
        Turn decorated = new OnGroundThreeBuild(turn);

        return decorated;
    };

}