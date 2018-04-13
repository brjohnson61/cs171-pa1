import java.io.*;
import java.net.*;
import java.util.Scanner;

class Kiosk{
    private String movieServerIP, playServerIP, thisIP;
    private int movieServerPort, playServerPort;
    private Socket movieSocket, playSocket;

    

    public void setupKiosk(){
        Scanner input = new Scanner(System.in);

        try {
            InetAddress address = InetAddress.getLocalHost();
            String thisIP = address.getHostAddress();
            System.out.println(thisIP);
        }catch(UnknownHostException ex){
            ex.printStackTrace();

        }

        System.out.println("Enter Movie server IP address: ");
        movieServerIP = input.nextLine();

        System.out.println("Enter Movie server port number: ");
        movieServerPort = input.nextInt();

        System.out.println("Enter Play server IP address: ");
        playServerIP = input.nextLine();

        System.out.println("Enter Play server port number: ");
        playServerPort = input.nextInt();

        movieSocket = new Socket(movieServerIP, movieServerPort);
        playSocket = new Socket(playServerIP, playServerPort);

        start();
    }

    public void start(){
        
        while(true){
            Scanner RequestInput = new Scanner(System.in);
            String choice;
            Integer numTickets;
            Request request;
            
            System.out.println("Enter 'movie' for movie and 'play' for play");
            choice = RequestInput.nextLine();

            System.out.println("Enter the number of tickets you want to purchase");
            numTickets = RequestInput.nextInt();
            
            if(choice.equals("movie")){
                request = new Request(choice, numTickets);
                request.setHasRedirected(false);
            }
            RequestInput.close();
        }
    }

    public void sendRequest(){

    }








}