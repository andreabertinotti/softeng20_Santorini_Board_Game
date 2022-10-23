package it.polimi.ingsw.Server.Model.Decorator;

import it.polimi.ingsw.Server.Model.Root.Board;
import it.polimi.ingsw.Server.Model.Root.Player;
import it.polimi.ingsw.Server.Model.Root.Position;
import it.polimi.ingsw.Server.Model.Root.Worker;

import java.util.ArrayList;

/**
 * This class represents the most generic type of turn, associated therefore to a Player with no Divinity Cards
 * Every player has it's own instance of a Turn
 */
public class SimpleTurn implements Turn {

    /**
     * @param player is the current player, the one that has to play
     * This method set attributes of player to a default value before starting the player's turn
     * Set to 0 the number of moves, build of the player in this turn, and set attribute MyTurnOver to false
     */
    @Override
    public void startTurn(Player player) {
        player.getWorkers().get(0).setMoveNum(0);
        player.getWorkers().get(0).setBuildNum(0);
        player.getWorkers().get(1).setMoveNum(0);
        player.getWorkers().get(1).setBuildNum(0);
        player.setMyTurnOver(false);
        player.setSelectedWorker(null);
        player.setWantsToBuild(false);
        player.setWantsToMove(false);
    }

    /**
     * This method moves a worker to a new position if that is an allowed move, if it is allowed it also checks if it is a winning move
     * @param player it is the currentPlayer
     * @param board it is the game board with all information
     * @param worker it is the worker that player wants to move
     * @param newPos it is the position to which the player wants to move the worker
     * @return True if the worker has been moved false otherwise
     */
    @Override
    public boolean move(Player player, Board board, Worker worker, Position newPos) {
        if(player.getMyTurn().checkPossibleMove(newPos, board, worker) &&
                player.getMyTurn().effectsOnOpponents(player,newPos,board,worker) && player.getMyTurn().checkMoveFlow(player, worker)) {

            worker.setMoveNum(worker.getMoveNum()+1);                                                       //incremento di uno il numero di mosse fatte da questo worker in questo turno

            if (player.getWorkers().contains(worker))
                player.getMyTurn().isWinner(worker.getPosition(), newPos, player, board);                   //se la mossa è vincente setto attributo di player winner a true dopo di che il controller farà un check su quell'attributo

            board.freeCell(worker);                                                                         //setto la vecchia posizione a libera
            board.fillCell(newPos, worker);                                                                 //muovo worker nella suo nuova posizione
            return true;                                                                                    //se spostamento avvenuto con succeso true
        }
        return false;
    }

    /**
     * This method checks if worker can be moved to newPos based on the rules without divinities
     * @param newPos is the new position to which we want to move worker
     * @param board is the board of the game with all info needed to decide if a move is possible
     * @param worker is the worker meant to be moved to new pos
     * @return True if the movement is allowed False otherwise
     */
    @Override
    public boolean checkPossibleMove(Position newPos, Board board, Worker worker) {
        if (board.isNear(worker.getPosition(), newPos) && newPos.isEmpty() &&
                worker.getMoveNum() == 0 && !newPos.hasDome() &&
                worker.getPosition().getLevel() - newPos.getLevel() > -2 && board.getStateboard().contains(newPos)) {
            return true;
        }
        return false;
    }

    /**
     * This method allows a worker of a player to construct on the board, if it is allowed
     * @param player it is the currentPlayer
     * @param board it is the game board with all information
     * @param worker it is the worker that player wants to build with
     * @param buildPos it is the position in which the player wants to build with the worker
     * @param buildDome it is always false or null
     * @return True if the construction has been done, False otherwise
     */
    @Override
    public  boolean build(Player player, Board board, Worker worker, Position buildPos, boolean buildDome) {
        if (player.getMyTurn().checkPossibleBuilding(buildPos, board, worker, player) && player.getMyTurn().checkBuildFlow(player,worker)) {
            if((buildPos.getLevel()==3 && buildDome) || (!buildDome && buildPos.getLevel() < 3) ) {
                worker.setBuildNum(worker.getBuildNum() + 1);
                board.addBlock(buildPos);
                return true;
            }
        }return false;
    }

    /**
     * This method checks if worker can build on buildPos based on the rules without divinities
     * @param buildPos is the position on which the player wants to build
     * @param board is the board of the game with all info needed to decide if a build is possible
     * @param worker is the worker meant to build on buildPos
     * @param player it is the currentPlayer
     * @return True if the build is allowed, false otherwise
     */
    @Override
    public boolean checkPossibleBuilding(Position buildPos, Board board, Worker worker, Player player) {
        if (board.isNear(worker.getPosition(), buildPos) && buildPos.isEmpty() &&
                !buildPos.hasDome() && worker.getBuildNum() == 0) {
            player.setMyTurnOver(true);
            return true;
        }
        return false;
    }

