package it.polimi.ingsw.Server.Model.Decorator.MultipleMove;

import it.polimi.ingsw.Server.Model.Decorator.SimpleTurn;
import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.Root.Game;
import it.polimi.ingsw.Server.Model.Root.Player;
import it.polimi.ingsw.Server.Model.Root.Worker;
import it.polimi.ingsw.Server.Parser.ParserJson;
import it.polimi.ingsw.Server.RemoteView.RemoteView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OnBorderOneMoreMoveTest {


    Game sceneOfGame() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson = new ParserJson(3);
        Game game = new Game(3, true, parserJson,remoteView);
        game.addPlayer("Marc", 24);

        for (Player player : game.getPlayers())
            player.assignWorker(game.getPlayers().indexOf(player));
        //posizione di tutti i worker
        game.getPlayers().get(0).getWorkers().get(0).setPosition(game.getBoard().getPosition(2, 3));
        game.getBoard().getPosition(2, 3).setOccupiedBy(game.getPlayers().get(0).getWorkers().get(0));


        return game;
    }


    //muovo worker da una pos iniziale ad una pos perimetrale e cerco di muoverlo ancora --> è concesso
    //muovo worker da una pos iniziale ad un pos non perimetrale e cerco di muoverlo ancora --> non è concesso
    @Test
    void checkPossibleMoveTest() {

        boolean res;
        Game game = sceneOfGame();
        Turn turn = new SimpleTurn();
        Turn decoratedTurn = new OnBorderOneMoreMove(turn);

        Player player = game.getPlayers().get(0);
        player.setMyTurn(decoratedTurn);

        Worker worker = game.getBoard().getPosition(2, 3).getOccupiedBy();
        worker.setMoveNum(0);
        decoratedTurn.startTurn(game.getPlayers().get(0));


        //tentativo di eseguire la prima mossa verso una pos perimetrale
        res = decoratedTurn.move(player, game.getBoard(), worker, game.getBoard().getPosition(2, 4));
        assertEquals(true, res);
        assertEquals(1,worker.getMoveNum());
        System.out.println("primo test passato \n\n\n");


        //tentativo di eseguire una seconda mossa verso una pos perimetrale
        res = decoratedTurn.move( player, game.getBoard(), worker,  game.getBoard().getPosition(1,4));
        assertEquals(true, res);
        assertEquals(1,worker.getMoveNum());
        System.out.println("secondo test passato \n\n\n");


        //tentativo di eseguire una seconda mossa da una pos perimetrale ad una non
        res = decoratedTurn.move( player, game.getBoard(), worker,  game.getBoard().getPosition(0,4));
        assertEquals(true, res);
        assertEquals(1,worker.getMoveNum());
        System.out.println("terzo test passato \n\n\n");


        res = decoratedTurn.move( player, game.getBoard(), worker,  game.getBoard().getPosition(1,3));
        assertEquals(true, res);
        assertEquals(1,worker.getMoveNum());
        System.out.println("quarto test passato \n\n\n");


        //tentativo di eseguire una terza mossa dato che la mossa precedente non era verso una pos perimetrale
        res = decoratedTurn.move( player, game.getBoard(), worker,  game.getBoard().getPosition(0,3) );
        assertEquals(false, res);
        assertEquals(1,worker.getMoveNum());
        System.out.println("quinto test passato \n\n\n");

        //tentativo di costruzione
        res = decoratedTurn.build( player, game.getBoard(), worker, game.getBoard().getPosition(1,2), false );
        assertEquals(true, res);
        assertEquals(true, player.isMyTurnOver());



    }




}

