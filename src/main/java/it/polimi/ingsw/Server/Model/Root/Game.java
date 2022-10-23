package it.polimi.ingsw.Server.Model.Root;

import it.polimi.ingsw.shared.Colour;
import it.polimi.ingsw.Server.Model.Decorator.SimpleTurn;
import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Parser.ParserJson;
import it.polimi.ingsw.Server.Model.Root.ObservableFromView.*;
import it.polimi.ingsw.Server.RemoteView.ObserverOfModel.*;
import it.polimi.ingsw.Server.RemoteView.RemoteView;

import java.util.ArrayList;
import java.util.List;

/**
 * this class represent the core of the game
 */
public class Game implements WorkerBuildObservable, WorkerMoveObesrvable, PlayerWinOrLoseObservable, DeckObservable,
        PlayerAttributesObservable, ChallengerObservable, TurnObservable,
        PlayerDecisionObservable, WorkerSelectionObservable, GameCreationObservable, BeginningOfTurnObservable {
    /**
     * this attribute is the player who can play his turn in this moment
     */
    private Player currentPlayer;

    /**
     * this attribute is maximum number of player in a single game
     */
    private int maxPlayers;
    /**
     * this attribute is the list of all players who are in the game
     */
    private ArrayList<Player> players;
    /**
     * this attribute is the game board
     */
    private Board board;
    /**
     * this attribute is the deck of cards created based on the number of players
     */
    private Deck deck;
    /**
     * this attribute is the type of game, with or without divinity card
     */
    private boolean withDivinities;
    /**
     * this attribute is the player who is choose as challenger
     */
    private Player challenger;

    /**
     * This attribute (a define)  is the index of the first worker in the list of workers
     */
    private static final int worker1index = 0;
    /**
     * This attribute (a define)  is the index of the secondo worker in the list of workers
     */
    private static final int worker2index = 1;



    private List<WorkerBuildObserver> workerBuildObserverList;
    private List<WorkerMoveObserver> workerMoveObserverList;
    private List<PlayerWinOrLoseObserver> playerWinOrLoseObserverList;
    private List<DeckObserver> deckObserverList;
    private List<PlayerAttributesObserver> playerAttributesObserverList;
    private List<ChallengerObserver> challengerObserverList;
    private List<TurnObserver> turnObserverList;
    private List<PlayerDecisionObserver> playerDecisionObserverList;
    private List<WorkerSelectionObserver> workerSelectionObserverList;
    private List<GameCreationObserver> gameCreationObserverList;
    private List<BeginningOfTurnObserver> beginningOfTurnObserverList;

    public boolean isWithDivinities() {
        return withDivinities;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Deck getDeck() {
        return deck;
    }

    public Player getChallenger() {
        return challenger;
    }


    //Note: on game creation depending on the parameter withDivinities it will also be created a deck of card (and the view is notified about it)
    //
    /**
     * this method creates new instance of Game with new board, the list of players connected to this game
     * and the deck with all cards usable with this number of player
     *
     * @param nPlayers       is the number of player which play the game
     * @param withDivinities is the choice to play with or without divinity card
     * @param remoteView is the view that is now registered as an observer of the game
     */
    public Game(int nPlayers, boolean withDivinities, ParserJson parserJson, RemoteView remoteView) {
        maxPlayers = nPlayers;
            this.withDivinities = withDivinities;
            board = new Board();
            players = new ArrayList<>();

            //costruzione delle liste di observers
            workerBuildObserverList = new ArrayList<>();
            workerMoveObserverList = new ArrayList<>();
            playerWinOrLoseObserverList = new ArrayList<>();
            playerAttributesObserverList = new ArrayList<>();
            turnObserverList = new ArrayList<>();
            playerDecisionObserverList = new ArrayList<>();
            workerSelectionObserverList = new ArrayList<>();
            gameCreationObserverList = new ArrayList<>();
            beginningOfTurnObserverList = new ArrayList<>();
            deckObserverList = new ArrayList<>();
            challengerObserverList = new ArrayList<>();

            registerView(remoteView);

            if (withDivinities) {
                deck = new Deck(parserJson.getParserCardsInDeck());
                notifyDeckObservers();
            }
            notifyGameCreationObserver();
    }

    /**
     * this method adds a player to list of players connected, it check that the player is in the list only once
     *
     * @param nickname is the name of player
     * @param age      is the age of player
     * @return is the value of ending process, true if it's successful, false if the parameters are malformed or the game is already full
     */
    public boolean addPlayer(String nickname, int age) {
        if (players.size() < maxPlayers) {
            Player player = new Player(nickname, age);
            for (Player players : players) {
                if (players.getNickname().equals(nickname)) {
                    return false;
                }
            }

            if (players.size() > 0) {
                for (Player opponent : players) {
                    player.getOpponents().add(opponent);
                    opponent.getOpponents().add(player);
                }
            }

            players.add(player);
            player.assignWorker(players.indexOf(player));
            currentPlayer = player;
            notifyPlayerAttributesObservers();
            if (!withDivinities) {
                Turn turn = new SimpleTurn();
                player.setMyTurn(turn);
            }

            //if the last player has been added
            if (players.size() == maxPlayers) {
                if (!withDivinities) {
                    //identify the youngest and start its turn
                    currentPlayer = youngestPlayer();
                    notifyTurnObserver("setWorkers");
                }
                else {
                    //the challenger it's chosen
                    chooseChallenger();
                    currentPlayer = challenger;
                    notifyChallengerObservers();
                    notifyTurnObserver("ChooseDeck");
                }
            }
            return true;
        }
        return false;
    }

    /**
     * this method is the opposite of addPlayer, it removes a player from list of players connected
     * @param nickname is the name of player
     * @param age      is the age of player
     * @return is the value of ending process, true if it's successful, else false
     */
    public boolean removePlayer(String nickname, int age) {

        for (Player player : players) {
            if (player.getNickname().equals(nickname) && player.getAge() == age) {
                    for (Worker worker : player.getWorkers())
                        if (worker.getPosition() != null)
                            board.getPosition(worker.getPosition().getCoordX(), worker.getPosition().getCoordY()).setOccupiedBy(null);

                        players.remove(player);
                        notifyPlayerWinOrLoseObservers();
                    return true;
            }

        }

        return false;
    }

    /**
     * this method removes al card that challenger deletes from deck, after this method deck
     * contains only the cards chosen by challenger for this game
     *
     * @param chosenDeck is the card chosen by challenger
     */
    //modificato perchè non gestiva il caso di nomi mal scritti e creava deck con meno carte in quel caso
    //e non gestiva il caso di una chiamata al metodo senza che il challenger fosse stato settato
    public boolean challengerChooseCards(ArrayList<String> chosenDeck) {
        if(challenger!=null) {

                if (chosenDeck.size() == maxPlayers) {
                    ArrayList<DivinityCard> deck = new ArrayList<>();

                    for (DivinityCard card : this.deck.getCardsInDeck()) {
                        if (chosenDeck.contains(card.getName()))
                            deck.add(card);
                    }
                    if (deck.size() == maxPlayers) {
                        this.deck.setCardsInDeck(deck);
                        nextPlayerTurn(true);
                        notifyTurnObserver("ChooseCard");
                        notifyDeckObservers();
                        return true;
                    } else
                        return false;
                } else
                    return false;
        }
        return false;
    }


    /**
     * this method calculate who is the youngest player in this game, if two players has the same age and
     * they are youngest too, the method choose the first one in the list of players
     *
     * @return is the youngest player found in the list
     */
    public Player youngestPlayer() {
        Player youngest = players.get(0);

        for (Player player : players) {
            if (player.getAge() < youngest.getAge())
                youngest = player;
        }
        return youngest;
    }

    /**
     * this method choose a player who will be the challenger using the sum of his age and nickname size,
     * who has the highest result is the challenger
     * @return is the player choose as challenger
     */
    public void chooseChallenger() {

        if(players.size() != 0) {
            Player challenger = players.get(0);
            for (Player player : players) {
                if (player.getAge() + player.getNickname().length() > challenger.getAge() + challenger.getNickname().length())
                    challenger = player;
                else if (player.getAge() + player.getNickname().length() == challenger.getAge() + challenger.getNickname().length())
                    if (player.getAge() > challenger.getAge())
                        challenger = player;
            }
            this.challenger = challenger;
        }

    }

    /**
     * this method is used by challenger to define which player will be first to play
     * @param nickname is the name of player
     */
    public boolean chooseFirst(String nickname) {
        for (Player firstPlayer : players)
            if (firstPlayer.getNickname().equals(nickname)) {
                if(firstPlayer.getNickname().equals(challenger.getNickname()))
                    return false;
                currentPlayer = firstPlayer;
                notifyTurnObserver("setWorkers");
                return true;
            }
        return false;
    }

    /**
     * this method set the divinity card chosen by a single player and create his own turn based on this card
     * @param myDivinityName is the name of divinity chosen by player
     */
    public boolean setPlayerDivinity(String myDivinityName) {
        //aggiunto if per non far settare due volte la carta
        if(currentPlayer.isDivinityAlreadyAssigned()) {
            for (DivinityCard card : deck.getCardsInDeck()) {
                if (card.getName().equals(myDivinityName)) {
                    currentPlayer.setCard(card);
                    currentPlayer.setDivinityAlreadyAssigned(true);
                    notifyPlayerAttributesObservers();
                    currentPlayer.createTurn(currentPlayer.getCard().getEffect()); //inizializza il turno con la specalità
                    deck.removeCard(card);
                    if (deck.getCardsInDeck().size() == 1) {
                        challenger.setCard(deck.getCardsInDeck().get(0));
                        challenger.setDivinityAlreadyAssigned(true);
                        currentPlayer = challenger;
                        notifyPlayerAttributesObservers();
                        challenger.createTurn(challenger.getCard().getEffect()); //inizializza il turno con la specalità
                        deck.removeCard(deck.getCardsInDeck().get(0));
                        notifyTurnObserver("ChooseFirst");
                    } else {
                        nextPlayerTurn(true);
                        notifyTurnObserver("ChooseCard");
                        notifyDeckObservers();
                    }
                    return true;
                }
            }
            return false;
        }return false;
    }

    /**
     * this method initialize the position of workers at the beginning of game
     *
     * @param firstCoordX  is the row of first position chose for worker
     * @param firstCoordY  is the column of first position chose for worker
     * @param secondCoordX is the row of second position chose for worker
     * @param secondCoordY is the column of second position chose for worker
     * @return is 1 if the workers haven't been positioned due to some errors in the parameters, 0 if they have, 2 if they have and the game's initialization is over, 3 if divinities haven't been assigned yet
     */
    public int setWorkersInitialPosition(int firstCoordX, int firstCoordY, int secondCoordX, int secondCoordY) {
        Position firstPos = board.getPosition(firstCoordX, firstCoordY);
        Position secondPos = board.getPosition(secondCoordX, secondCoordY);
        //controllo che posizioni inserite siano valide
    if (firstPos != null && secondPos != null && !firstPos.equals(secondPos)) {
            //controllo che worker non siano già posizionati e che, se la partita lo prevede, le divinità siano già assegnate
            if (currentPlayer.getWorkers().get(worker1index).getPosition() == null && currentPlayer.getWorkers().get(worker2index).getPosition() == null && (currentPlayer.isDivinityAlreadyAssigned() || !withDivinities)) {
                if (firstPos.isEmpty() && secondPos.isEmpty()) {
                    currentPlayer.getWorkers().get(worker1index).setPosition(firstPos);
                    currentPlayer.setSelectedWorker(currentPlayer.getWorkers().get(worker1index));
                    notifyMoveObservers(currentPlayer.getSelectedWorker());
                    board.fillCell(firstPos, currentPlayer.getWorkers().get(worker1index));
                    currentPlayer.getWorkers().get(worker2index).setPosition(secondPos);
                    currentPlayer.setSelectedWorker(currentPlayer.getWorkers().get(worker2index));
                    notifyMoveObservers(currentPlayer.getSelectedWorker());
                    board.fillCell(secondPos, currentPlayer.getWorkers().get(worker2index));
                    nextPlayerTurn(true);
                    if (currentPlayer.getWorkers().get(worker1index).getPosition() == null && currentPlayer.getWorkers().get(worker2index).getPosition() == null)
                        notifyTurnObserver("setWorkers");
                    else notifyTurnObserver(null);
                    return 0;
                } else
                    return 1;
            } else if (currentPlayer.getWorkers().get(worker1index).getPosition() != null && currentPlayer.getWorkers().get(worker2index).getPosition() != null) {
                currentPlayer.getMyTurn().startTurn(currentPlayer);
                notifyBeginningOfTurnObserver();
                //da qui inizia il gioco
                return 2;
            } else
                return 3;
        }
            return 1;
    }

    /**
     * this method will be called at the end of single turn, it update the currentPlayer
     * with the following player in the list, it also check if the player is a loser or not
     *
     * @param initialization is the state of game, true if it's not started and player
     *                       are choosing cards or worker, false during normal turn
     */
    public void nextPlayerTurn(boolean initialization) {
        int indexPlayer = players.indexOf(currentPlayer);

        if (!initialization) {
            currentPlayer.setActiveEffect(false);
            for (Worker worker : currentPlayer.getWorkers()) {
                if (worker.getMoveNum() > 0 && worker.getBuildNum() > 0) {
                    if (indexPlayer < players.size() - 1)
                        indexPlayer++;
                    else indexPlayer = 0;
                    currentPlayer = players.get(indexPlayer);
                    notifyTurnObserver(null);

                    currentPlayer.getMyTurn().startTurn(currentPlayer);
                    notifyBeginningOfTurnObserver();

                    if (currentPlayer.getMyTurn().isLoser(currentPlayer, board)) {
                        Player loser = currentPlayer;
                        nextPlayerTurn(false);
                        notifyTurnObserver(null);
                        removePlayer(loser.getNickname(), loser.getAge());
                    }
                }
            }
        } else {
            if (indexPlayer < players.size() - 1)
                indexPlayer++;
            else indexPlayer = 0;
            currentPlayer = players.get(indexPlayer);
            //notifyTurnObserver(null);
        }
    }

    /**
     * this method select worker use by player to move or build
     *
     * @param ID     is the parameter to identify the worker
     * @param colour is the parameter to identify the worker
     * @return is the sentence
     */
    public boolean selectWorker(int ID, Colour colour) {
        for (Worker worker : currentPlayer.getWorkers())
            if (worker.getID() == ID  && worker.getColour().equals(colour)) {
                    currentPlayer.setSelectedWorker(worker);
                    notifyWorkerSelectionObserver();
                    if (currentPlayer.getIsWinner())
                        notifyPlayerWinOrLoseObservers();
                    return true;

            }
        return false;
    }

    //currentPlayer seleziona worker, su quel worker gli viene mostrato cosa può fare (costruire muovere o entrambe), giocatore seleziona quello che vuole fare e di conseguenza viene
    //settato l'attributo di Player e mostrate o le mosse o le costruzioni valide
    public boolean setWantsToMove() {
        if(currentPlayer.getMyTurn().showPossibilities(currentPlayer, board).get(0)) {
            currentPlayer.setWantsToMove(true);
            currentPlayer.setWantsToBuild(false);
            notifyPlayerDecisionObserver();
            return true;
        }else
            return false;

    }

    public boolean setWantsToBuild() {
        if(currentPlayer.getMyTurn().showPossibilities(currentPlayer,board).get(1)) {
            currentPlayer.setWantsToMove(false);
            currentPlayer.setWantsToBuild(true);
            notifyPlayerDecisionObserver();
            return true;
        }else
            return false;
    }


    public boolean setWantsToEndTurn(){
        if(currentPlayer.getMyTurn().showPossibilities(currentPlayer,board).get(2)) {
            currentPlayer.setWantsToMove(false);
            currentPlayer.setWantsToBuild(false);
            nextPlayerTurn(false);
            notifyPlayerDecisionObserver();
            return true;
        }else
            return false;
    }


    /**
     * this method is a call to normal move, using by controller to edit the game state,
     * it also check if the player is a loser or not
     *
     * @param coordX is the row of position
     * @param coordY is the column of position
     * @return is the sentence of move, true if it's successful, else false
     */
    public boolean move(int coordX, int coordY) {

        boolean swappingOrPushing = false;
        Worker swappedOrPushedWorker = null;
        if(!board.getPosition(coordX,coordY).isEmpty()) {
            swappingOrPushing = true;
            swappedOrPushedWorker = board.getPosition(coordX,coordY).getOccupiedBy();
        }
        if (currentPlayer.getMyTurn().move(currentPlayer, board, currentPlayer.getSelectedWorker(), board.getPosition(coordX, coordY))) {
            if (currentPlayer.getMyTurn().isLoser(currentPlayer, board)) {
                Player loser = currentPlayer;
                nextPlayerTurn(false);
                removePlayer(loser.getNickname(), loser.getAge());
            }
            if(swappingOrPushing){
                notifyMoveObservers(swappedOrPushedWorker);
            }

            notifyMoveObservers(currentPlayer.getSelectedWorker());
            if (currentPlayer.getIsWinner())
                notifyPlayerWinOrLoseObservers();
            return true;

        }
        return false;
    }

    /**
     * this method is a call to normal build, using by controller to edit the game state,
     * it doesn't check if the player is loser, because after a build he does his homework
     *
     * @param coordX    is the row of position
     * @param coordY    is the column of position
     * @param buildDome is the parameter used if the player want to force a build dome, but
     *                  it will be true only if he can
     * @return is the sentence of build, true if it's successful, else false
     */
    public boolean build(int coordX, int coordY, boolean buildDome) {
        if (currentPlayer.getMyTurn().build(currentPlayer, board, currentPlayer.getSelectedWorker(), board.getPosition(coordX, coordY), buildDome)) {
            if (currentPlayer.getMyTurn().isLoser(currentPlayer, board)) {
                Player loser = currentPlayer;
                nextPlayerTurn(false);
                removePlayer(loser.getNickname(), loser.getAge());
            }
            notifyBuildObservers(coordX, coordY);
            if (currentPlayer.isMyTurnOver())
                nextPlayerTurn(false);
            return true;
        }
        return false;
    }

    /**
     * This method register the view as an observer of all the game's events it is interested in
     * @param remoteView it is the remoteView
     */
    public void registerView(RemoteView remoteView){
        registerGameCreationObserver(remoteView);
        registerBeginningOfTurnObserver(remoteView);
        registerBuildObserver(remoteView);
        registerMoveObserver(remoteView);
        registerPlayerAttributesObserver(remoteView);
        registerPlayerDecisionObserver(remoteView);
        registerPlayerWinOrLoseObserver(remoteView);
        registerTurnObserver(remoteView);
        registerWorkerSelectionObserver(remoteView);

        if(withDivinities){
            registerChallengerObserver(remoteView);
            registerDeckObserver(remoteView);

        }

    }


    @Override
    public void registerPlayerWinOrLoseObserver(PlayerWinOrLoseObserver playerObserver) {
        playerWinOrLoseObserverList.add(playerObserver);
    }
    @Override
    public void unregisterPlayerWinOrLoseObserver(PlayerWinOrLoseObserver playerObserver) {
        playerWinOrLoseObserverList.remove(playerObserver);
    }
    @Override
    public void notifyPlayerWinOrLoseObservers() {

        for (PlayerWinOrLoseObserver o : playerWinOrLoseObserverList) {

            if (!currentPlayer.getIsWinner()) {
                //se ho un giocatore perdente passo anche coordinate delle posizioni dei due lavoratori che devono esssere mostrate vuote ora
                if( currentPlayer.getWorkers().get(0).getPosition()!=null && currentPlayer.getWorkers().get(1).getPosition()!=null ) {
                    int firstCoordX = currentPlayer.getWorkers().get(0).getPosition().getCoordX();
                    int firstCoordY = currentPlayer.getWorkers().get(0).getPosition().getCoordY();
                    int secondCoordX = currentPlayer.getWorkers().get(1).getPosition().getCoordX();
                    int secondCoordY = currentPlayer.getWorkers().get(1).getPosition().getCoordY();
                    o.onPlayerLose(currentPlayer.getNickname(), currentPlayer.getAge(), firstCoordX, firstCoordY, secondCoordX, secondCoordY);
                }else {
                    //case of a player removed for instance because the connection has fallen
                    o.onPlayerRemoved(currentPlayer.getNickname(), currentPlayer.getAge());
                }
            } if(currentPlayer.getIsWinner() || players.size() == 1){
                if(players.size() == 1)
                    o.onPlayerWin(players.get(0).getNickname(),players.get(0).getAge());
                else
                    o.onPlayerWin(currentPlayer.getNickname(), currentPlayer.getAge());

            }
        }
    }


    @Override
    public void registerBuildObserver(WorkerBuildObserver buildObserver) {
        workerBuildObserverList.add(buildObserver);
    }
    @Override
    public void unregisterBuildObserver(WorkerBuildObserver buildObserver) {
        workerBuildObserverList.remove(buildObserver);
    }
    @Override
    public void notifyBuildObservers(int x, int y) {
        for (WorkerBuildObserver o : workerBuildObserverList) {
            o.updateOnBuild(x, y, board.getPosition(x, y).getLevel(), board.getPosition(x, y).hasDome());
        }
    }


    @Override
    public void registerMoveObserver(WorkerMoveObserver moveObserver) {
        workerMoveObserverList.add(moveObserver);
    }
    @Override
    public void unregisterMoveObserver(WorkerMoveObserver moveObserver) {
        workerMoveObserverList.remove(moveObserver);
    }
    @Override
    public void notifyMoveObservers(Worker movedWorker) {
        for (WorkerMoveObserver o : workerMoveObserverList) {
            o.updateOnMove(movedWorker.getPosition().getCoordX(), movedWorker.getPosition().getCoordY(),
                    movedWorker.getID(), movedWorker.getColour());
        }

    }


    @Override
    public void registerDeckObserver(DeckObserver deckObserver) {
        deckObserverList.add(deckObserver);
    }
    @Override
    public void unregisterDeckObserver(DeckObserver deckObserver) {
        deckObserverList.remove(deckObserver);
    }
    @Override
    public void notifyDeckObservers() {
        //creo un array di stringhe contenente tutte le carte del deck
        for (DeckObserver o : deckObserverList) {
            ArrayList<String> deckOfStrings = new ArrayList<>();
            for (int i = 0; i < deck.getCardsInDeck().size(); i++) {
                deckOfStrings.add(deck.getCardsInDeck().get(i).getName());
            }

                o.onDeckChange(deckOfStrings);
            }
    }



    @Override
    public void registerPlayerAttributesObserver(PlayerAttributesObserver playerAttributesObserver) {
        playerAttributesObserverList.add(playerAttributesObserver);
    }
    @Override
    public void unregisterPlayerAttributesObserver(PlayerAttributesObserver playerAttributesObserver) {
        playerAttributesObserverList.remove(playerAttributesObserver);
    }
    @Override
    public void notifyPlayerAttributesObservers() {
        for (PlayerAttributesObserver o : playerAttributesObserverList) {
            if(currentPlayer.getCard() != null)
            o.onPlayerAttributesUpdate(currentPlayer.getNickname(), currentPlayer.getAge(),
                    currentPlayer.getWorkers().get(0).getColour(), currentPlayer.getCard().getName());
            else
                o.onPlayerAttributesUpdate(currentPlayer.getNickname(), currentPlayer.getAge(),
                        currentPlayer.getWorkers().get(0).getColour(), null);

        }



    }


    @Override
    public void registerChallengerObserver(ChallengerObserver challengerObserver) {
        challengerObserverList.add(challengerObserver);
    }
    @Override
    public void unregisterChallengerObserver(ChallengerObserver challengerObserver) {
        challengerObserverList.remove(challengerObserver);
    }
    @Override
    public void notifyChallengerObservers() {
        for (ChallengerObserver o : challengerObserverList) {
            ArrayList<String> deckOfStrings = new ArrayList<>();
            for (int i = 0; i < deck.getCardsInDeck().size(); i++) {
                deckOfStrings.add(deck.getCardsInDeck().get(i).getName());
            }
            o.onChallengerChosen(challenger.getNickname(), challenger.getAge(), deckOfStrings);
        }
    }


    @Override
    public void registerTurnObserver(TurnObserver turnObserver) {
        turnObserverList.add(turnObserver);
    }
    @Override
    public void unregisterTurnObserver(TurnObserver turnObserver) {
        turnObserverList.remove(turnObserver);
    }
    @Override
    public void notifyTurnObserver(String whatToDo) {
        for (TurnObserver o : turnObserverList)
            o.onNextPlayerTurn(currentPlayer.getNickname(), currentPlayer.getAge(), whatToDo);
    }


    @Override
    public void registerPlayerDecisionObserver(PlayerDecisionObserver playerDecisionObserver) {
        playerDecisionObserverList.add(playerDecisionObserver);
    }
    @Override
    public void unregisterPlayerDecisionObserver(PlayerDecisionObserver playerDecisionObserver) {
        playerDecisionObserverList.remove(playerDecisionObserver);
    }
    @Override
    public void notifyPlayerDecisionObserver() {
        for(PlayerDecisionObserver o : playerDecisionObserverList)
            if(currentPlayer.getWantsToMove())
                o.onDecisionTaken(currentPlayer.getNickname(),currentPlayer.getMyTurn().showMoves(currentPlayer, board));
            else if(currentPlayer.getWantsToBuild())
                o.onDecisionTaken(currentPlayer.getNickname(), currentPlayer.getMyTurn().showBuilds(currentPlayer,board));

    }


    @Override
    public void registerWorkerSelectionObserver(WorkerSelectionObserver workerSelectionObserver) {
        workerSelectionObserverList.add(workerSelectionObserver);
    }
    @Override
    public void unregisterWorkerSelectionObserver(WorkerSelectionObserver workerSelectionObserver) {
        workerSelectionObserverList.remove(workerSelectionObserver);
    }
    @Override
    public void notifyWorkerSelectionObserver() {
        for( WorkerSelectionObserver o : workerSelectionObserverList)
            o.onWorkerSelection(currentPlayer.getNickname(), currentPlayer.getMyTurn().showPossibilities(currentPlayer,board));
    }


    @Override
    public void registerGameCreationObserver(GameCreationObserver gameCreationObserver) {
        gameCreationObserverList.add(gameCreationObserver);
    }
    @Override
    public void unregisterGameCreationObserver(GameCreationObserver gameCreationObserver) {
        gameCreationObserverList.remove(gameCreationObserver);
    }
    @Override
    public void notifyGameCreationObserver() {
        ArrayList<String> oldPlayers = new ArrayList<>();
        for (Player player : players)
            oldPlayers.add(player.getNickname());
        for( GameCreationObserver o : gameCreationObserverList)
            o.onGameCreation(maxPlayers, withDivinities, board.getDimension(), oldPlayers);
    }


    @Override
    public void registerBeginningOfTurnObserver(BeginningOfTurnObserver beginningOfTurnObserver) {
        beginningOfTurnObserverList.add(beginningOfTurnObserver);
    }
    @Override
    public void unregisterBeginningOfTurnObserver(BeginningOfTurnObserver beginningOfTurnObserver) {
        beginningOfTurnObserverList.remove(beginningOfTurnObserver);
    }
    @Override
    public void notifyBeginningOfTurnObserver() {
        for( BeginningOfTurnObserver o: beginningOfTurnObserverList)
            o.onBeginningOfTurn(currentPlayer.getNickname());
    }
}