    /**
     * This method checks whether a player has won in consequence to a move within it's turn and set the relative player's attribute
     * @param oldPos it is the oldPos of the player'sworker that has just been moved
     * @param newPos it is the newPos of the player's worker that has just benn moved
     * @param player it is the currentPlayer
     * @param board is the board of the game with all info needed to decide if a build is possible
     * @return True if player has won, false otherwise
     */
    @Override
    public boolean isWinner(Position oldPos, Position newPos, Player player, Board board) {
        //se il giocatore ha fatto una mossa vincente
        if (newPos.getLevel() == oldPos.getLevel() + 1 && newPos.getLevel() == 3) {
            player.setWinner(true);
            return true;
        }
        return false;
    }

    /**
     * This method it's called before of the method move().
     * This method check if a player has lost based on it's possibility to move ( throughout look up of the board)
     * @param player it is the currentPlayer
     * @param board  it is the game board
     * @return True if it has lost, False otherwise
     */
    @Override
    public boolean isLoser(Player player, Board board) {
        if (player.getWorkers().get(0).getMoveNum()==0 && player.getWorkers().get(1).getMoveNum()==0) {
            for (Worker worker : player.getWorkers()) {
                ArrayList<Position> nextPos = board.positionNearWorker(worker);
                for (Position pos : nextPos) {
                    if (player.getMyTurn().checkPossibleMove(pos, board, worker) &&
                    player.getMyTurn().effectsOnOpponents(player, pos, board, worker))
                        return false;
                }
            }
        } else if (player.getWorkers().get(0).getBuildNum()==0 && player.getWorkers().get(1).getBuildNum()==0) {
            Worker workerMoved = player.getWorkers().get(0);
            if (workerMoved.getMoveNum() == 0)
                workerMoved = player.getWorkers().get(1);
            ArrayList<Position> nextPos = board.positionNearWorker(workerMoved);
            for (Position pos : nextPos) {
                if (player.getMyTurn().checkPossibleBuilding(pos, board, workerMoved, player)) {
                    return false;
                }
            }
        } else if (player.getWorkers().get(0).getMoveNum()>=1 || player.getWorkers().get(1).getMoveNum()>=1 &&
                player.getWorkers().get(0).getBuildNum()>=1 || player.getWorkers().get(1).getBuildNum()>=1)
            return false;
        return true;
    }

    /**
     * This method checks if a move requires a worker to go in a superior level and if it does check if some effects that prevent it to do so are active
     * The first if clause it's entered only when playing with the divinity Athena, and the player that has it has used it's effect
     * @param player it is the currentPlayer
     * @param newPos
     * @param board
     * @param worker
     * @return True, if Athena'effect is not active or if the worker doesn't go up, False, if the worker is required to go up and Athena's effect prevent it to do so
     */
    @Override
    public boolean effectsOnOpponents(Player player, Position newPos, Board board, Worker worker) {
        if(player.isActiveEffect())
            if(worker.getPosition().getLevel() < newPos.getLevel()) {
                return false;
            }
        return true;
    }

    /**
     * This method controls that only one of the two worker of a player can be moved within a turn
     * @param player is the currentPlayer
     * @param worker1 is the worker that the player is trying to move
     * @return true if worker1 can be moved, false otherwise
     */
    @Override
    public boolean checkMoveFlow(Player player, Worker worker1){
        Worker worker2;
        if ( worker1 == player.getWorkers().get(0))
            worker2 = player.getWorkers().get(1);
        else worker2 = player.getWorkers().get(0);

        if(worker2.getMoveNum() == 0 && worker1.getMoveNum()==0) {
            return true;
        }
        return false;
    }

    /**
     * This method controls that, within a turn, a build can be done only subsequently to a move of the same worker
     * @param player is the currentPlayer
     * @param worker1 is the worker with whom the player is trying to build
     * @return true if the worker can build, false otherwise
     */
    @Override
    public boolean checkBuildFlow(Player player, Worker worker1){
        Worker worker2;
        if ( worker1 == player.getWorkers().get(0))
            worker2 = player.getWorkers().get(1);
        else worker2 = player.getWorkers().get(0);

        if( worker2.getMoveNum() == 0 && worker1.getMoveNum() >= 1) {
            return true;
        }
        return false;
    }


