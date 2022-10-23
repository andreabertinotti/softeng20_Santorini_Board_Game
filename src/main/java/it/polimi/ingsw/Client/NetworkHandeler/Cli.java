package it.polimi.ingsw.Client.NetworkHandeler;

import it.polimi.ingsw.Client.View.View;
import it.polimi.ingsw.shared.Colour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Cli implements UserInterface, Runnable{

    private View view;



    public Cli(View view) {
        this.view = view;
    }

    public synchronized void reader() throws IOException {
        // creazione stream di input da tastiera
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        // ciclo di lettura da tastiera, invio al server e stampa risposta

        userInput = null;
        while(true) {
            do {
                try {
                    userInput = stdIn.readLine();
                } catch (IOException e) {
                    System.err.println("Couldn't read user input");
                    System.exit(1);
                }
            } while (!userInput.equals("cmd"));
            System.out.println("write command name...\n (available commands: connect, disconnect, create game, insert nickname, build, move, " +
                    "choose deck, choose first, \nchoose possibilities, select worker, choose cards, set worker position)");
            userInput = stdIn.readLine();
            interpreter(userInput);
        }

    }

    public synchronized void interpreter(String userInput) throws IOException {
        //dalla stringa ricevuta vengono tolti gli spazi e messi a minuscolo tutti i caratteri
        switch(userInput.replaceAll("\\s+","").toLowerCase()){

            case "creategame" :
                createGame();
                break;

            case "insertnickname" :
                insertNickname();
                break;


            case "build" :
                build();
                break;

            case "move" :
                move();
                break;

            case "choosedeck" :
                challengerChooseCards();
                break;

            case "choosefirst" :
                challengerChooseFirst();
                break;

            case "choosepossibilities" :
                possibilityChosen();
                break;

            case "selectworker" :
                selectWorker();
                break;

            case "choosecards" :
                setCardChosen();
                break;

            case "setworkerposition" :
                setWorkerPosition();
                break;

            case "connect" :
                view.connectToServer();
                break;

            case "disconnect" :
                view.disconnectFromServer();
                break;
            default :
                System.out.println("Insert a valid command.");
                break;
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("new CLI created connect to server !!!!");
            reader();
        } catch (IOException e) {
            System.out.println("keyboard malfunction  :(  ");
            e.printStackTrace();
            System.exit(0);
        }
    }


    //:::::::::::::::::::::::::::::::::: METODI CLI ::::::::::::::::::::::::::::::::::::::::::::::


    public void createGame() {


        int nPlayer=0;
        int tmp=0;
        boolean withDivinities;
        Scanner input1;
        Scanner input2;

        do {
            System.out.println("enter number of players (2 or 3) :");
            input1 = new Scanner(System.in);
        }while(!input1.hasNextInt());
        nPlayer = input1.nextInt();

        do{
            System.out.println("with or without divinities (1= with, 0 = without) :");
            input2 = new Scanner(System.in);
        }while(!input2.hasNextInt());

        tmp = input2.nextInt();
        if(tmp == 1){
            withDivinities = true;
        }else {
            withDivinities = false;
        }

        view.createGame(nPlayer, tmp, withDivinities);
    }

    public void insertNickname() {
        String nickname;
        int age;
        Scanner input2;

        System.out.println("nickname: ");
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        try {
            nickname = stdIn.readLine();
            view.setNickname(nickname);
            do {
                System.out.println("age: ");
                input2 = new Scanner(System.in);
            }while(!input2.hasNextInt());

            age = input2.nextInt();
            view.insertNickname(nickname, age);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void build(){
        int coordX;
        int coordY;
        boolean buildDome;
        Scanner input1;
        Scanner input2;
        Scanner input3;

        do {
            input1 = new Scanner(System.in);
            System.out.println("X: ");
        }while(!input1.hasNextInt());
        coordX = input1.nextInt();
        do {
            System.out.println("Y: ");
            input2 = new Scanner(System.in);
        }while(!input2.hasNextInt());
        coordY = input2.nextInt();
        do {
            input3 = new Scanner(System.in);
            System.out.println("dome: ");
        }while(!input3.hasNextBoolean());
        buildDome = input3.nextBoolean();

        view.build(coordX, coordY, buildDome);

    }

    public void move(){
        int coordX;
        int coordY;
        Scanner input1;
        Scanner input2;

        do {
            input1 = new Scanner(System.in);
            System.out.println("X: ");
        }while(!input1.hasNextInt());
        coordX = input1.nextInt();
        do {
            System.out.println("Y: ");
            input2 = new Scanner(System.in);
        }while(!input2.hasNextInt());
        coordY = input2.nextInt();

        view.move(coordX, coordY);
    }

    public void challengerChooseCards(){
        ArrayList<String> chosenDeck = new ArrayList<>();

        for(int i=1; i<=view.getnPlayer(); i++) {
            Scanner input1 = new Scanner(System.in);
            System.out.println("divinity " + i + ": ");
            chosenDeck.add(input1.nextLine());
        }

        view.challengerChooseCards(chosenDeck);
    }

    public void challengerChooseFirst(){
        String firstPlayerNickname;

        Scanner input = new Scanner(System.in);
        System.out.println("insert first player nickname: ");
        firstPlayerNickname = input.nextLine();

        view.challengerChooseFirst(firstPlayerNickname);

    }


    public void possibilityChosen(){
        boolean move = false;
        boolean build = false;
        boolean endTurn = false;
        Scanner input;
        Scanner input2;
        Scanner input3;

        do {
            input = new Scanner(System.in);
            System.out.println("want to move: ");
        }while(!input.hasNextBoolean() );
            move = input.nextBoolean();

        do {
            input2 = new Scanner(System.in);
            System.out.println("want to build: ");
        }while(!input2.hasNextBoolean() );
            build = input2.nextBoolean();
        do {
            input3 = new Scanner(System.in);
            System.out.println("want to end turn: ");
        }while (!input3.hasNextBoolean() );
            endTurn = input3.nextBoolean();

        view.possibilityChosen(move, build, endTurn);

    }


    public void selectWorker(){
        int ID;
        Scanner input;

        do {
            input = new Scanner(System.in);
            System.out.println("worker to select (ID): ");
        }while(!input.hasNextInt());
        ID = input.nextInt();

        view.selectWorker(ID);
    }

    public void setCardChosen(){
        String cardChosen;

        Scanner input = new Scanner(System.in);
        System.out.println("card chosen: ");
        cardChosen = input.nextLine();

        view.setCardChosen(cardChosen);

    }

    public void setWorkerPosition(){
        int firstCoordX;
        int firstCoordY;
        int secondCoordX;
        int secondCoordY;
        Scanner input;
        Scanner input1;
        Scanner input2;
        Scanner input3;

        do {
            input = new Scanner(System.in);
            System.out.println("worker 1, X: ");
        }while(!input.hasNextInt());
        firstCoordX = input.nextInt();
        do {
            input1 = new Scanner(System.in);
            System.out.println("worker 1, Y: ");
        }while(!input1.hasNextInt());
        firstCoordY = input1.nextInt();
        do {
            input2 = new Scanner(System.in);
            System.out.println("worker 2, X: ");
        }while(!input2.hasNextInt());
        secondCoordX = input2.nextInt();
        do {
            input3 = new Scanner(System.in);
            System.out.println("worker 2, Y: ");
        }while(!input3.hasNextInt());
        secondCoordY = input3.nextInt();

        view.setWorkerPosition(firstCoordX, firstCoordY,secondCoordX,secondCoordY);
    }



    //:::::::::::::::::::::::::::::::::: METODI CLI PER UPDATES ::::::::::::::::::::::::::::::::::::::::::::::



    public void showBeginningOfTurn(String popUpMessage) {
        System.out.println(popUpMessage);
    }

    public void showBuild(int x, int y, int level, boolean hasDome) {
        System.out.println("built on position x: " + x + " y: " + y + " level: " + level + "has dome: " + hasDome);
    }

    //ricevo showChallenger solo se sono il challenger
    public void showChallenger(String nickname, ArrayList<String> deck) {
        System.out.println("The challenger is: " + nickname);
    }

    public void showDeck(ArrayList<String> deck) {
        System.out.println("Cards in deck are: " + deck);
    }

    public void showGameAttributes(int nPlayer, boolean withDivinities, int boardDimension, ArrayList<String> opponents) {
        System.out.println("You are in a game with " + nPlayer + " players, divinities: " + withDivinities + ", board dimension is: " + boardDimension);
        for (String opponent : opponents)
            System.out.println("nella partita è presente: " + opponent);
    }

    public void showInvalidAction(String whatToFix) {
        System.out.println("Action is invalid! What to fix: " + whatToFix);
    }

    public void showLose(String nickname) {
        System.out.println("Player " + nickname + "has lost!");
    }

    public void showMove(Colour colour, int ID, int coordX, int coordY) {
        System.out.println("Worker with colour " + colour + ", ID " + ID + ", moved to position X: " + coordX + " Y: " + coordY);
    }

    public void showPlayerAttribute(String nickname, int age, Colour workerColour, String divinity) {
        System.out.println("Change in player's attributes! Nickname: " + nickname + " age: " + age + " workers' colour: " + workerColour + " divinity: " + divinity);
    }

    public void showPlayerDecision(ArrayList<Integer> validPos) {
        int i, j;
        System.out.println("valid positions: ");
        for (i = 0, j = 1; j < validPos.size(); i++, j++) {
            System.out.println("x:" + validPos.get(i) + "   y:" + validPos.get(j));
            i++;
            j++;
        }
    }

    public void showTurn(String nickname, int age, String whatToDo, Boolean yourTurn) {
        if(yourTurn){
            System.out.println("It's your turn! What to do: " + whatToDo);
        } else {
            System.out.println("It's " + nickname + "'s turn!");
        }

    }


    public void showWin(String nickname, Boolean winner) {
        if(winner){
            System.out.println("You are the winner!");
        }else{
            System.out.println("The winner is " + nickname);
        }

    }

    public void showWorkerSelection(ArrayList<Boolean> possibilities) {
        System.out.println("the selceted worker can: " +"Move:"+possibilities.get(0)+" Build: "+possibilities.get(1)+" End Turn: "+ possibilities.get(2));

    }

    public void showPopUp(String message){
        System.out.println(message);
    }








}
