package networkingAssignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Brad Huang on 12/21/2016.
 */
class ChatServ{

    private Socket mySocket; //socket for connection
    private BufferedReader input; //reader for network stream
    private PrintWriter output;
    private boolean running = true;

    public static void main(String[] args) throws IOException{
        ChatServ client = new ChatServ();
        new ChatServ().start();
    }

    public void start() throws IOException{
        System.out.println("Attempting to make a connection..");

        try {
            mySocket = new Socket("127.0.0.1", 5000); //attempt socket connection (local address). This will wait until a connection is made
            InputStreamReader stream1 = new InputStreamReader(mySocket.getInputStream()); //Stream for network input
            input = new BufferedReader(stream1);
            output = new PrintWriter(mySocket.getOutputStream()); //assign printwriter to network stream
        } catch (IOException e){  //connection error occured
            System.out.println("Connection to Server Failed");
            e.printStackTrace();
        }

        System.out.println("Connection made.");

        LoginGUI login = new LoginGUI(output, input, this);
        login.setVisible(true);

        while (running){  // loop unit a message is received
            try {
                if (input.ready()){ //check for an incoming messge
                    String msg;
                    msg = input.readLine(); //read the message
                    System.out.println("Server: " + msg);
                }
            } catch (IOException e){
                System.out.println("Failed to receive msg from the server");
                e.printStackTrace();
            }
        }
    }

    public void close(){
        try {  //after leaving the main loop we need to close all the sockets
            this.output.println("close");
            this.output.flush();

            running = false;

            this.input.close();
            this.output.close();
            this.mySocket.close();

            System.exit(0);
        } catch (Exception e){
            System.out.println("Failed to close socket");
        }
    }
}
