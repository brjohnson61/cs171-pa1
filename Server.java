import java.io.*;
import java.net.*;

class Server {
    public void go() {
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
        } catch (IOExecption ex){
            ex.printStackTrace();
        }
    }


    public static void main(String[] args){

        Server server = new Server();
        server.go();
    }
}