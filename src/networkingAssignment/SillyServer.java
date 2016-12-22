package networkingAssignment;

/* [SillyServer.java]
 * Description: This is an example of a chat server.
 * The program  waits for a client and accepts a message. 
 * It then responds to the message and quits.
 * This server demonstrates how to employ multithreading to accepts multiple clients
 * @author Mangat
 * @version 1.0a
 */

//imports for network communication
import java.io.*;
import java.net.*;
import java.util.Date;

class SillyServer{

    ServerSocket serverSock;// server socket for connection
    static Boolean running = true;  // controls if the server is accepting clients

    /**
     * Main
     *
     * @param args parameters from command line
     */
    public static void main(String[] args) throws IOException{
        new SillyServer().go(); //start the server
    }

    /**
     * Go
     * Starts the server
     */
    public void go() throws IOException{
        System.out.println("Waiting for a client connection..");

        Socket client = null;

        try {
            serverSock = new ServerSocket(5000);

            while (running){
                client = serverSock.accept();
                System.out.println("Client connected");
                Thread t = new Thread(new ConnectionHandler(client));
                t.start();
            }
        } catch (Exception e){
            System.out.println("Error accepting connection");
            try {
                client.close();
            } catch (Exception e1){
                System.out.println("Failed to close socket");
            }
            System.exit(-1);
        }
    }

    class ConnectionHandler implements Runnable{
        private PrintWriter output;
        private BufferedReader input; //Stream for network input
        private Socket client;  //keeps track of the client socket
        private boolean running;

        /* ConnectionHandler
         * Constructor
         * @param the socket belonging to this client connection
         */
        ConnectionHandler(Socket s){
            this.client = s;  //constructor assigns client to this
            try {  //assign all connections to client
                this.output = new PrintWriter(client.getOutputStream());
                InputStreamReader stream = new InputStreamReader(client.getInputStream());
                this.input = new BufferedReader(stream);
            } catch (IOException e){
                e.printStackTrace();
            }
            running = true;
        } //end of constructor

        /* run
         * executed on start of thread
         */
        public void run(){
            //Get a message from the client
            String msg;

            //Get a message from the client
            while (running){
                try {
                    if (input.ready()){
                        msg = input.readLine();
                        System.out.println("Client: " + msg);
                        output.println("Message Received. Time = " + new Date());
                        output.flush();
                        if (msg.equals("close")){
                            running = false;
                            output.println("close");
                            output.flush();
                            System.out.println("Client closed.");
                        }
                    }
                } catch (IOException e){
                    System.out.println("Failed to receive msg from the client");
                    e.printStackTrace();
                }
            }

            try {
                input.close();
                output.close();
                client.close();
            } catch (Exception e){
                System.out.println("Failed to close socket");
            }
        }
    }
}