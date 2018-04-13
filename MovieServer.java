import java.io.*;
import java.net.*;
import java.util.Scanner;

class MovieServer{

    private String PlayServerIP;
    private Integer PlaySeverPort;

    public void ListenForRequest() {
        try {
            ServerSocket serverSocket = new ServerSocket(4242);
            
            while (true){
                Socket sock = serverSocket.accept();
                InputStream is = sock.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                try {
                    Request request = (Request)ois.readObject();
                    if (request != null ){

                        if( request.getRequestType().equals("movie")){
                            //add it to queue
                        }else {
                            if(request.getHasRedirected()){
                                String origIP = request.getOriginalIP();
                                Integer origPort = new Integer(request.getOriginalPort());

                                SendRequestToPlayServer(origIP, origPort, request);
                            }
                        
                             //send to play server
                        }

                    }
                }catch (ClassNotFoundException cnfe){
                    cnfe.printStackTrace();
                }
                
               

                is.close();
                sock.close();
                serverSocket.close();
                
                
            }
        } catch (IOException ex){
            ex.printStackTrace();
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


    public void setupMovieServer(){
        Scanner input = new Scanner(System.in);
        try {
            InetAddress address = InetAddress.getLocalHost();
            String getIPString = address.getHostAddress();
            System.out.println(getIPString);
        }catch(UnknownHostException ex){
            ex.printStackTrace();

        }
        System.out.println("Enter IP address of Play server");
        PlayServerIP = input.nextLine();


        System.out.println("Enter Play server port number");
        PlaySeverPort = new Integer(input.nextLine());

       ListenForRequest();

        //request ip adress and port

    }
}