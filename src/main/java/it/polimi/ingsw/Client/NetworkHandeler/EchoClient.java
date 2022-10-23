package it.polimi.ingsw.Client.NetworkHandeler;

import it.polimi.ingsw.Client.View.View;
import it.polimi.ingsw.shared.EventRecivedFromServer.Request.EventDisconnectionRequest;
import it.polimi.ingsw.shared.EventRecivedFromServer.Request.EventServerRequest;
import it.polimi.ingsw.shared.EventsRecivedFromClient.ClientVisitor;
import it.polimi.ingsw.shared.EventsRecivedFromClient.EventClientVisitor;
import it.polimi.ingsw.shared.EventsRecivedFromClient.Updates.EventClientUpdate;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class EchoClient implements Runnable {

    //TODO controllare se da mettere private
    String hostName;
    int portNumber;

    private ObjectOutputStream out;

    private ObjectInputStream in;

    private Socket echoSocket;

    private boolean isActive;

    private int clientID;

    private static View view;



    public int getClientID() {
        return clientID;
    }

    public synchronized void sendToServer(EventServerRequest event){

        send(event);

    }

    public synchronized void send(EventServerRequest event){

        try {
            out.writeObject(event);
            out.flush();
        }catch (IOException e){
            System.out.println("can't send");
        }

    }

    public synchronized void closeConnection(){

        EventServerRequest eventDisconnect = new EventDisconnectionRequest(clientID);
        sendToServer(eventDisconnect);

        System.out.println("connection closed.");
        isActive = false;
        try{
            echoSocket.close();
            in.close();
            out.close();

        }catch (IOException e){
            System.err.println(e.getMessage());
        }


    }


    public EchoClient(View view, String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.view = view;
        isActive = true;
    }

    @Override
    public void run(){


        try {

            echoSocket = new Socket(hostName, portNumber);
            out = new ObjectOutputStream(echoSocket.getOutputStream()); // get the output stream of client.
            in = new ObjectInputStream(echoSocket.getInputStream());  // get the input stream of client


            view.setConnectionActive(true);
            System.out.println("connection established");

            clientID = echoSocket.getLocalPort();
            ClientVisitor visitor = new EventClientVisitor(view);
            EventClientUpdate eventRecived;


            while (isActive) {

                try {   //ricevi tutti i pacchetti in arrivo
                    eventRecived = (EventClientUpdate) in.readObject();
                    eventRecived.accept(visitor);
                } catch (ClassNotFoundException e) {
                    System.err.println(e.getMessage());
                }catch (IOException e){
                    if(isActive)
                        System.out.println("connection interrupted! :(");
                    echoSocket.close();
                    in.close();
                    out.close();
                    break;
                }

            }

        }catch (IOException e){
            System.out.println("unreachable server! Connection closed :(");
            view.setConnectionActive(false);
        }



    }


}
