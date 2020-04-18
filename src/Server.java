import java.io.*;
import java.net.*;

public class Server {
    private Gra gra;
    private ServerSocket socketSerwer;
    private short liczbaGraczy;
    private PolaczenieDoKlienta[] gracze;
    private int maxLiczbaGraczy;
    private short nrGraczaUWladzy;
    private byte kolor;
    private short oldX;
    private short oldY;
    private short currentX;
    private short currentY;
    private byte kolorBuff;
    private short oldXBuff;
    private short oldYBuff;
    private short currentXBuff;
    private short currentYBuff;
    private byte flaga;//1=kolor, 3 = oldx, 4 = oldy, 5 = currx, 6 = curry
    private String slowo;
    private String slowoBuff;


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
        nrGraczaUWladzy =(short)gra.losujNrGracza();//trzeba przeniesc do zmiany tury czy czegos
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
        private short playerID;

        public  PolaczenieDoKlienta(Socket s, short id) {
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
                daneOUT.writeShort(playerID);
                flaga = 0;
                    daneOUT.writeByte(flaga);
                    daneOUT.writeShort(nrGraczaUWladzy);
                    daneOUT.flush();
                    flaga = -1;

                while(true){
                    Thread.yield();
                    if(nrGraczaUWladzy == playerID){
                        flaga = daneIN.readByte();
                        if(flaga == 1)
                            kolorBuff = daneIN.readByte();
                        if(flaga == 2)
                            slowoBuff = daneIN.readUTF();
                        if(flaga == 3)
                            oldXBuff = daneIN.readShort();
                        if(flaga == 4)
                            oldYBuff = daneIN.readShort();
                        if(flaga == 5)
                            currentXBuff = daneIN.readShort();
                        if(flaga == 6)
                            currentYBuff = daneIN.readShort();
                    }
                    else{
                        if(kolor != kolorBuff) {
                            //System.out.println("wysylam kolor teraz");
                            kolor = kolorBuff;
                            flaga = 1;
                            daneOUT.writeByte(flaga);
                            daneOUT.writeByte(kolor);
                            daneOUT.flush();
                        }
                        if(slowo != slowoBuff) {
                            //System.out.println("wysylam kolor teraz");
                            slowo = slowoBuff;
                            flaga = 2;
                            daneOUT.writeByte(flaga);
                            daneOUT.writeUTF(slowo);
                            daneOUT.flush();
                        }
                        if(oldX != oldXBuff){
                            oldX = oldXBuff;
                            flaga = 3;
                            daneOUT.writeByte(flaga);
                            daneOUT.writeShort(oldX);
                            daneOUT.flush();
                        }
                        if( oldY != oldYBuff) {
                            oldY = oldYBuff;
                            flaga = 4;
                            daneOUT.writeByte(flaga);
                            daneOUT.writeShort(oldY);
                            daneOUT.flush();
                        }
                        if(currentX != currentXBuff) {
                            currentX = currentXBuff;
                            flaga = 5;
                            daneOUT.writeByte(flaga);
                            daneOUT.writeShort(currentX);
                            daneOUT.flush();
                        }
                        if(currentY != currentYBuff) {
                            currentY = currentYBuff;
                            flaga = 6;
                            daneOUT.writeByte(flaga);
                            daneOUT.writeShort(currentY);
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
