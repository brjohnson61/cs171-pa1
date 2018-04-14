import java.io.*;
import java.net.*;
import java.util.Scanner;

class Kiosk{
    private String movieServerIP, playServerIP, thisIP;
    private int movieServerPort = 4243;
    private int playServerPort = 4242;
    private Socket movieSocket, playSocket, receiveSocket;
    private ServerSocket receiveServerSocket;
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;

    

    public void setupKiosk(){
        try {
            movieSocket = new Socket(movieServerIP, movieServerPort);
            //playSocket = new Socket(playServerIP, playServerPort);
        }catch (UnknownHostException err){
            err.printStackTrace();
        }catch (IOException err){
            err.printStackTrace();
        }
        

        takeUserRequest();
    }

    public void takeUserRequest(){


        while(true){
            String choice = "movie";
            Integer numTickets = 2;
            Request request = new Request(true, numTickets);
            
            
            request.setHasRedirected(false);
            request.setSucessfullyProcessed(false);

            sendRequest(request);
            responseFromServer();
        }
        
    }

    public void sendRequest(Request request){
        try{
            OutputStream os = movieSocket.getOutputStream();
            toServer = new ObjectOutputStream(os);
            toServer.writeObject(request);
            toServer.close();
            os.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void responseFromServer(){
        try{
            receiveServerSocket = new ServerSocket(4350);
            receiveSocket = receiveServerSocket.accept();
            InputStream is = receiveSocket.getInputStream();
            fromServer = new ObjectInputStream(is);
            Request receivedRequest = (Request)fromServer.readObject();
            is.close();
            receiveSocket.close();
            receiveServerSocket.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}