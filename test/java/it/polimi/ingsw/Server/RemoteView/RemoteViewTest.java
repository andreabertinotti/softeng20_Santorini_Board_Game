package it.polimi.ingsw.Server.RemoteView;

import org.junit.jupiter.api.Test;

public class RemoteViewTest {


    @Test
    void RemoteViewInteroperationalTest1() {

/*
        RemoteView remoteView = new RemoteView();
        remoteView.setTesting(true);
        remoteView.newGameRequest(3, true);
        remoteView.addPlayerRequest("Luca", 12);
        remoteView.addPlayerRequest("Arianna", 23);
        remoteView.addPlayerRequest("Matteo", 16);
        //cards chosen by the challenger
        ArrayList<String> challengerDeck = new ArrayList<>();
        challengerDeck.add("Apollo");
        challengerDeck.add("Artemis");
        challengerDeck.add("Athena");
        remoteView.challengerChoseCardsRequest(challengerDeck);
        remoteView.setCardChosenRequest("Artemis");
        remoteView.setCardChosenRequest("Athena");
        remoteView.challengerChoseFirstRequest("Matteo");
        remoteView.setWorkersPositionRequest(1, 1, 2, 2);
        remoteView.setWorkersPositionRequest(1, 2, 2, 3);
        remoteView.setWorkersPositionRequest(1, 3, 3, 3);
        //end of game's set up
        remoteView.selectWorkerRequest(1, Colour.RED);
        remoteView.possibilityChosenRequest(true, false, false);
        remoteView.moveRequest(0, 1);
        remoteView.selectWorkerRequest(1, Colour.RED);
        remoteView.possibilityChosenRequest(false, true, false);
        remoteView.buildRequest(0, 0, false);
        remoteView.selectWorkerRequest(1, Colour.GREEN);
        remoteView.possibilityChosenRequest(false, false, true);
        remoteView.possibilityChosenRequest(false, false, false);
        remoteView.possibilityChosenRequest(true, false, false);
        remoteView.moveRequest(0, 2);
        remoteView.selectWorkerRequest(2, Colour.GREEN);
        remoteView.selectWorkerRequest(2, Colour.RED);
        remoteView.selectWorkerRequest(1, Colour.GREEN);
        remoteView.possibilityChosenRequest(false, true, false);
        remoteView.buildRequest(0, 3, true);
        remoteView.buildRequest(0, 3, false);
        remoteView.selectWorkerRequest(1, Colour.RED);
        remoteView.selectWorkerRequest(1, Colour.YELLOW);
        //no option selected
        remoteView.possibilityChosenRequest(false, false, false);
        //to many possibilities chosen
        remoteView.possibilityChosenRequest(true, true, true);
        //right possibility selected
        remoteView.possibilityChosenRequest(true, false, false);
        //building when expected to move
        remoteView.buildRequest(0, 2, false);
        //moving on an non valid position
        remoteView.moveRequest(0, 1);
        remoteView.moveRequest(2, 2);
        //selecting wrong worker
        remoteView.selectWorkerRequest(2, Colour.YELLOW);
        remoteView.selectWorkerRequest(1, Colour.YELLOW);
        remoteView.possibilityChosenRequest(false, true, false);
        remoteView.buildRequest(1, 1, false);


    }


    @Test
    void RemoteViewInteroperationalTest2() {


        RemoteView remoteView = new RemoteView();
        remoteView.setTesting(true);
        //attempt to create a game with too many players
        remoteView.newGameRequest(4, true);
        remoteView.newGameRequest(2, true);
        remoteView.addPlayerRequest("Luca", 12);
        remoteView.addPlayerRequest("Arianna", 23);
        //attempt to add a third player on a 2 players game
        remoteView.addPlayerRequest("Matteo", 16);
        //cards chosen by the challenger
        ArrayList<String> challengerDeck = new ArrayList<>();
        challengerDeck.add("Apollo");
        challengerDeck.add("Artemis");
        challengerDeck.add("Athena");
        //attempting to create a deck (chosen by the challenger) with too many cards
        remoteView.challengerChoseCardsRequest(challengerDeck);
        ArrayList<String> challengerDeck1 = new ArrayList<>();
        challengerDeck1.add("Apollo");
        challengerDeck1.add("Artemis");
        remoteView.challengerChoseCardsRequest(challengerDeck1);
        //Luca get the divinity artemis
        remoteView.setCardChosenRequest("Artemis");
        //Arianna is given the remaining divinity
        //challenger chooses an non valid first player
        remoteView.challengerChoseFirstRequest("Matteo");
        remoteView.challengerChoseFirstRequest("Luca");
        //what happens if this command is launched when the player should position its workers?
        //remoteView.possibilityChosenRequest(true,false,false);
        //TODO it crashes!! something to fix
        remoteView.setWorkersPositionRequest(0, 0, 2, 2);
        remoteView.setWorkersPositionRequest(1, 1, 3, 3);
        remoteView.selectWorkerRequest(1, Colour.GREEN);
        remoteView.possibilityChosenRequest(true, false, false);
        remoteView.moveRequest(1, 0);
        //attempt to use artemis power (move twice to a different position)
        remoteView.selectWorkerRequest(1, Colour.GREEN);
        remoteView.possibilityChosenRequest(true, false, false);
        remoteView.moveRequest(2, 0);
        //only possibility has to be build
        remoteView.selectWorkerRequest(1, Colour.GREEN);
        remoteView.possibilityChosenRequest(false, true, true);
        remoteView.buildRequest(2, 1, false);
        //Luca's turn it's forced to end
        remoteView.selectWorkerRequest(2, Colour.YELLOW);
        remoteView.possibilityChosenRequest(true, false, false);
        //attempt to use apollo's power swapping a worker
        //the swapped worker's position has to be notified
        remoteView.moveRequest(2, 2);
        remoteView.selectWorkerRequest(2, Colour.YELLOW);
        remoteView.buildRequest(3, 1, false);
        //change turn
        remoteView.selectWorkerRequest(1, Colour.GREEN);
        remoteView.possibilityChosenRequest(true, false, false);
        remoteView.moveRequest(2, 1);
        remoteView.selectWorkerRequest(1, Colour.GREEN);
        remoteView.possibilityChosenRequest(true, false, false);
        remoteView.moveRequest(3, 1);
        remoteView.selectWorkerRequest(1, Colour.GREEN);
        remoteView.possibilityChosenRequest(false, true, false);
        remoteView.buildRequest(2, 1, false);
        //change turn
        remoteView.selectWorkerRequest(1, Colour.YELLOW);
        remoteView.possibilityChosenRequest(true, false, false);
        remoteView.moveRequest(1, 2);
        remoteView.selectWorkerRequest(21, Colour.YELLOW);
        remoteView.possibilityChosenRequest(false, true, false);
        remoteView.buildRequest(1, 1, false);
        //change turn
        remoteView.selectWorkerRequest(1, Colour.GREEN);
        remoteView.possibilityChosenRequest(true, false, false);
        remoteView.moveRequest(2, 1);
        remoteView.selectWorkerRequest(1, Colour.GREEN);
        remoteView.possibilityChosenRequest(false, true, false);
        remoteView.buildRequest(1, 1, false);
        //change turn
        remoteView.selectWorkerRequest(1, Colour.YELLOW);
        remoteView.possibilityChosenRequest(true, false, false);
        remoteView.moveRequest(0, 2);
        remoteView.selectWorkerRequest(1, Colour.YELLOW);
        remoteView.possibilityChosenRequest(false, true, false);
        remoteView.buildRequest(1, 1, false);
        //change turn
        remoteView.selectWorkerRequest(1, Colour.GREEN);
        remoteView.possibilityChosenRequest(true, false, false);
        remoteView.moveRequest(1, 1);
        //Luca has won!

    }


    @Test
    void RemoteViewInteroperationalTest3() {


        RemoteView remoteView = new RemoteView();
        remoteView.setTesting(true);
        //attempt to create a game with too many players
        remoteView.newGameRequest(4, true);
        remoteView.newGameRequest(2, true);
        remoteView.addPlayerRequest("Luca", 12);
        remoteView.addPlayerRequest("Arianna", 23);
        //attempt to add a third player on a 2 players game
        remoteView.addPlayerRequest("Matteo", 16);
        //cards chosen by the challenger
        ArrayList<String> challengerDeck = new ArrayList<>();
        challengerDeck.add("Apollo");
        challengerDeck.add("Artemis");
        challengerDeck.add("Athena");
        //attempting to create a deck (chosen by the challenger) with too many cards
        remoteView.challengerChoseCardsRequest(challengerDeck);
        ArrayList<String> challengerDeck1 = new ArrayList<>();
        challengerDeck1.add("Atlas");
        challengerDeck1.add("Artemis");
        remoteView.challengerChoseCardsRequest(challengerDeck1);
        //Luca get the divinity artemis
        remoteView.setCardChosenRequest("Artemis");
        //Arianna is given the remaining divinity
        //challenger chooses an non valid first player
        remoteView.challengerChoseFirstRequest("Matteo");
        remoteView.challengerChoseFirstRequest("Luca");
        //what happens if this command is launched when the player should position its workers?
        remoteView.possibilityChosenRequest(true,false,false);
        remoteView.setWorkersPositionRequest(0, 0, 2, 0);
        remoteView.setWorkersPositionRequest(0, 2, 1, 2);
        remoteView.selectWorkerRequest(2, Colour.GREEN);
        remoteView.possibilityChosenRequest(true, false, false);
        remoteView.moveRequest(1, 0);
        remoteView.selectWorkerRequest(2, Colour.GREEN);
        remoteView.possibilityChosenRequest(false, true, false);
        remoteView.buildRequest(2, 0, false);
        //change turn
        remoteView.selectWorkerRequest(2, Colour.YELLOW);
        remoteView.possibilityChosenRequest(true, false, false);
        remoteView.moveRequest(1, 1);
        remoteView.selectWorkerRequest(2, Colour.YELLOW);
        remoteView.possibilityChosenRequest(false, true, false);
        remoteView.buildRequest(2, 0, true);
        remoteView.selectWorkerRequest(2,Colour.GREEN);
        remoteView.possibilityChosenRequest(true,false,false);
        remoteView.moveRequest(0,1);
        remoteView.selectWorkerRequest(2,Colour.GREEN);
        remoteView.buildRequest(1,2,false);
        //change turn
        remoteView.selectWorkerRequest(2,Colour.YELLOW);
        remoteView.possibilityChosenRequest(true,false,false);
        remoteView.moveRequest(2,1);
        remoteView.selectWorkerRequest(2,Colour.YELLOW);
        remoteView.buildRequest(1,1,true);
        //change turn
        remoteView.selectWorkerRequest(2,Colour.GREEN);
        remoteView.possibilityChosenRequest(true,false,false);
        remoteView.moveRequest(1,0);
        remoteView.selectWorkerRequest(2,Colour.GREEN);
        remoteView.possibilityChosenRequest(false,true,false);
        remoteView.buildRequest(0,1,false);
        //change turn
        remoteView.selectWorkerRequest(1,Colour.YELLOW);
        remoteView.possibilityChosenRequest(true,false,false);
        remoteView.moveRequest(1,2);
        remoteView.selectWorkerRequest(1,Colour.YELLOW);
        remoteView.possibilityChosenRequest(false,true,false);
        remoteView.buildRequest(0,1,true);
        //Luca should have lost



    }

    @Test
    void RemoteViewInteroperationalTest4() {


        RemoteView remoteView = new RemoteView();
        remoteView.setTesting(true);
        //attempt to create a game with too many players
        remoteView.newGameRequest(4, true);
        remoteView.newGameRequest(2, true);
        remoteView.addPlayerRequest("Luca", 12);

        //attempt to add a player with the same nickname
        remoteView.addPlayerRequest("Luca", 23);
        //second player added
        remoteView.addPlayerRequest("Arianna", 23);
        //game is full so it automatically select the challenger and asks him to chose a deck of divinities
        //attempt to add a third player on a 2 players game
        remoteView.addPlayerRequest("Matteo", 16);
        remoteView.moveRequest(1,2);
        //cards chosen by the challenger
        ArrayList<String> challengerDeck = new ArrayList<>();
        challengerDeck.add("123");
        challengerDeck.add("Artemis");
        //attempting to create a deck (chosen by the challenger) mal formed
        remoteView.challengerChoseCardsRequest(challengerDeck);
        ArrayList<String> challengerDeck1 = new ArrayList<>();
        challengerDeck1.add("Atlas");
        challengerDeck1.add("Artemis");
        remoteView.challengerChoseCardsRequest(challengerDeck1);
        //Luca try to get the divinity artemis but types it wrongly
        remoteView.setCardChosenRequest("Atemis");
        //Luca gets artemis
        remoteView.setCardChosenRequest("Artemis");
        //Arianna is given the remaining divinity
        //challenger chooses a non valid first player
        remoteView.challengerChoseFirstRequest("Matteo");
        remoteView.challengerChoseFirstRequest("Luca");
        remoteView.setWorkersPositionRequest(0, 0, 2, 0);
        remoteView.setWorkersPositionRequest(0, 2, 1, 2);



 */
    }


}
