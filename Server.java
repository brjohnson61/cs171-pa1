import java.io.*;
import java.net.*;

class Server{
    public void ListenForClients() {
        try {
            ServerSocket serverSocket = new ServerSocket(4242);
            
            while (true){

                Socket sock = serverSocket.accept();

                PrintWriter writer = new PrintWriter(sock.getOutputStream());
                String helloWorld = "Hello World";
                writer.println(helloWorld);
                writer.close();
                System.out.println(helloWorld)
            }
        } catch (IOExecption ex)
        
        }
    }

    public void ConnectToServer(String IPAddress, int port){
        Socket s = new Socket(IPAddress, port);
        InputStreamReader streamReader = new InputStreamReader(s.getInputStream());
        BufferedReader reader = new BufferedReader(streamReader);

        String 

    }


    public static void main(String[] args){

        Server server = new Server();
        server.go();
        //request ip adress and port

    }
}