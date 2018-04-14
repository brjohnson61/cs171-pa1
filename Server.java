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

    
    public Server(Boolean type){
        if (type){
            this.type = "movie";
            this.PORT = 4243;
        }else{
            this.type = "play";
            this.PORT = 4242;
        }
    }


    ProcessRequest processRequest = new ProcessRequest();

    public synchronized Boolean buyTickets(int num){
        if (ticketsLeft <= 0){
            return false;
        }else{
            int temp = ticketsLeft - num;
            if (temp < 0){
                return false;
            } else{
                return true;
            }
        }
    }


    public void ListenForRequest() {

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException ex){
            ex.printStackTrace();
        }

        while (true){
            System.out.println("Waiting for connection...");
            try {
                sock = serverSocket.accept();
                System.out.println("Accepted a connection");
                serverSocket.close();
            }catch (IOException err){
                err.printStackTrace();
            }
            processRequest.sock = this.sock;
            Thread newClient = new Thread(processRequest);

                
        }
    }

    public void SendRequestTo(String ipAddress, int port, Request request){
        try {
            Socket s = new Socket(ipAddress, port);
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
        Scanner input = new Scanner(System.in);
        

        URL whatismyip;
        BufferedReader in;
        String myIp = "";


        // try {
        //     InetAddress address = InetAddress.getLocalHost();
        //     myIp = address.getHostAddress();
        //     System.out.println(myIp);
        // }catch(UnknownHostException ex){
        //     ex.printStackTrace();

        // }
        try {
            whatismyip = new URL("http://checkip.amazonaws.com");
            in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            myIp = in.readLine();
        }catch (MalformedURLException err){
            err.printStackTrace();
        } catch (IOException err){
            err.printStackTrace();
        }
        System.out.println(myIp);

        // System.out.println("Enter IP address of Play server");
        // opposingServerIP = input.nextLine();


        // System.out.println("Enter Play server port number");
        // opposingServerPort = new Integer(input.nextLine());

       ListenForRequest();

        //request ip adress and port

    }


    class ProcessRequest implements Runnable{
        private Socket sock;

        public void run(){
            try {
                InputStream is = this.sock.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                try {
                    Request request = (Request)ois.readObject();
                    if (request != null ){

                        if( request.getRequestType().equals(type)){
                             Boolean sucesssfull = buyTickets(request.getNumTickets());
                             request.setSucessfullyProcessed(sucesssfull);

                            if (request.getHasRedirected()){
                                SendRequestTo(opposingServerIP,opposingServerPort,request);
                            }else{
                                SendRequestTo(request.getOriginalIP(), request.getOriginalPort(), request);
                            }

                        }else {
                            if(request.getHasRedirected()){
                                SendRequestTo(request.getOriginalIP(), request.getOriginalPort(), request);
                            }else{
                                request.setHasRedirected(true);
                                SendRequestTo(opposingServerIP, opposingServerPort, request);
                            }
                            
                        }

                    }
                }catch (ClassNotFoundException cnfe){
                    cnfe.printStackTrace();
                }
                is.close();
                sock.close();
            }catch (IOException err){
                err.printStackTrace();
            }
            
                
                
        }
    }
}