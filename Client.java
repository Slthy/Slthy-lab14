import java.util.*;
import java.net.*;
import java.io.*;


/*
Initial Handshake
    When a client first connects, they need to send a key to the server to 
    indicate they're a valid client (recall that anyone can connect to public ports). 
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
    private PrintWriter pw;
    private BufferedReader in;

    private static String PASSWORD = "12345";

    public Client(String host, int port){
        this.host = host;
        this.port = port;
        try{
            this.s = new Socket(this.host, this.port);
            this.pw = new PrintWriter(this.s.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.print("UnknownHostException");
            System.exit(1);
        } catch(Exception e){
            System.err.print("IOException");
            System.exit(1);
        }
        
    }


    public String request(String payload){
        try{
            pw.println(payload);
            pw.flush();

            String reply = in.readLine(); 

            return reply;
        }catch(Exception e){
            System.err.print("IOException");
            System.exit(1);
        }
        return null;
    }

    public void handshake(){
        try{
            pw.println(PASSWORD);
            pw.flush();
        }catch(Exception e){
            System.err.print("IOException");
            System.exit(1);
        }
    }

    public void disconnect(){
        try{
            if(pw != null) pw.close();
            if(in != null) in.close();
            if(this.s != null) this.s.close();
            this.s = null; // avoid dangling
        } catch(Exception e){
            System.err.print("IOException");
            System.exit(1);
        }
    }

    public Socket getSocket(){
        return s;
    }


    public static void main(String args[]){
        // connect with handshake

        // send and receive message

        // client.request(number.toString());

    }
}