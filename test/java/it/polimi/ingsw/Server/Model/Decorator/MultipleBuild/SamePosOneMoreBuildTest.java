package it.polimi.ingsw.Server.Model.Decorator.MultipleBuild;

import it.polimi.ingsw.Server.Model.Decorator.SimpleTurn;
import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Root.Game;
import it.polimi.ingsw.Server.Model.Root.Player;
import it.polimi.ingsw.Server.Model.Root.Worker;
import it.polimi.ingsw.Server.Parser.ParserJson;
import it.polimi.ingsw.Server.RemoteView.RemoteView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SamePosOneMoreBuildTest {

    Game sceneOfGame() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(3);
        Game game = new Game(3, true,parserJson, remoteView);
        game.addPlayer("Marc", 24);

        for (Player player : game.getPlayers())
            player.assignWorker(game.getPlayers().indexOf(player));
        //posizione di tutti i worker
        game.getPlayers().get(0).getWorkers().get(0).setPosition(game.getBoard().getPosition(1, 1));
        game.getBoard().getPosition(1, 1).setOccupiedBy(game.getPlayers().get(0).getWorkers().get(0));


        //settaggio delle costruzioni sulla board
        game.getBoard().getPosition(0, 0).setLevel(2);
        game.getBoard().getPosition(0, 1).setLevel(2);
        game.getBoard().getPosition(1, 0).setLevel(1);
        game.getBoard().getPosition(1, 1).setLevel(1);


        return game;
    }


    @Test
    void checkPossibleBuildingTest(){

        boolean res;
        Game game = sceneOfGame();
        Turn turn = new SimpleTurn();
        Turn decoratedTurn = new SamePosOneMoreBuild(turn);

        Player player = game.getPlayers().get(0);
        player.setMyTurn(decoratedTurn);

        Worker worker = game.getBoard().getPosition(1, 1).getOccupiedBy();
        worker.setBuildNum(0);
        decoratedTurn.startTurn(game.getPlayers().get(0));
        worker.setMoveNum(1);

        decoratedTurn.build(player,game.getBoard(),worker,game.getBoard().getPosition(1,0),false);

        //tentativo di eseguire la seconda build sopra la prima (senza dover costruire una dome)
       res= decoratedTurn.build(player,game.getBoard(),worker,game.getBoard().getPosition(1,0),false);
       assertEquals(true, res);
       assertEquals(3, game.getBoard().getPosition(1,0).getLevel());
       assertEquals(2,worker.getBuildNum());

       //tentativo di eseguire una terza build
        res= decoratedTurn.build(player,game.getBoard(),worker,game.getBoard().getPosition(1,0),false);
        assertEquals(false, res);


        decoratedTurn.startTurn(player);
        worker.setMoveNum(1);

        decoratedTurn.build(player,game.getBoard(),worker,game.getBoard().getPosition(0,0),false);

        //tentativo di eseguire una seconda build in una diversa dalla prima
        res= decoratedTurn.build(player,game.getBoard(),worker,game.getBoard().getPosition(0,1),false);
        assertEquals(false, res);
        assertEquals(2, game.getBoard().getPosition(0,1).getLevel());


        //tentativo di eseguire una seconda build sulla stessa pos della prima ma implicando la costruzione di una dome
        res= decoratedTurn.build(player,game.getBoard(),worker,game.getBoard().getPosition(0,0),false);
        assertEquals(false, res);
        assertEquals(3, game.getBoard().getPosition(0,0).getLevel());



    }


}
