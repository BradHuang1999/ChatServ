package client;

import server.ChatProgramServer;

import java.io.*;
import java.net.Socket;

/**
 * @author Brad Huang
 */
class ChatServ{

    private Socket mySocket; //socket for connection
    protected ClientBufferedReader input; //reader for network stream
    protected PrintWriter output;

    protected LoginGUI login;
    protected FriendListGUI friendList;

    protected boolean running;

    public static void main(String[] args) throws IOException{
        ChatServ client = new ChatServ();
        client.start();
    }

    public void start() throws IOException{
        System.out.println("Attempting to make a connection..");

        try {
            mySocket = new Socket(ChatProgramServer.IP_ADDRESS, ChatProgramServer.PORT_NUM); //attempt socket connection (local address). This will wait until a connection is made
            InputStreamReader stream1 = new InputStreamReader(mySocket.getInputStream()); //Stream for network input
            input = new ClientBufferedReader(stream1);
            output = new PrintWriter(mySocket.getOutputStream()); //assign printwriter to network stream
        } catch (IOException e){  //connection error occured
            System.out.println("Connection to Server Failed");
        }

        System.out.println("Connection made.");

        login = new LoginGUI(this);
        login.setVisible(true);
    }

    public void close(){
        try {  //after leaving the main loop we need to close all the sockets
            this.output.println("Close");
            this.output.flush();

            try{
                Thread.sleep(1000);
            } catch (InterruptedException e){}

            this.running = false;

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
