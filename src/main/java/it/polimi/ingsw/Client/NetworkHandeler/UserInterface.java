package it.polimi.ingsw.Client.NetworkHandeler;

import it.polimi.ingsw.shared.Colour;

import java.util.ArrayList;

public interface UserInterface extends Runnable {


    public abstract void showBeginningOfTurn(String popUpMessage);

    public abstract void showBuild(int x, int y, int level, boolean hasDome);

    public abstract void showChallenger(String nickname, ArrayList<String> deck);

    public abstract void showDeck(ArrayList<String> deck);

    public abstract void showGameAttributes(int nPlayer, boolean withDivinities, int boardDimension, ArrayList<String> opponents);

    public abstract void showInvalidAction(String whatToFix);

    public abstract void showLose(String nickname);

    public abstract void showMove(Colour colour, int ID, int coordX, int coordY);

    public abstract void showPlayerAttribute(String nickname, int age, Colour workerColour, String divinity);

    public abstract void showPlayerDecision(ArrayList<Integer> validPos);

    public abstract void showTurn(String nickname, int age, String whatToDo, Boolean yourTurn);


    public abstract void showWin(String nickname, Boolean winner);

    public abstract void showWorkerSelection(ArrayList<Boolean> possibilities);

    public abstract void showPopUp(String message);
}
