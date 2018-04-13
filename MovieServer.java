import java.io.*;
import java.net.*;

class MovieServer{

    private String PlayServerIP;
    private Integer PlaySeverPort;

    public void ListenRequest() {
        try {
            ServerSocket serverSocket = new ServerSocket(4242);
            
            while (true){
                Socket sock = serverSocket.accept();
                InputStream is = sock.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                Request request = (Request)ois.readObject();
                if (request != null ){

                    if( request.getRequesType() == "movie"){
                        //add it to queue
                    }else {
                        //send to play server
                    }
                }
                
            }
        } catch (IOExecption ex)
            ex.printStackTrace();
        }
    }

    public void SendRequestToPlayServer(String IPAddress, int port, Request request){
        Socket s = new Socket(IPAddress, port);
        InputStreamReader streamReader = new InputStreamReader(s.getInputStream());
        BufferedReader reader = new BufferedReader(streamReader);

    }


    public static void main(String[] args){

        MovieServer movieServer = new MovieServer();

        try {
            InetAddress address = InetAddress.getLocalHost();
            String getIPString = address.getHostAddress();
            System.out.println(getIPString);
        }catch(UnkownHostExeption ex){
            ex.printStackTrace();

        }
        System.out.println("Enter IP address of Play server");
        Scanner ipAddress = new Scanner(System.in);
        PlayServerIP = 


        System.out.println("Enter Play server port number");
        Scanner port = new Scanner(System.in);




        server.ListenForClientRequest();
        


        //request ip adress and port

    }
}