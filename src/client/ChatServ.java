package client;

import server.ChatProgramServer;

import java.io.*;
import java.net.Socket;

/**
 * ChatServ
 * @author Brad Huang
 * GUI design: Charlie Lin
 * 1990s chat program
 */
class ChatServ {

    private Socket mySocket; //socket for connection

    protected ClientBufferedReader input; //reader for network stream
    protected PrintWriter output;   // writer for the system

    protected LoginGUI login;       // login UI
    protected FriendListGUI friendList;       // friendList UI

    protected boolean running;

    /**
     * start a chatServ client
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ChatServ client = new ChatServ();
        client.start();
    }

    /**
     * start the connection
     *
     * @throws IOException
     */
    public void start() throws IOException {
        System.out.println("Attempting to make a connection..");

        try {
            // attempt to make a connection
            mySocket = new Socket(ChatProgramServer.IP_ADDRESS, ChatProgramServer.PORT_NUM); // attempt socket connection (local address). This will wait until a connection is made
            InputStreamReader stream1 = new InputStreamReader(mySocket.getInputStream()); // stream for network input
            input = new ClientBufferedReader(stream1);
            output = new PrintWriter(mySocket.getOutputStream()); // assign printwriter to network stream

            System.out.println("Connection made.");

            login = new LoginGUI(this);     // set up and display login UI
            login.setVisible(true);
        } catch (IOException e) {
            // connection error occuring
            System.out.println("Connection to Server Failed");
            System.exit(-1);
        }
    }

    /**
     * close the chatServ client UI
     */
    public void close() {
        try {  //after leaving the main loop we need to close all the sockets
            this.output.println("Close");       // send the server a closing message
            this.output.flush();

            try {
                Thread.sleep(1000);     // wait for any extra response from server
            } catch (InterruptedException e) {
            }

            this.running = false;       // stop receiving message from server

            this.input.close();         // close the sockets
            this.output.close();
            this.mySocket.close();

            System.exit(0);

        } catch (Exception e) {
            System.out.println("Failed to close socket");
        }
    }

    /**
     * Client Reader
     * read from server
     * if necessary, can display messages from server
     */
    class ClientBufferedReader extends BufferedReader {
        public ClientBufferedReader(Reader in) {
            super(in);
        }

//        @Override
//        public String readLine() throws IOException{
//            String line = super.readLine();
//            System.out.println("Server: " + line);
//            return line;
//        }
    }
}