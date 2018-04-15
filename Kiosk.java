import java.io.*;
import java.net.*;
import java.util.Scanner;

class Kiosk{
    private String movieServerIP, playServerIP, thisIP;
    private Integer movieServerPort;
    private Integer playServerPort;
    private Integer receivePort;
    private Socket movieSocket, playSocket, receiveSocket;
    private ServerSocket receiveServerSocket;
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;

    

    public void setupKiosk(){
        Scanner setupInput = new Scanner(System.in);

        System.out.println("Enter Movie Server IP address:");
        movieServerIP = setupInput.nextLine();
        
        System.out.println("Enter Movie Server port number:");
        movieServerPort = setupInput.nextInt();


        try {
            InetAddress address = InetAddress.getLocalHost();
            thisIP = address.getHostAddress();
            System.out.println("This machine's IP Address: " + thisIP);
        }catch(UnknownHostException ex){
            ex.printStackTrace();
        }

        
        System.out.println("Enter Port Number to receive requests back from server");
        receivePort = setupInput.nextInt();

        
        setupInput.close();
        takeUserRequest();
    }

    public void takeUserRequest(){


        while(true){
            Scanner ticketRequest = new Scanner(System.in);
            String choice = "";
            Boolean userInputSuccess = false;
            Integer numTickets = 2;
            
            do{
                System.out.println("Enter 'movie' to purchase a movie ticket, or 'play' to purchase a play ticket.");
                //choice = ticketRequest.nextLine();
                System.out.println("Enter the number of tickets you would like to purchase:");
                //numTickets = ticketRequest.nextInt();

                if((choice.equals("movie") || choice.equals("play")) && numTickets > 0){
                    userInputSuccess = true;
                }
                else{
                    System.out.println("Please enter a valid input!");
                }
            }while(!userInputSuccess);
            
            Request request = new Request(true, numTickets);
            request.setHasRedirected(false);
            request.setSucessfullyProcessed(false);
            request.setOriginalIP(thisIP);
            request.setOriginalPort(receivePort);;

            sendRequest(request);
            responseFromServer();
            ticketRequest.close();
        }
        
    }

    public void sendRequest(Request request){
        try{
            try {
                System.out.println("trying socket");
                movieSocket = new Socket(movieServerIP, movieServerPort);
                //playSocket = new Socket(playServerIP, playServerPort);
                System.out.println("Socket Working");
            }catch (UnknownHostException err){
                System.out.println("Unknown Host exception");
                err.printStackTrace();
            }catch (IOException err){
                err.printStackTrace();
                System.out.println("IOException");
            }
            OutputStream os = movieSocket.getOutputStream();
            toServer = new ObjectOutputStream(os);
            toServer.writeObject(request);
            toServer.close();
            os.close();
            System.out.println("sent.");
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
            System.out.println("received");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
