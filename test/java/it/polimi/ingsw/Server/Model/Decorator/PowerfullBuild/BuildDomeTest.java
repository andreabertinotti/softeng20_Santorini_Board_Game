package it.polimi.ingsw.Server.Model.Decorator.PowerfullBuild;

import it.polimi.ingsw.Server.Model.Decorator.SimpleTurn;
import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Root.Game;
import it.polimi.ingsw.Server.Model.Root.Player;
import it.polimi.ingsw.Server.Model.Root.Worker;
import it.polimi.ingsw.Server.Parser.ParserJson;
import it.polimi.ingsw.Server.RemoteView.RemoteView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;




public class BuildDomeTest {


    Game sceneOfGame() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(3);
        Game game = new Game(3, true, parserJson, remoteView);
        game.addPlayer("Marc", 24);

        for (Player player : game.getPlayers())
            player.assignWorker(game.getPlayers().indexOf(player));
        //posizione di tutti i worker
        game.getPlayers().get(0).getWorkers().get(0).setPosition(game.getBoard().getPosition(1, 1));
        game.getBoard().getPosition(1, 1).setOccupiedBy(game.getPlayers().get(0).getWorkers().get(0));
        game.getPlayers().get(0).getWorkers().get(1).setPosition(game.getBoard().getPosition(1, 2));
        game.getBoard().getPosition(1, 2).setOccupiedBy(game.getPlayers().get(0).getWorkers().get(1));

        //settaggio delle costruzioni sulla board
        game.getBoard().getPosition(0, 0).setLevel(0);
        game.getBoard().getPosition(0, 1).setLevel(2);
        game.getBoard().getPosition(1, 0).setLevel(2);
        game.getBoard().getPosition(1, 1).setLevel(1);
        game.getBoard().getPosition(1, 2).setLevel(1);
        game.getBoard().getPosition(1, 0).setDome(true);

        return game;
    }


    @Test
    void buildTest() {

        boolean res;
        Game game = sceneOfGame();
        Turn turn = new SimpleTurn();
        Turn decoratedTurn = new BuildDome(turn);

        Player player = game.getPlayers().get(0);
        player.setMyTurn(decoratedTurn);

        Worker worker = game.getBoard().getPosition(1, 1).getOccupiedBy();
        worker.setBuildNum(0);
        decoratedTurn.startTurn(game.getPlayers().get(0));
        worker.setMoveNum(1);

        //tentativo di costruire su una pos in cui c'è già una cupola
        res = decoratedTurn.build(player, game.getBoard(), worker, game.getBoard().getPosition(1, 0), true);
        assertEquals(false, res);
        assertEquals(true, game.getBoard().getPosition(1, 0).hasDome());
        assertEquals(0, worker.getBuildNum());
        assertEquals(2, game.getBoard().getPosition(1, 0).getLevel() );


        //tentativo di costruire una cupola al livello 0
        res = decoratedTurn.build(player, game.getBoard(), worker, game.getBoard().getPosition(0, 0), true);
        assertEquals(true, res);
        assertEquals(true, game.getBoard().getPosition(0, 0).hasDome());
        assertEquals(1, worker.getBuildNum());
        assertEquals(0, game.getBoard().getPosition(0, 0).getLevel() );

        worker.setBuildNum(0);

        //tentativo di costruire una cupola in una pos occupata da un worker
        res = decoratedTurn.build(player, game.getBoard(), worker, game.getBoard().getPosition(1, 2), true);
        assertEquals(false, res);
        assertEquals(false, game.getBoard().getPosition(1, 2).hasDome());
        assertEquals(0, worker.getBuildNum());
        assertEquals(1, game.getBoard().getPosition(1, 2).getLevel() );

        //costruzione normale
        res = decoratedTurn.build(player, game.getBoard(), worker, game.getBoard().getPosition(0, 1), false);
        assertEquals(true, res);
        assertEquals(false, game.getBoard().getPosition(0, 1).hasDome());
        assertEquals(1, worker.getBuildNum());
        assertEquals(3, game.getBoard().getPosition(0, 1).getLevel() );

    }
}