    //metodo che mostra ad un giocatore, che ha già selezionato il suo lavoratore, che cosa può fare
    // posizione 1 contenente true = può muovere pos 2 contenente true = può costruire posizione 3 contenente true = può finire il turno
    public ArrayList<Boolean> showPossibilities(Player player, Board board){
        ArrayList<Boolean> possibilities = new ArrayList<>(3);
        //se puoi muovere
        if(player.getMyTurn().checkMoveFlow(player, player.getSelectedWorker()) && player.getMyTurn().showMoves(player,board).size()!=0)
            possibilities.add(true);
        else possibilities.add(false);

        if(player.getMyTurn().checkBuildFlow(player,player.getSelectedWorker()) &&   player.getMyTurn().showBuilds(player,board).size()!=0)
            possibilities.add(true);
        else possibilities.add(false);

        if((player.getWorkers().get(0).getMoveNum() >= 1 && player.getWorkers().get(0).getBuildNum() >= 1)  ||
                (player.getWorkers().get(1).getMoveNum() >= 1  && player.getWorkers().get(1).getBuildNum() >= 1)  )
            possibilities.add(true);
        else possibilities.add(false);

        return possibilities;
    }

    //metodo che viene chiamato  (dopo che è stato selezionato un worker) e dopo che player ha manifestato l'intento di costruire (quindi wantsToMove=true)
    //gli viene passato currentPlayer, il worker slezionato
    //vedi modifica selectedWorker
    //ritorno un array list di interi contenente 18 interi che devono essere letti a coppie di due a sono rispettivamente coordinata x e y di una posizione su cui si può effettuare move

    public ArrayList<Integer> showMoves(Player player, Board board){

        ArrayList<Integer> validMoves = new ArrayList<>(18);
        Position pToBeValidated;
        int x, y;
        int workerX = player.getSelectedWorker().getPosition().getCoordX();
        int workerY = player.getSelectedWorker().getPosition().getCoordY();

        if(workerX == 0)
            workerX++;
        if(workerY == 0)
            workerY++;
        //controllo tutte le posizioni circostatnti (inclusa quella del worker) e vedo se ci può andare
        for(x = workerX-1; x>=0 && x< 5 &&  x>= workerX -1  &&  x<= workerX +1; x++){
            for(y = workerY-1; y>=0 && y< 5 &&  y>= workerY -1  &&  y<= workerY +1; y++){
                pToBeValidated = board.getPosition(x,y);
                //se pToBeValidate rappresenta una pos su cui si può movere le sue coordinate vengono inserite nell'arry validPos
                if(player.getMyTurn().checkPossibleMove(pToBeValidated,board,player.getSelectedWorker())){
                    validMoves.add(x);
                    validMoves.add(y);
                }
            }
        }
        return validMoves;
    }

    //con questo metodo ritorno solo le posizioni su cui si può costruire e non cosa ci si può costruire
    public ArrayList<Integer> showBuilds( Player player, Board board){

        ArrayList<Integer> validBuilds = new ArrayList<>(18);
        Position pToBeValidated;
        int x, y;
        int workerX = player.getSelectedWorker().getPosition().getCoordX();
        int workerY = player.getSelectedWorker().getPosition().getCoordY();

        if(workerX == 0)
            workerX++;
        if(workerY == 0)
            workerY++;
        //controllo tutte le posizioni circostatnti (inclusa quella del worker) e vedo se ci può costruire e cosa
        for(x = workerX-1; x>=0 && x< 5 &&  x>= workerX -1  &&  x<= workerX +1; x++){
            for(y = workerY-1; y>=0 && y< 5 &&  y>= workerY -1  &&  y<= workerY +1; y++){
                pToBeValidated = board.getPosition(x,y);

                //se pToBeValidate rappresenta una pos su cui si può costruire le sue coordinate vengono inserite nell'arry validPos
                if(player.getMyTurn().checkPossibleBuilding(pToBeValidated,board,player.getSelectedWorker(),player)) {
                    validBuilds.add(x);
                    validBuilds.add(y);

                }
            }
        }
        player.setMyTurnOver(false);
        return validBuilds;
    }
}















































    /* versione di move() con elementi del controller


    @Override
    public Worker move(Player player, Board board) {

        Worker worker;
        Position newPos;


        do {
           worker = player.chooseWorker();
           newPos = player.choosePosition();


           //se la posizione in cui si vuole muovere il worker è valida
       }while(!checkPossibleMove(worker.getPosition(), newPos, board) );

        worker.setOldPosition(worker.getPosition());
        worker.getPosition().setOccupiedBy(null);   //tolgo worker dalla vecchia posizione
        newPos.setOccupiedBy(worker);               //metto worker nella nuova posizione


        return worker;
    }

 */


/* versione di build() con elementi del controller


    //controllo se la costruzione semplice è possibile, se possibile la effettuo
    @Override
    public void build(Player player, Board board) {
        Worker worker;
        Position buildPos;
        do {
            worker = player.chooseWorker();
            buildPos = player.choosePosition();

            //se la posizione su cui si vuole costruire è vicina e ci si può costruire
        }while (!checkPossibleBuilding(worker.getPosition(), buildPos, board));

            board.addBlock(buildPos);


    }

 */