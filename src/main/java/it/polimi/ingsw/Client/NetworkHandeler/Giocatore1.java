package it.polimi.ingsw.Client.NetworkHandeler;


import it.polimi.ingsw.Client.View.View;
import it.polimi.ingsw.Parser.ParserCSV;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Giocatore1 {

    public static void main(String[] args){


        String hostName;
        int portNumber;


        if(args.length == 1){
            hostName = args[0];
            portNumber = Integer.parseInt(args[1]);
        }else{
            ParserCSV parserCSV = new ParserCSV();
            hostName = parserCSV.readHostName();
            portNumber =parserCSV.readPort();


        }

        View view = new View(hostName,portNumber);


    }

}
