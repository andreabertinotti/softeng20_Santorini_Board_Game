package it.polimi.ingsw.Server.Model.Root;

import it.polimi.ingsw.Server.Model.Decorator.Turn;
import it.polimi.ingsw.Server.Model.TurnCreator.Effect;

import java.util.ArrayList;

/**
 * this class represent a single worker of a player
 */
public class Player {

    private String nickname;
    private int age;
    private DivinityCard card;
    private boolean winner;
    private ArrayList<Worker> workers;
    private static final int workerNumber = 2;
    private Turn myTurn;
    private ArrayList<Player> opponents = new ArrayList<>();
    private boolean activeEffect;
    private boolean MyTurnOver;

    private boolean divinityAlreadyAssigned = false;

   //attributo che in startTurn viene resettato a null tutte le volte che rincomincia il turno
    private Worker selectedWorker;
    private boolean wantsToMove;
    private boolean wantsToBuild;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getIsWinner() {
        ;
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public DivinityCard getCard() {
        return card;
    }

    public void setCard(DivinityCard card) {
        this.card = card;
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }

    public Turn getMyTurn() {
        return myTurn;
    }

    public void setMyTurn(Turn myTurn) {
        this.myTurn = myTurn;
    }

    public ArrayList<Player> getOpponents() {
        return opponents;
    }

    public boolean isActiveEffect() {
        return activeEffect;
    }

    public void setActiveEffect(boolean activeEffect) {
        this.activeEffect = activeEffect;
    }

    public void setSelectedWorker(Worker selectedWorker) {
        this.selectedWorker = selectedWorker;
    }

    public Worker getSelectedWorker() {
        return selectedWorker;
    }

    public boolean getWantsToMove() {
        return wantsToMove;
    }

    public boolean getWantsToBuild() {
        return wantsToBuild;
    }

    public void setWantsToMove(boolean wantsToMove) {
        this.wantsToMove = wantsToMove;
    }

    public void setWantsToBuild(boolean wantsToBuild) {
        this.wantsToBuild = wantsToBuild;
    }

    public void setMyTurnOver(boolean myTurnOver) {
        MyTurnOver = myTurnOver;
    }

    public boolean isMyTurnOver() {
        return MyTurnOver;
    }

    public boolean isDivinityAlreadyAssigned() {
        return divinityAlreadyAssigned;
    }

    public void setDivinityAlreadyAssigned(boolean divinityAlreadyAssigned) {
        this.divinityAlreadyAssigned = divinityAlreadyAssigned;
    }

    /**
     * this method create a single player with all default information and unique information
     * which make the difference between players
     *
     * @param nickname is the name of player
     * @param age      is the age of player
     */
    public Player(String nickname, int age) {
        this.nickname = nickname;
        this.age = age;
        this.card = null;
        this.winner = false;
        this.activeEffect = false;
        this.MyTurnOver = false;
        this.workers = null;
    }

    /**
     * this method create and assign two worker to player
     *
     * @param indexID is the index of player in list of players
     */
    public void assignWorker(int indexID) {
        workers = new ArrayList<>();
        for (int i = 1; i <= workerNumber; i++) {
            Worker newWorker = new Worker(i, indexID);
            workers.add(newWorker);
        }
    }

    /**
     * this method creates the Turn related to the abilities of the player's divinity
     *
     * @param cardEffect is the ability of the player's divinity
     */
    public void createTurn(Effect cardEffect) {
        myTurn = cardEffect.getTurn();
    }
}
