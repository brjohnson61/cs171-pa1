import java.util.Scanner;

class Main{

    public static void main(String[] args){
        Scanner InputScanner = new Scanner(System.in);

        System.out.println("Enter S to set up a server. Enter K to set up a kiosk");

     
        String input = InputScanner.nextLine();
        if(input.equals("K") || input.equals("k")){
            System.out.println("Set up kiosk");
            Kiosk kiosk = new Kiosk();
            kiosk.setupKiosk();
        }
        else if(input.equals("S") || input.equals("s")){
            System.out.println("Enter M to set up a movie server. Enter P to set up a play server");
            input = InputScanner.nextLine();
            if(input.equals("M") || input.equals("m")){
                System.out.println("Movie Server");
                Server movieServer = new Server(true);
                movieServer.setupMovieServer();
            }
            else if(input.equals("P") || input.equals("p")){
                System.out.println("Play Server");
            }
        }
        else{
            System.out.println("Enter valid input next time");
            System.out.println(input);
        }
        InputScanner.close();


    }


}