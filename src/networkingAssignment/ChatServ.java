package networkingAssignment;

import sun.rmi.runtime.Log;

import java.io.*;
import java.net.Socket;

/**
 * Created by Brad Huang on 12/21/2016.
 */
class ChatServ{

    private Socket mySocket; //socket for connection
    protected ClientBufferedReader input; //reader for network stream
    protected PrintWriter output;
    private boolean running = true;

    protected LoginGUI login;
    protected FriendListGUI friendList;

    public static void main(String[] args) throws IOException{
        ChatServ client = new ChatServ();
        new ChatServ().start();
    }

    public void start() throws IOException{
        System.out.println("Attempting to make a connection..");

        try {
            mySocket = new Socket("127.0.0.1", ChatProgramServer.socketNum); //attempt socket connection (local address). This will wait until a connection is made
            InputStreamReader stream1 = new InputStreamReader(mySocket.getInputStream()); //Stream for network input
            input = new ClientBufferedReader(stream1);
            output = new PrintWriter(mySocket.getOutputStream()); //assign printwriter to network stream
        } catch (IOException e){  //connection error occured
            System.out.println("Connection to Server Failed");
            e.printStackTrace();
        }

        System.out.println("Connection made.");

        login = new LoginGUI(this);
        login.setVisible(true);

//        while (running){  // loop unit a message is received
//            try {
//                if (input.ready()){ //check for an incoming messge
//                    String msg;
//                    msg = input.readLine(); //read the message
//                    System.out.println("Server: " + msg);
//                }
//            } catch (IOException e){
//                System.out.println("Failed to receive msg from the server");
//                e.printStackTrace();
//                this.close();
//            }
//        }
    }

    public void close(){
        try {  //after leaving the main loop we need to close all the sockets
            this.output.println("Close");
            this.output.flush();

            running = false;

            this.input.close();
            this.output.close();
            this.mySocket.close();

            System.exit(5);

        } catch (Exception e){
            System.out.println("Failed to close socket");
        }
    }

    class ClientBufferedReader extends BufferedReader{
        public ClientBufferedReader(Reader in){
            super(in);
        }
        @Override
        public String readLine() throws IOException{
            String line = super.readLine();
            System.out.println("Server: " + line);
            return line;
        }
    }
}
