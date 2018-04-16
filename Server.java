import java.io.*;
import java.net.*;
import java.util.Scanner;

class Server{

    private String opposingServerIP;
    private Integer opposingServerPort;
    private String type;
    private int ticketsLeft = 50;
    private int PORT;
    private ServerSocket serverSocket;
    private Socket sock;
    private static final Scanner input = new Scanner(System.in);

    
    public Server(Boolean type){
        if (type){
            this.type = "movie";
            //this.PORT = 4001;
        }else{
            this.type = "play";
            //this.PORT = 4242;
        }
    }


    ProcessRequest processRequest = new ProcessRequest();

    public synchronized Boolean buyTickets(int num){
        if (ticketsLeft <= 0){
	    System.out.println("Unable to buy tickets");
            return false;
        }else{
            int temp = ticketsLeft - num;
            if (temp < 0){
                return false;
            } else{
		ticketsLeft = ticketsLeft - num;
        System.out.println("Bought tickets");
        System.out.print("Tickets left: ");
		System.out.println( ticketsLeft);
            	return true;
	    }
	    
        }
    }


    public void ListenForRequest() {

	try {

		serverSocket = new ServerSocket(PORT);
        	while (true){
            	System.out.println("Waiting for connection...");
            	try {
                	sock = serverSocket.accept();
                	//System.out.println("Accepted a connection");
                	processRequest.sock = this.sock;
			        //System.out.println("Creating new thread");
                	Thread newClient = new Thread(processRequest);
			        newClient.start();
            	}catch (IOException err){
                	err.printStackTrace();
            	}
        			        
        	}
	}catch (IOException err){
		err.printStackTrace();
	}
    }

    public void SendRequestTo(String ipAddress, int port, Request request){
        try {
            System.out.println("Sending To");
            System.out.println("IP Address: " + ipAddress);
            System.out.println("Port Number: " + port);
            Socket s = new Socket(ipAddress, port);
            //System.out.println("Socket created");
            OutputStream os = s.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(request);
            oos.close();
            os.close();
            s.close();
       
            
        }catch (IOException ex){
            ex.printStackTrace();

        }
        

    }
    
    public void setupMovieServer(){
        

        URL whatismyip;
        BufferedReader in;
        String myIp = "";
        try {
             InetAddress address = InetAddress.getLocalHost();
             myIp = address.getHostAddress();
             System.out.println(myIp);
        }catch(UnknownHostException ex){
             ex.printStackTrace();

        }
        String opposingType = "";

        
        System.out.println("Enter IP address of other server");
        opposingServerIP = input.nextLine();

        System.out.println("Enter port number for server to listen on:");
        this.PORT = input.nextInt();
        opposingServerPort = this.PORT;
        ListenForRequest();

    }


    class ProcessRequest implements Runnable{
        private Socket sock;

        public void run(){
        try {
		    System.out.println("Processing Request");
            InputStream is = this.sock.getInputStream();
		    //System.out.println("Done with socket input");
            ObjectInputStream ois = new ObjectInputStream(is);
            try {
                System.out.println("Reading Request");
                Request request = (Request)ois.readObject();
                //System.out.println("Read request");
                if (request != null ){
                    if( request.getRequestType().equals(type)){
                        //System.out.println("Request type:");
                        //System.out.println(request.getRequestType());
                        //System.out.println("Number of tickets:");
                        //System.out.println(request.getNumTickets());

                        Boolean sucessfull = buyTickets(request.getNumTickets());
                        System.out.println("Buy tickets sucessfulll ?");
                        System.out.println(sucessfull);
                        request.setSucessfullyProcessed(sucessfull);
                        request.setRemaining(ticketsLeft);
                        System.out.println("Number of tickets left" );
                        System.out.println(ticketsLeft);
                    
                                                    
                        if (request.getHasRedirected()){
                            System.out.println("Forwarding request to opposing server");
                            SendRequestTo(opposingServerIP,opposingServerPort,request);
                        }else{
                            System.out.println("Forwarding request to original client");
                            SendRequestTo(request.getOriginalIP(), request.getOriginalPort(), request);
                            //System.out.println("Sent request to original client");
                        }

                    }else {
                        if(request.getHasRedirected()){
                            System.out.println("Forwarding opposing request to original client");
                            SendRequestTo(request.getOriginalIP(), request.getOriginalPort(), request);
                        }else{
                            System.out.println("Forwarding opposing request to opposing server");
                            request.setHasRedirected(true);
                            SendRequestTo(opposingServerIP, opposingServerPort, request);
                        }  
                    }
                }
            }catch (ClassNotFoundException cnfe){
                cnfe.printStackTrace();
            }
		    System.out.println("Closing alll streams");
            is.close();
            sock.close();
	    }catch (IOException err){
                err.printStackTrace();
        }
        System.out.println("Done reciving request");
                
                
        }
    }
}
