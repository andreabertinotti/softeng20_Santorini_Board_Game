package it.polimi.ingsw.Server.NetworkHandeler;

import it.polimi.ingsw.shared.EventsRecivedFromClient.Updates.EventClientUpdate;
import it.polimi.ingsw.Parser.ParserCSV;
import it.polimi.ingsw.Server.RemoteView.RemoteView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiEchoServer {

    private int portNumber;
    ServerSocket serverSocket;

    private List<Connection> connections = new ArrayList<>();
    private RemoteView remoteView;


    public List<Connection> getConnections() {
        return connections;
    }

    public MultiEchoServer(int portNumber) {
        this.portNumber = portNumber;
    }


    public void createRemoteView() {
        remoteView = new RemoteView();
        remoteView.setServer(this);
    }

    public void pongReceiver(int clientID){
        getConnectionByID(clientID).setPongReceived(true);
    }


    public void startServer() {
        ExecutorService executor = Executors.newCachedThreadPool();

        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // Porta non disponibile
            return;
        }

        System.out.println("Server ready");
        while (true) {
            try {
                if (remoteView.newClientRequest(connections.size())) {
                    Socket socket = serverSocket.accept();
                    Connection newClient = new Connection(socket, this, remoteView, socket.getPort());
                    connections.add(newClient);
                    executor.submit(newClient);

                }

            } catch(IOException e) {
                System.out.println("serverSocket cut!");
                break; // Entrerei qui se serverSocket venisse chiuso
            }
        }
        executor.shutdown();
    }



    public void sendBroadcast(EventClientUpdate event) {
        for (Connection clientConnection : connections)
            try {
                clientConnection.send(event);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
    }

    public void sendToClient(EventClientUpdate event, Connection connection) {
        try {
            connection.send(event);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    public void deregisterConnection(Connection connection) {
        connections.remove(connection);
    }

    public void deregisterAllConnection() {
        for(Connection c : connections) {
            c.close();
        }
    }

    public Connection getConnectionByID(int clientID){
        for( Connection c : connections)
            if(c.getClientID() == clientID)
                return c;
         return null;
    }

    public Connection getConnectionByIdentifier(String identifier){
        for( Connection c : connections)
            if(c.getIdentifier().equals(identifier))
                return c;
        return null;
    }


    public static void main(String[] args) {

        int portNumber;
        if(args.length == 1){
            portNumber = Integer.parseInt(args[1]);
        }else{
            ParserCSV parserCSV = new ParserCSV();
            portNumber =parserCSV.readPort();
        }

        MultiEchoServer multiEchoServer = new MultiEchoServer(portNumber);
        multiEchoServer.createRemoteView();
        multiEchoServer.startServer();
    }

}