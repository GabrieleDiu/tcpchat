package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
    private ArrayList<connection> connections;

    @Override
    public void run() {
        try {
            int port= 9999;
            ServerSocket server = new ServerSocket(port);
            Socket client = server.accept();
            connection handler = new connection(client);
            connections.add(handler);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }


}
public void broadcast(String message){
        for(connection ch :connections)
        {
            if(ch!= null)
            {
                ch.sendmes(message);
            }
        }

    }
class connection implements Runnable{
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    public  connection (Socket client)
    {
        this.client=client;
    }

    @Override
    public void run() {
        try{
            out= new PrintWriter(client.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out.println("nickname:");
             String nickname=in.readLine();
             //de adauga
            System.out.println(nickname);

            broadcast(nickname+"joi the chat");
            String message ;
            while ((message = in.readLine() )!= null)
            {
                if(message.startsWith("/nick")) {
                    String[] messagesplit = message.split(" ", 2);
                    if(messagesplit.length==2)
                    {
                        broadcast(nickname +"rename" + messagesplit[1]);
                        System.out.println(nickname +"rename" + messagesplit[1]);
                        nickname=messagesplit[1];
                    }
                }else if(message.startsWith("/quit"))
                    // TODO: quit
            }else{
                broadcast(nickname +":"+message);
            }


        }catch (IOException e)
        {

        }

    }
        public void sendmes(String message )
        {
            out.println(message);
        }
    }
}
