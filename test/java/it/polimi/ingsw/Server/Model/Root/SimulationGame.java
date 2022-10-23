package it.polimi.ingsw.Server.Model.Root;

public class SimulationGame {
/*
    Game prepareGame() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson3 = new ParserJson(3);
        Game game = new Game(3, true, parserJson3, remoteView);
        game.addPlayer("Marc", 26);
        game.addPlayer("Alexander", 18);
        game.addPlayer("Johnny", 22);
        return game;
    }

    Game prepareGameOnlyTwo() {
        RemoteView remoteView = new RemoteView();
        ParserJson parserJson3 = new ParserJson(2);
        Game game = new Game(2, true, parserJson3, remoteView);
        game.addPlayer("Marc", 26);
        game.addPlayer("Alexander", 18);
        return game;
    }

    void positionWorker(Game game) {
        game.setWorkersInitialPosition(1,1,3,3); //Johnny RED
        game.setWorkersInitialPosition(2,2,4,2); //Marc GREEN
        game.setWorkersInitialPosition(4,0,2,3); //Alexander YELLOW
        for (Player player : game.getPlayers())
            for (Worker worker : player.getWorkers())
                assertNotNull(worker.getPosition());
    }

    void positionWorkerOnlyTwo(Game game) {
        game.setWorkersInitialPosition(2,2,4,2); //Marc GREEN
        game.setWorkersInitialPosition(4,0,2,3); //Alexander YELLOW
        for (Player player : game.getPlayers())
            for (Worker worker : player.getWorkers())
                assertNotNull(worker.getPosition());
    }

    @Test
    void ApolloArtemisAthenaTest() {
        Game game = prepareGame();

        ArrayList<String> divinityChosen = new ArrayList<>(Arrays.asList("Apollo", "Artemis", "Athena"));
        game.challengerChooseCards(divinityChosen);

        game.setPlayerDivinity("Apollo");
        game.setPlayerDivinity("Athena");
        for (Player player : game.getPlayers())
            assertNotNull(player.getCard());

        //Marc --> Artemis
        //Alexander --> Apollo
        //Johnny --> Athena
        game.chooseFirst("Johnny");
        positionWorker(game);

        //Turno di Johnny
        assertEquals(true, game.selectWorker(1, Colour.RED));
        assertEquals(game.getCurrentPlayer().getSelectedWorker(), game.getCurrentPlayer().getWorkers().get(0));

        assertEquals(true, game.move(2,1));

        assertEquals(false, game.build(2,2,false));
        assertEquals(true, game.build(1,1,false));

        //Turno di Marc
        assertEquals(true, game.selectWorker(1, Colour.GREEN));

        assertEquals(false, game.move(2,1));
        assertEquals(true, game.move(1,2));
        assertEquals(true, game.move(0,1));
        assertEquals(false, game.move(0,0));

        assertEquals(true, game.build(1,2,false));

        //Turno di Alexander
        assertEquals(true, game.selectWorker(2, Colour.YELLOW));

        assertEquals(true, game.move(3,3));

        assertEquals(true, game.build(3,2, false));

        //Turno di Johnny
        assertEquals(true, game.selectWorker(2, Colour.RED));

        assertEquals(true, game.move(3,2));

        assertEquals(true, game.build(3,1, false));

        //Turno di Marc
        assertEquals(true, game.selectWorker(1, Colour.GREEN));

        assertEquals(false, game.move(1,1));
        assertEquals(true, game.move(0,2));

        assertEquals(true, game.build(0,1, false));
    }

    @Test
    void AtlasDemeterHephaestusTest() {
        Game game = prepareGame();

        ArrayList<String> divinityChosen = new ArrayList<>(Arrays.asList("Atlas", "Demeter", "Hephaestus"));
        game.challengerChooseCards(divinityChosen);

        game.setPlayerDivinity("Atlas");
        game.setPlayerDivinity("Hephaestus");
        for (Player player : game.getPlayers()) {
            assertNotNull(player.getCard());
        }

        //Marc --> Demeter
        //Alexander --> Atlas
        //Johnny --> Hephaestus
        game.chooseFirst("Johnny");
        positionWorker(game);

        //Turno di Johnny
        assertEquals(true, game.selectWorker(2, Colour.RED));

        assertEquals(false, game.move(2,3));
        assertEquals(true, game.move(3,2));

        assertEquals(true, game.build(3,1, false));
        assertEquals(false, game.build(4,1, false));
        assertEquals(false, game.build(4,1, false));
        assertEquals(true, game.build(3,1, false));

        //Turno di Marc
        assertEquals(true, game.selectWorker(1, Colour.GREEN));

        assertEquals(true, game.move(1,3));

        assertEquals(true, game.build(0,4, false));
        assertEquals(false, game.build(0,4, false));
        assertEquals(true, game.build(1,2, false));

        //Turno di Alexander
        assertEquals(true, game.selectWorker(2, Colour.YELLOW));

        assertEquals(true, game.move(2,2));

        assertEquals(true, game.build(1,2, true));
    }

    @Test
    void MinotaurPanPrometheusTest() {
        Game game = prepareGame();

        ArrayList<String> divinityChosen = new ArrayList<>(Arrays.asList("Minotaur", "Pan", "Prometheus"));
        game.challengerChooseCards(divinityChosen);

        game.setPlayerDivinity("Minotaur");
        game.setPlayerDivinity("Pan");
        for (Player player : game.getPlayers()) {
            assertNotNull(player.getCard());
        }

        //Marc --> Prometheus
        //Alexander --> Minotaur
        //Johnny --> Pan
        game.chooseFirst("Johnny");
        positionWorker(game);

        //Turno di Johnny
        assertEquals(true, game.selectWorker(2, Colour.RED));

        assertEquals(true, game.move(3,4));

        assertEquals(true, game.build(3,3, false));

        //Turno di Marc
        assertEquals(true, game.selectWorker(1, Colour.GREEN));

        assertEquals(true, game.build(3,1, false));

        assertEquals(true, game.move(3,2));

        assertEquals(true, game.build(3,1, false));

        //Turno di Alexander
        assertEquals(true, game.selectWorker(2, Colour.YELLOW));

        assertEquals(true, game.move(3,2));

        assertEquals(true, game.build(3,1, false));
    }

    @Test
    void TritonHestiaPoseidonTest() {
        Game game = prepareGame();

        ArrayList<String> divinityChosen = new ArrayList<>(Arrays.asList("Triton", "Hestia", "Poseidon"));
        game.challengerChooseCards(divinityChosen);

        game.setPlayerDivinity("Triton");
        game.setPlayerDivinity("Hestia");
        for (Player player : game.getPlayers())
            assertNotNull(player.getCard());
        //Marc --> Poseidon
        //Alexander --> Triton
        //Johnny --> Hestia
        game.chooseFirst("Johnny");
        positionWorker(game);

        //Turno di Johnny
        assertEquals(true, game.selectWorker(2, Colour.RED));

        assertEquals(true, game.move(4,3));

        assertEquals(true, game.build(3,3, false));
        assertEquals(false, game.build(4,4, false));
        assertEquals(true, game.build(3,3, false));

        //Turno di Marc
        assertEquals(true, game.selectWorker(1, Colour.GREEN));

        assertEquals(true, game.move(1,2));

        assertEquals(true, game.build(1,3, false));

        assertEquals(true, game.selectWorker(2, Colour.GREEN));
        assertEquals(true, game.build(3,1, false));
        assertEquals(true, game.build(3,1, false));
        assertEquals(true, game.build(3,1, false));

        //Turno di Alexander
        assertEquals(true, game.selectWorker(2, Colour.YELLOW));

        assertEquals(true, game.move(2,4));
        assertEquals(true, game.move(1,4));
        assertEquals(true, game.move(0,4));
        assertEquals(true, game.move(1,3));
        assertEquals(false, game.move(0,3));

        assertEquals(true, game.build(0,3, false));
    }

    @Test
    void ChronusZeusTest() {
        Game game = prepareGameOnlyTwo();

        ArrayList<String> divinityChosen = new ArrayList<>(Arrays.asList("Chronus", "Zeus"));
        game.challengerChooseCards(divinityChosen);

        game.setPlayerDivinity("Chronus");
        for (Player player : game.getPlayers())
            assertNotNull(player.getCard());
        //Marc --> Zeus
        //Alexander --> Chronus
        game.chooseFirst("Marc");
        positionWorkerOnlyTwo(game);

        //Turno di Marc
        assertEquals(true, game.selectWorker(2, Colour.GREEN));

        assertEquals(true, game.move(4,3));

        assertEquals(true, game.build(4,2, false));

        //Turno di Alexander
        assertEquals(true, game.selectWorker(2, Colour.YELLOW));

        assertEquals(true, game.move(3,3));

        assertEquals(true, game.build(2,3, false));

        game.getBoard().getPosition(4,2).setLevel(2);
        game.getBoard().getPosition(4,3).setLevel(1);

        //Turno di Marc
        assertEquals(true, game.selectWorker(2, Colour.GREEN));

        assertEquals(true, game.move(4,2));

        assertEquals(true, game.build(4,2, false));

        game.getBoard().getPosition(0,0).setLevel(3);
        game.getBoard().getPosition(0,0).setDome(true);
        game.getBoard().getPosition(0,1).setLevel(3);
        game.getBoard().getPosition(0,1).setDome(true);
        game.getBoard().getPosition(0,2).setLevel(3);
        game.getBoard().getPosition(0,2).setDome(true);
        game.getBoard().getPosition(0,3).setLevel(3);
        game.getBoard().getPosition(0,3).setDome(true);
        game.getBoard().getPosition(4,1).setLevel(3);

        //Turno di Alexander
        assertEquals(true, game.selectWorker(1, Colour.YELLOW));

        assertEquals(true, game.move(3,0));

        assertEquals(true, game.build(4,1, true));
    }

 */
}
