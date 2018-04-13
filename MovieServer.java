import java.io.*;
import java.net.*;

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
                Request request = (Request)ois.readObject();
                if (request != null ){

                    if( request.getRequesType() == "movie"){
                        //add it to queue
                    }else {
                        //send to play server
                    }
                }

                is.close();
                sock.close();
                serverSocket.close();
                
                
            }
        } catch (IOExecption ex)
            ex.printStackTrace();
        }
    }

    public void SendRequestToPlayServer(String IPAddress, int port, Request request){
        Socket s = new Socket(IPAddress, port);
        OutputStream os = s.getOuputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(request);
        oos.close();
        os.close();
        s.close();

    }


    public setupMovieServer(){
        Scanner input = new Scanner(System.in);
        try {
            InetAddress address = InetAddress.getLocalHost();
            String getIPString = address.getHostAddress();
            System.out.println(getIPString);
        }catch(UnkownHostExeption ex){
            ex.printStackTrace();

        }
        System.out.println("Enter IP address of Play server");
        PlayServerIP = input.nextLine();


        System.out.println("Enter Play server port number");
        PlaySeverPort = input.nextLine();

       ListenForRequest();

        //request ip adress and port

    }
}