import java.io.*;
import java.net.*;

public class Server {
    private Gra gra;
    private ServerSocket socketSerwer;
    private int liczbaGraczy;
    private PolaczenieDoKlienta[] gracze;
    private int maxLiczbaGraczy;
    private int nrGraczaUWladzy;
    private int kolor;
    private int oldX;
    private int oldY;
    private int currentX;
    private int currentY;
    int kolorBuff=1;
    int oldXBuff;
    int oldYBuff;
    int currentXBuff;
    int currentYBuff;
    int flaga;//1=kolor, 3 = oldx, 4 = oldy, 5 = curx, 6 = cury


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
        this.maxLiczbaGraczy = 3;
        gra = new Gra(maxLiczbaGraczy);
        nrGraczaUWladzy =gra.losujNrGracza();//trzeba przeniesc do zmiany tury czy czegos
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
                flaga = 0;
                    daneOUT.writeInt(flaga);
                    daneOUT.writeInt(nrGraczaUWladzy);
                    daneOUT.flush();
                    flaga = -1;

                while(true){
                    Thread.yield();

                    if(nrGraczaUWladzy == playerID){
                        flaga = daneIN.readInt();
                        if(flaga == 1)
                            kolorBuff = daneIN.readInt();
                        System.out.println(kolorBuff);
                        if(flaga == 3)
                            oldXBuff = daneIN.readInt();
                        if(flaga == 4)
                            oldYBuff = daneIN.readInt();
                        if(flaga == 5)
                            currentXBuff = daneIN.readInt();
                        if(flaga == 6)
                            currentYBuff = daneIN.readInt();
                    }
                    else{
                        if(kolor != kolorBuff) {
                            System.out.println("wysylam kolor teraz");
                            kolor = kolorBuff;
                            flaga = 1;
                            daneOUT.writeInt(flaga);
                            daneOUT.writeInt(kolor);
                            daneOUT.flush();
                        }
                        if(oldX != oldXBuff){
                            oldX = oldXBuff;
                            flaga = 3;
                            daneOUT.writeInt(flaga);
                            daneOUT.writeInt(oldX);
                            daneOUT.flush();
                        }
                        if( oldY != oldYBuff) {
                            oldY = oldYBuff;
                            flaga = 4;
                            daneOUT.writeInt(flaga);
                            daneOUT.writeInt(oldY);
                            daneOUT.flush();
                        }
                        if(currentX != currentXBuff) {
                            currentX = currentXBuff;
                            flaga = 5;
                            daneOUT.writeInt(flaga);
                            daneOUT.writeInt(currentX);
                            daneOUT.flush();
                        }
                        if(currentY != currentYBuff) {
                            currentY = currentYBuff;
                            flaga = 6;
                            daneOUT.writeInt(flaga);
                            daneOUT.writeInt(currentY);
                            daneOUT.flush();
                        }
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
