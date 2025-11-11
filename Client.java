import java.util.*;
import java.net.*;
import java.io.*;


/*
Initial Handshake
    When a client first connects, they need to send a key to the server to 
    indicate they’re a valid client (recall that anyone can connect to public ports). 
    Here, the key will be the very insecure passcode 12345; 
    the server should look for each client to send such a passcode as their first message, 
    make sure it matches this number, and only then allow the client to actually 
    remain connected for future factorization requests.

Client
    You will implement a client in Client.java that connects to the server with a handshake, 
    and then sends and receives messages. See the unit tests for details of what methods you are 
    required to have (and their functionality).
*/
public class Client{
    // client.getSocket().getLocalAddress().toString()
    // client.getSocket().getPort())

    // client.handshake();
    private Socket s;
    private String host;
    private int port;

    private static String PASSWORD = "12345";

    public Client(String host, int port){
        this.host = host;
        this.port = port;
        this.s = new Socket(this.host, this.port);
    }

    

    public String request(String payload){
        try{
            PrintWriter pw = new PrintWriter(sock.getOutputStream());
            pw.println(payload);
            pw.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            String reply = in.readLine(); 
            
            pw.close();
            in.close();
            sock.close();

            return reply;
        }catch(Exception e){
            System.err.print("IOException");
            System.exit(1);
        }
    }

    public void handshake(){
        String response = request(PASSWORD);
    }



    public static void main(String args[]){
        // connect with handshake

        // send and receive message

        // client.request(number.toString());

    }
}