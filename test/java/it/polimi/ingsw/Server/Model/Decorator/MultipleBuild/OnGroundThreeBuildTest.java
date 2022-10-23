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

public class OnGroundThreeBuildTest {


    Game sceneOfGame() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(3);
        Game game = new Game(3,true, parserJson, remoteView);
        game.addPlayer("Marc", 24);

        for (Player player : game.getPlayers())
            player.assignWorker(game.getPlayers().indexOf(player));
        //posizione di tutti i worker
        game.getPlayers().get(0).getWorkers().get(0).setPosition(game.getBoard().getPosition(1, 1));
        game.getBoard().getPosition(1, 1).setOccupiedBy(game.getPlayers().get(0).getWorkers().get(0));
        game.getPlayers().get(0).getWorkers().get(1).setPosition(game.getBoard().getPosition(1, 3));
        game.getBoard().getPosition(1, 3).setOccupiedBy(game.getPlayers().get(0).getWorkers().get(1));


        //settaggio delle costruzioni sulla board
        game.getBoard().getPosition(0, 0).setLevel(2);
        game.getBoard().getPosition(0, 1).setLevel(2);
        game.getBoard().getPosition(1, 0).setLevel(1);
        game.getBoard().getPosition(1, 1).setLevel(0);


        return game;
    }


    @Test
    void checkPossibleBuildingTest() {

        boolean res;
        Game game = sceneOfGame();
        Turn turn = new SimpleTurn();
        Turn decoratedTurn = new OnGroundThreeBuild(turn);

        Player player = game.getPlayers().get(0);
        player.setMyTurn(decoratedTurn);

        Worker worker = player.getWorkers().get(0);
        Worker worker1 = player.getWorkers().get(1);
        decoratedTurn.startTurn(game.getPlayers().get(0));


        assertEquals(true, game.getBoard().getPosition(1,4).isEmpty());
        assertEquals(false, game.getBoard().getPosition(1,4).hasDome());
        assertEquals(true, game.getBoard().isNear(worker1.getPosition(), game.getBoard().getPosition(1,4)));

        res = decoratedTurn.move(player, game.getBoard(), worker1, game.getBoard().getPosition(1, 4));
        assertEquals(true, res);
        assertEquals(1, worker1.getMoveNum());

        res = decoratedTurn.build(player, game.getBoard(), worker1, game.getBoard().getPosition(1, 3), false);
        assertEquals(1, game.getBoard().getPosition(1,3).getLevel());
        assertEquals(true, game.getBoard().getPosition(1,3).isEmpty());
        assertEquals(true, res);
        assertEquals(1, worker1.getBuildNum());

        //tentativo di costruire con il worker che ha già mosso e costruito
        res = decoratedTurn.build(player, game.getBoard(), worker1, game.getBoard().getPosition(0, 4), false);
        assertEquals(false, res);
        assertEquals(0, game.getBoard().getPosition(0, 4).getLevel());
        assertEquals(1, worker1.getBuildNum());

        //tentativo di costruire con il worker non mosso che si trova al livello 0
        res = decoratedTurn.build(player, game.getBoard(), worker, game.getBoard().getPosition(1, 0), false);
        assertEquals(true, res);
        assertEquals(2, game.getBoard().getPosition(1, 0).getLevel());
        assertEquals(1, worker.getBuildNum());

        //tentativo di costruire con il worker non mosso che si trova al livello 0 la seconda volta
        res = decoratedTurn.build(player, game.getBoard(), worker, game.getBoard().getPosition(1, 0), false);
        assertEquals(true, res);
        assertEquals(3, game.getBoard().getPosition(1, 0).getLevel());
        assertEquals(2, worker.getBuildNum());


        //tentativo di costruire con il worker non mosso che si trova al livello 0 la terza volta
        res = decoratedTurn.build(player, game.getBoard(), worker, game.getBoard().getPosition(0, 0), false);
        assertEquals(true, res);
        assertEquals(3, game.getBoard().getPosition(0, 0).getLevel());
        assertEquals(3, worker.getBuildNum());

        //tentativo di costruire con il worker non mosso che si trova al livello 0 la quarta volta
        res = decoratedTurn.build(player, game.getBoard(), worker, game.getBoard().getPosition(0, 1), false);
        assertEquals(false, res);
        assertEquals(2, game.getBoard().getPosition(0, 1).getLevel());
        assertEquals(3, worker.getBuildNum());
        assertEquals(true, player.isMyTurnOver());


        game.getBoard().getPosition(1, 0).setLevel(1);
        game.getBoard().getPosition(1, 1).setLevel(2);
        decoratedTurn.startTurn(game.getPlayers().get(0));
        decoratedTurn.move(player, game.getBoard(), worker1, game.getBoard().getPosition(1, 4));
        decoratedTurn.build(player, game.getBoard(), worker1, game.getBoard().getPosition(1, 3), false);


        //tentativo di costruire con il worker non mosso che si trova al livello 2
        res = decoratedTurn.build(player, game.getBoard(), worker, game.getBoard().getPosition(1, 0), false);
        assertEquals(false, res);
        assertEquals(1, game.getBoard().getPosition(1, 0).getLevel());
        assertEquals(0, worker.getBuildNum());
        assertEquals(true, player.isMyTurnOver());



    }

    //non testato

    @Test
    void checkBuildFlowTest(){


        boolean res;
        Game game = sceneOfGame();
        Turn turn = new SimpleTurn();
        Turn decoratedTurn = new OnGroundThreeBuild(turn);

        Player player = game.getPlayers().get(0);
        player.setMyTurn(decoratedTurn);

        Worker worker = player.getWorkers().get(0);
        Worker worker1 = player.getWorkers().get(1);
        decoratedTurn.startTurn(game.getPlayers().get(0));

        worker1.setMoveNum(1);

        //tentativo costruire con worker al livello zero quando è già stato mosso l'altro worker ma non ha costruito
        res = decoratedTurn.build(player, game.getBoard(), worker, game.getBoard().getPosition(1, 0), false);
        assertEquals(false, res);
        assertEquals(0, worker.getBuildNum());

        worker1.setBuildNum(1);
        //tentativo di costruire con worker al livello zero dopo che l'altro worker ha mosso e costruito
        res = decoratedTurn.build(player, game.getBoard(), worker, game.getBoard().getPosition(1, 0), false);
        assertEquals(true, res);
        assertEquals(1, worker.getBuildNum());

    }

}
