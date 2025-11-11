import java.util.*;
import java.net.*;
import java.io.*;

import java.time.LocalDateTime;

/*
Initial Handshake
    When a client first connects, they need to send a key to the server to 
    indicate they're a valid client (recall that anyone can connect to public ports). 
    Here, the key will be the very insecure passcode 12345; 
    the server should look for each client to send such a passcode as their first message, 
    make sure it matches this number, and only then allow the client to actually 
    remain connected for future factorization requests.

Server
    You will implement a server in Server.java that allows multiple clients to 
    simultaneously connect to the server, perform a handshake, 
    and request that the server factorize an integer.

    the serve method
        To facilitate grading, the server will have a serve method that takes as argument the 
        number of clients it is expected to serve per test case, as opposed to an infinite 
        loop like there was in the exercises from class. This serve method will have a loop 
        run for as many clients as specified in its argument.

        After accepting a client via a successful handshake, this method will process 
        the client request in a separate thread so that the server can continue to accept 
        connections while these expensive factorization calculations are being performed on 
        behalf of various clients.
        --> serve +1

    other server methods
        The server should record the time each client was connected so that it can properly return 
        these values with the getConnectedTimes method, which should return a sorted ArrayList of 
        LocalDateTime objects representing the connection time of every client.

    See the unit tests for details of what additional methods you are required to have (and their functionality).
*/

public class Server{

    private ServerSocket serverSock;
    private ArrayList<LocalDateTime> connectedTimes;
    private static String PASSWORD = "12345";


    public Server(int port){
        this.connectedTimes = new ArrayList<LocalDateTime>();
        try{
            serverSock = new ServerSocket(port);
        }catch(Exception e){
            System.err.println("Cannot establish server socket");
            System.exit(1);
        }
        
    }

    public ArrayList<LocalDateTime> getConnectedTimes(){
        // tbd
        Collections.sort(connectedTimes);
        return new ArrayList<LocalDateTime>(connectedTimes);
    }

    public void serve(int n){
        for(int i = 0; i<n; i++){
            try{
                //accept incoming connection
                Socket clientSock = serverSock.accept();
                System.out.println("New connection: "+clientSock.getRemoteSocketAddress());
                connectedTimes.add(LocalDateTime.now());
                //start the thread
                (new ClientHandler(clientSock)).start();
                
                //continue looping
            }catch(Exception e){} //exit serve if exception
        }
    }

    public void disconnect(){
        try {
            if (this.serverSock != null && !this.serverSock.isClosed()) {
                serverSock.close();
                System.out.println("ServerSocket closed successfully.");
            }
        } catch (IOException e) {
            System.err.println("Error closing ServerSocket: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private class ClientHandler extends Thread{

        Socket sock;
        public ClientHandler(Socket sock){
            this.sock=sock;
        }

        public void run(){
            PrintWriter out=null;
            BufferedReader in=null;
            try{
                out = new PrintWriter(sock.getOutputStream());
                in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

                //read and echo back forever!

                // handshake
                String msg = in.readLine();

                if(!msg.equals(PASSWORD)) {
                    out.close();
                    in.close();
                    sock.close();
                    return;
                }

                while(true){
                    msg = in.readLine();
                    if(msg == null) break; //read null, remote closed
                    out.println(msg);
                    out.flush();
                }

                //close the connections
                out.close();
                in.close();
                sock.close();
                
            }catch(Exception e){}

            //note the loss of the connection
            System.out.println("Connection lost: "+sock.getRemoteSocketAddress());
        }

    }
    /*
    public static void main(String args[]){
        EchoSocketServer server = new EchoSocketServer(2021);
        server.serve();
    }
    */  
    
}