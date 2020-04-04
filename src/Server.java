import java.io.*;
import java.net.*;

public class Server {

    private ServerSocket socketSerwer;
    private int liczbaGraczy;
    private PolaczenieDoKlienta[] gracze;
    private int maxLiczbaGraczy;


   ///////////////////Metody
    public void przyjeciePolaczenia(){
        System.out.println("Oczekiwanie na graczy");
        try {
            while (liczbaGraczy < maxLiczbaGraczy) {//warunke dodatkowy jesli chcemy zeby wczesniej zakonczyl oczekiwanie na graczy
                Socket socket = socketSerwer.accept();//accept zwraca socket,(kliencka wersja serwer socketa)
                liczbaGraczy++;
                System.out.println("Gracz nr " + liczbaGraczy + " polaczyl sie");
                PolaczenieDoKlienta polaczenieDoKlienta = new PolaczenieDoKlienta(socket,liczbaGraczy);
                gracze[liczbaGraczy-1] = polaczenieDoKlienta;
                Thread t = new Thread(polaczenieDoKlienta);
                t.start();
            }
            System.out.print("Maksymalna liczb garczy osiganieta!!!");
        }catch(IOException e){
            System.out.println("przechwycono wyjątek z podlaczania klienta ");
        }
    }

    ///////////////////Konstruktor
    public Server(){
        maxLiczbaGraczy = 5;
        gracze = new PolaczenieDoKlienta[maxLiczbaGraczy];
        System.out.print("Serwer ruszyl!!!");
        liczbaGraczy = 0;
        try{
            socketSerwer = new ServerSocket(51724);
        }catch(IOException e){
            System.out.print("przechwycono wyjątek z kontrukcji socketa");
        }
    }

    ////Klasy

    private class  PolaczenieDoKlienta implements Runnable{
        private Socket socket;
        private DataOutputStream daneOUT;
        private DataInputStream daneIN;
        private int playerID;

        public  PolaczenieDoKlienta(Socket s, int id) {
            socket = s;
            playerID = id;
            try {
                daneIN = new DataInputStream(socket.getInputStream());
                daneOUT = new DataOutputStream(socket.getOutputStream());

            } catch (IOException e) {
                System.out.println("Wyjatek z polaczena strona serwera");

            }
        }
        public void run(){
            try{
                daneOUT.writeInt(playerID);
                daneOUT.flush();
                while(true){

                }

            }catch (IOException e){

            }
        }
    }
    /////////////////////////Main
    public static void main(String[] args) {
        Server serwer = new Server();
        serwer.przyjeciePolaczenia();
    }
}
