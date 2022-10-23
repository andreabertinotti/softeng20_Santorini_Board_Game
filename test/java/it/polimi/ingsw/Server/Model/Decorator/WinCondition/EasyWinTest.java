package it.polimi.ingsw.Server.Model.Decorator.WinCondition;


import it.polimi.ingsw.Server.Model.Decorator.SimpleTurn;
import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Root.Game;
import it.polimi.ingsw.Server.Model.Root.Player;
import it.polimi.ingsw.Server.Model.Root.Worker;
import it.polimi.ingsw.Server.Parser.ParserJson;
import it.polimi.ingsw.Server.RemoteView.RemoteView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EasyWinTest {


    Game sceneOfGame() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(3);
        Game game = new Game(3, true, parserJson, remoteView);
        game.addPlayer("Marc", 24);

        for (Player player : game.getPlayers())
            player.assignWorker(game.getPlayers().indexOf(player));
        //posizione di tutti i worker
        game.getPlayers().get(0).getWorkers().get(0).setPosition(game.getBoard().getPosition(0, 0));
        game.getBoard().getPosition(0, 0).setOccupiedBy(game.getPlayers().get(0).getWorkers().get(0));


        game.getBoard().getPosition(0,0).setLevel(2);
        game.getBoard().getPosition(1,0).setLevel(1);
        game.getBoard().getPosition(1,1).setLevel(3);



        return game;
    }



    @Test
    void isWinnerTest() {

        boolean res;
        Game game = sceneOfGame();
        Turn turn = new SimpleTurn();
        Turn decoratedTurn = new EasyWin(turn);

        Player player = game.getPlayers().get(0);
        player.setMyTurn(decoratedTurn);

        Worker worker = game.getBoard().getPosition(0, 0).getOccupiedBy();
        decoratedTurn.startTurn(game.getPlayers().get(0));


        //scendo di una pos
        System.out.println(worker.getPosition().getCoordX() + "x    " + worker.getPosition().getCoordY()+ "y   ");
        System.out.println(worker.getPosition().getLevel() + "oldpos level\n");
        System.out.println("\n\n\n");
        decoratedTurn.move(player, game.getBoard(), worker, game.getBoard().getPosition(1, 0));

        System.out.println(worker.getPosition().getCoordX() + "x    " + worker.getPosition().getCoordY()+ "y   ");
        System.out.println(worker.getPosition().getLevel() + "newPos level\n");

        System.out.println("\n\n");
        res = decoratedTurn.isWinner(worker.getOldPosition(), worker.getPosition(), player, game.getBoard());
        assertEquals(false, res);
        assertEquals(false, player.getIsWinner());
        System.out.println("primo test\n\n\n");


        game.getPlayers().get(0).getWorkers().get(0).setPosition(game.getBoard().getPosition(0, 0));
        game.getBoard().getPosition(0, 0).setOccupiedBy(game.getPlayers().get(0).getWorkers().get(0));
        worker.setMoveNum(0);


        //scendo di due pos
        decoratedTurn.move(player, game.getBoard(), worker, game.getBoard().getPosition(0, 1));
        res = decoratedTurn.isWinner(worker.getOldPosition(), worker.getPosition(), player, game.getBoard());
        assertEquals(true, res);
        assertEquals(true, player.getIsWinner());
        System.out.println("secondo test\n\n\n");


        game.getPlayers().get(0).getWorkers().get(0).setPosition(game.getBoard().getPosition(0, 0));
        game.getBoard().getPosition(0, 0).setOccupiedBy(game.getPlayers().get(0).getWorkers().get(0));
        worker.setMoveNum(0);


        //salgo di una pos
        decoratedTurn.move(player, game.getBoard(), worker, game.getBoard().getPosition(1, 1));
        res = decoratedTurn.isWinner(worker.getOldPosition(), worker.getPosition(), player, game.getBoard());
        assertEquals(true, res);
        assertEquals(true, player.getIsWinner());
        System.out.println("terzo test\n\n\n");

    }

}
