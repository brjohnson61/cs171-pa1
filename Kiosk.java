import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.TimeUnit;

class Kiosk{
    private String movieServerIP, playServerIP, thisIP;
    private Integer movieServerPort;
    private Integer playServerPort;
    private Integer receivePort;
    private Socket movieSocket, playSocket, receiveSocket;
    private ServerSocket receiveServerSocket;
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;
    static final Scanner scan = new Scanner(System.in);
    static final Random rand = new Random();
    

    public void setupKiosk(){
       

        System.out.println("Enter Movie Server IP address:");
        movieServerIP = scan.nextLine();

        System.out.println("Enter Play Server IP address:");
        playServerIP = scan.nextLine();
        
        System.out.println("Enter server port number:");
        movieServerPort = scan.nextInt();
        playServerPort = movieServerPort;


        try {
            InetAddress address = InetAddress.getLocalHost();
            thisIP = address.getHostAddress();
            System.out.println(thisIP);
        }catch(UnknownHostException ex){
            ex.printStackTrace();
        }

        
        System.out.println("Enter Port Number to receive requests back from server");
        receivePort = scan.nextInt();

        
        takeUserRequest();
    }

    public void takeUserRequest(){

        while(true){
            String choice = "movie";
            Boolean userInputSuccess = false;
            Integer numTickets = 2;

            do{

                System.out.println("Enter 'movie' to purchase a movie ticket, or 'play' to purchase a play ticket:");
                choice = scan.nextLine();
               
               

                if((choice.equals("movie") || choice.equals("play"))){
                    System.out.println("Enter the number of tickets you would like to purchase:");
                    numTickets = scan.nextInt();
                    if( numTickets > 0){
                        userInputSuccess = true;
                    }else{
                        userInputSuccess = false;
                    }
                }
                else{
                    System.out.println("Please enter a valid input!");
                }
            }while(!userInputSuccess);
            
            Request request;


            if(choice.equals("movie")){
                request = new Request(true, numTickets);
            }
            else{
                request = new Request(false, numTickets);
            }
            request.setHasRedirected(false);
            request.setSucessfullyProcessed(false);
            request.setOriginalIP(thisIP);
            request.setOriginalPort(receivePort);;

            sendRequest(request);
            responseFromServer();
        }
        
    }

    public void sendRequest(Request request){
        
        Integer rn = rand.nextInt(2);
        try{
            try {
                TimeUnit.SECONDS.sleep(5);
                //System.out.println("trying socket");
                System.out.print("Random integer is:");
                System.out.println(rn);
                if(rn == 1){
                    movieSocket = new Socket(movieServerIP, movieServerPort);
                    System.out.println("Sent to movie server");
                }
                else{
                    playSocket = new Socket(playServerIP, playServerPort);
                    System.out.println("Sent to play server");
                }
                //System.out.println("Socket Working");
            }catch (UnknownHostException err){
                System.out.println("Unknown Host exception");
                err.printStackTrace();
            }catch (IOException err){
                err.printStackTrace();
                System.out.println("IOException");
            }catch(InterruptedException err){
                err.printStackTrace();
            }
            OutputStream os;
            if(rn == 1){
                os = movieSocket.getOutputStream();
            }
            else{
                os = playSocket.getOutputStream();
            }
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
            receiveServerSocket = new ServerSocket(receivePort);
            receiveSocket = receiveServerSocket.accept();
            InputStream is = receiveSocket.getInputStream();
            fromServer = new ObjectInputStream(is);
            Request receivedRequest = (Request)fromServer.readObject();
            is.close();
            receiveSocket.close();
            receiveServerSocket.close();
            //System.out.println("received");
            System.out.println("Tickets remaining:");
            System.out.println(receivedRequest.getRemaining());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
