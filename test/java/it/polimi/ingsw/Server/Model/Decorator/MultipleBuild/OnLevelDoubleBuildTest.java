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

public class OnLevelDoubleBuildTest {

    Game sceneOfGame() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(3);
        Game game = new Game(3,true, parserJson,remoteView);
        game.addPlayer("Marc", 24);

        for (Player player : game.getPlayers())
            player.assignWorker(game.getPlayers().indexOf(player));
        //posizione di tutti i worker
        game.getPlayers().get(0).getWorkers().get(0).setPosition(game.getBoard().getPosition(1, 1));
        game.getBoard().getPosition(1, 1).setOccupiedBy(game.getPlayers().get(0).getWorkers().get(0));
        game.getPlayers().get(0).getWorkers().get(1).setPosition(game.getBoard().getPosition(3, 3));
        game.getBoard().getPosition(3, 3).setOccupiedBy(game.getPlayers().get(0).getWorkers().get(1));


        //settaggio delle costruzioni sulla board
        game.getBoard().getPosition(0, 0).setLevel(0);
        game.getBoard().getPosition(0, 1).setLevel(2);
        game.getBoard().getPosition(1, 0).setLevel(1);
        game.getBoard().getPosition(1, 1).setLevel(0);
        game.getBoard().getPosition(1, 2).setLevel(0);

        return game;
    }


    @Test
    void checkPossibleMoveTest() {

        boolean res;
        Game game = sceneOfGame();
        Turn turn = new SimpleTurn();
        Turn decoratedTurn = new OnLevelDoubleBuild(turn);

        Player player = game.getPlayers().get(0);
        player.setMyTurn(decoratedTurn);

        Worker worker1 = player.getWorkers().get(1);
        Worker worker = game.getBoard().getPosition(1, 1).getOccupiedBy();
        decoratedTurn.startTurn(game.getPlayers().get(0));

        //costruzione prima di muovere  (anche test di checkBuildFlow )
        res = decoratedTurn.build(player,game.getBoard(),worker,game.getBoard().getPosition(0,1),false);
        assertEquals(true, res);
        assertEquals(0, worker.getPosition().getLevel());
        assertEquals(1, worker.getBuildNum());
        assertEquals(0, worker.getMoveNum() );
        assertEquals(false, player.isMyTurnOver());
        assertEquals(true, decoratedTurn.checkBuildFlow(player,worker));

        //tentativo di muovere worker1 avendo costruito con worker prima ( checkMoveFlow test)
        res = decoratedTurn.move(player,game.getBoard(),worker1,game.getBoard().getPosition(3,4));
        assertEquals(false, res);

        //tentativo di salire avendo già costruito all'inteno del turno
        res = decoratedTurn.move(player, game.getBoard(), worker, game.getBoard().getPosition(1, 0));
        assertEquals(1, game.getBoard().getPosition(1,0).getLevel());
        assertEquals(false, res);
        assertEquals(0, game.getBoard().getPosition(1, 1).getLevel());
        assertEquals(1, worker.getBuildNum());
        assertEquals(0, worker.getMoveNum() );


        //tentativo di muovere senza salire avendo già costruito nell'arco del turno
        res = decoratedTurn.move(player, game.getBoard(), worker, game.getBoard().getPosition(1, 2));
        assertEquals(true, res);
        assertEquals(0, game.getBoard().getPosition(1, 1).getLevel());
        assertEquals(1, worker.getBuildNum());
        assertEquals(1, worker.getMoveNum());

        //tentativo di muovere una seconda volta senza salire
        res = decoratedTurn.move(player, game.getBoard(), worker, game.getBoard().getPosition(0, 2));
        assertEquals(false, res);
        assertEquals(1, game.getBoard().getPosition(1, 0).getLevel());
        assertEquals(1, worker.getBuildNum());
        assertEquals(1, worker.getMoveNum());


        //tentativo di costruire dopo aver mosso
        res = decoratedTurn.build(player, game.getBoard(), worker, game.getBoard().getPosition(0, 2), false);
        assertEquals(true, res);
        assertEquals(2, worker.getBuildNum());
        assertEquals(true, player.isMyTurnOver());


        //tentativo di costruire una seconda volta dopo aver mosso
        res = decoratedTurn.build(player, game.getBoard(), worker, game.getBoard().getPosition(1, 3), false);
        assertEquals(false, res);
        assertEquals(2, worker.getBuildNum());


    }

}
