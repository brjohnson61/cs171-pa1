import java.io.*;
import java.net.*;
import java.util.Scanner;

class MovieServer{

    private String playServerIP;
    private Integer playSeverPort;
    private int numMoviesLeft = 50;
    private int PORT = 4242;
    private ServerSocket serverSocket;
    private Socket sock;
    
    ProcessRequest processRequest = new ProcessRequest();

    public synchronized Boolean buyMovies(int num){
        if (numMoviesLeft <= 0){
            return false;
        }else{
            int temp = numMoviesLeft - num;
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

            try {
                sock = serverSocket.accept();
                serverSocket.close();
            }catch (IOException err){
                err.printStackTrace();
            }
            processRequest.sock = this.sock;
            Thread newClient = new Thread(processRequest);

                
        }
    }

    public void SendRequestToPlayServer(String IPAddress, int port, Request request){
        try {
            Socket s = new Socket(IPAddress, port);
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


    //public void setupMovieServer()
    
    public void setupMovieServer(){
        Scanner input = new Scanner(System.in);
        // try {
        //     InetAddress address = InetAddress.getLocalHost();
        //     String getIPString = address.getHostAddress();
        //     System.out.println(getIPString);
        // }catch(UnknownHostException ex){
        //     ex.printStackTrace();

        // }

        URL whatismyip;
        BufferedReader in;
        String myIp = "";
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

        System.out.println("Enter IP address of Play server");
        playServerIP = input.nextLine();


        System.out.println("Enter Play server port number");
        playSeverPort = new Integer(input.nextLine());

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

                        if( request.getRequestType().equals("movie")){
                             Boolean sucesssfull = buyMovies(request.getNumTickets());
                             request.setSucessfullyProcessed(sucesssfull);

                            if (request.getHasRedirected()){
                                //send to play server
                            }else{
                                //send to origin 
                            }

                        }else {
                            if(request.getHasRedirected()){
                                //send to origin
                            }
                            request.setHasRedirected(true);
                            String origIP = request.getOriginalIP();
                            Integer origPort = new Integer(request.getOriginalPort());
                            SendRequestToPlayServer(origIP, origPort, request);
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