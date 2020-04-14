import java.io.*;
import java.net.*;

public class Server {
    private Gra gra;
    private ServerSocket socketSerwer;
    private int liczbaGraczy;
    private PolaczenieDoKlienta[] gracze;
    private int maxLiczbaGraczy;
    private int nrGraczaUWladzy;
    private int kolor =3;
    private int value;
    private int oldX;
    private int oldY;
    private int currentX;
    private int currentY;


   ///////////////////Metody
    public void przyjeciePolaczenia(){
        System.out.println("Oczekiwanie na graczy");
        try {
            while (liczbaGraczy < maxLiczbaGraczy) {//warunke dodatkowy jesli chcemy zeby wczesniej zakonczyl oczekiwanie na graczy
                Socket socket = socketSerwer.accept();//accept zwraca socket,(kliencka wersja serwer socketa)
                System.out.println("Gracz nr " + liczbaGraczy + " polaczyl sie");
                PolaczenieDoKlienta polaczenieDoKlienta = new PolaczenieDoKlienta(socket,liczbaGraczy);
                gracze[liczbaGraczy] = polaczenieDoKlienta;
                Thread t = new Thread(polaczenieDoKlienta);
                t.start();
                liczbaGraczy++;
            }
            System.out.println("Maksymalna liczb garczy osiganieta!!!");
        }catch(IOException e){
            System.out.println("przechwycono wyjątek z podlaczania klienta ");
        }
    }

    ///////////////////Konstruktor
    public Server(){
        this.maxLiczbaGraczy = 2;
        gra = new Gra(maxLiczbaGraczy);
        nrGraczaUWladzy =gra.losujNrGracza();//trzeba przeniesc do zmiany tury czy czeos
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
                    daneOUT.writeInt(nrGraczaUWladzy);
                    daneOUT.flush();
                    if(nrGraczaUWladzy == playerID){
                        kolor = daneIN.readInt();
                        value = daneIN.readInt();
                        oldX = daneIN.readInt();
                        oldY = daneIN.readInt();
                        currentX = daneIN.readInt();
                        currentY = daneIN.readInt();
                    }
                    else{
                        daneOUT.writeInt(kolor);
                        daneOUT.writeInt(value);
                        daneOUT.writeInt(oldX);
                        daneOUT.writeInt(oldY);
                        daneOUT.writeInt(currentX);
                        daneOUT.writeInt(currentY);
                        daneOUT.flush();
                    }
                }

            }catch (IOException e){

            }
        }
    }

    /////////////////////////Main
    public static void main(String[] args) throws UnknownHostException {
        System.out.print(InetAddress.getLocalHost());
        Server serwer = new Server();
        serwer.przyjeciePolaczenia();

    }
}
