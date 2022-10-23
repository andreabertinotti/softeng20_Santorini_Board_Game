package it.polimi.ingsw.Server.NetworkHandeler;

import it.polimi.ingsw.shared.EventRecivedFromServer.EventServerVisitor;
import it.polimi.ingsw.shared.EventRecivedFromServer.Request.EventServerRequest;
import it.polimi.ingsw.shared.EventRecivedFromServer.ServerVisitor;
import it.polimi.ingsw.shared.EventsRecivedFromClient.Updates.EventClientUpdate;
import it.polimi.ingsw.shared.EventsRecivedFromClient.Updates.EventPing;
import it.polimi.ingsw.Server.RemoteView.RemoteView;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;


public class Connection implements Runnable {

    private Socket socket;
    private RemoteView remoteView;
    private MultiEchoServer server;
    private int clientID;
    private String identifier;
    private ObjectOutputStream out = null;
    private boolean pongReceived = true;
    private boolean isActive;
    final Timer t = new Timer();


    public Connection(Socket socket, MultiEchoServer server, RemoteView remoteView, int clientID) {
        this.socket = socket;
        this.server = server;
        this.remoteView = remoteView;
        this.clientID = clientID;
        isActive = true;
    }


    public int getClientID() {
        return clientID;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String nickname) {
        identifier = nickname;
    }

    public void setPongReceived(boolean pongReceived) {
        this.pongReceived = pongReceived;
    }


    public void send(EventClientUpdate c) throws IOException {
        out.writeObject(c);
        out.flush();
    }

    public synchronized void closeConnection() {
        try {
            //termino thread delegato alla gestione di meccanismo di ping e pong
            t.purge();
            t.cancel();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void close() {
        isActive = false;
        closeConnection();
        System.out.println("Deregistering client: " + clientID);
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    public void pinger() {

        TimerTask tt = new TimerTask() {

            int pongMissed = 0;

            @Override
            public void run() {

                    if (pongReceived) {
                        pongMissed = 0;
                        //System.out.println("pong ricevuto da" + clientID);
                        EventPing ping = new EventPing();
                        try {
                            send(ping);
                        } catch (IOException e) {
                            System.out.println("can not send ping");
                            //e.printStackTrace();
                        }
                        setPongReceived(false);
                    } else {
                        pongMissed++;
                        if (pongMissed >= 2) {
                            System.out.println("SECOND PONG NOT RECEIVED!");
                            remoteView.onConnectionCut(clientID);
                        } else {
                            System.out.println("FIRST PONG NOT RECEIVED!");
                            pongMissed++;
                            EventPing ping = new EventPing();
                            try {
                                send(ping);
                            } catch (IOException e) {
                                //e.printStackTrace();
                            }
                        }
                    }
            }


        };


        //invio un ping ogni 3000ms a partire da 10ms dall'inizio della partita
        //parte il thread relativo

        t.schedule(tt, 2000, 4000);

    }



    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream()); // get the output stream of client.
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());    // get the input stream of client.


            //creo un pinger associato alla connessione
            pinger();


            //controller verifica se creare una partita o no (se prima connesione chiedi di creare partita)
            //se la partita già c'è aggiungo il giocatore alla partita chiedendoli di mettere nickname...
            remoteView.instructClientRequest(getClientID());

            ServerVisitor visitor = new EventServerVisitor(remoteView, server);
            EventServerRequest eventServerRequest;

            while (isActive) {
                try {
                    //ricevi tutti i pacchetti in arrivo
                    eventServerRequest = (EventServerRequest) in.readObject();
                    eventServerRequest.accept(visitor);

                } catch (ClassNotFoundException e) {
                    System.err.println(e.getMessage());
                } catch (IOException e){
                    System.out.println("connection with client "+ clientID+ " cut");
                    remoteView.onConnectionCut(clientID);
                }
            }
        } catch (IOException e) {
            System.out.println("can not open stream for client");
            remoteView.onConnectionCut(clientID);
            return;
            //e.printStackTrace();
            //System.err.println(e.getMessage());
        }

    }

}