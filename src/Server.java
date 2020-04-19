import java.io.*;
import java.net.*;
import java.util.Random;

public class Server {
    private ServerSocket socketSerwer;
    private short nrGracza;
    private PolaczenieDoKlienta[] gracze;
    private int maxLiczbaGraczy;
    private short nrGraczaUWladzy;
    private byte kolor;
    private short currentX;
    private short currentY;
    private byte kolorBuff;
    private short currentXBuff;
    private short currentYBuff;
    private byte flaga;//1=kolor, 2 = slowo, 5 = currx, 6 = curry
    private String slowo = "";
    private String[] imiona;
    private short otrzymanyNR;
    private int seed;
    private boolean flagaImie = false;


   ///////////////////Metody
    public void przyjeciePolaczenia(){
        try {
            while (nrGracza < maxLiczbaGraczy) {
                Socket socket = socketSerwer.accept();//accept zwraca socket,(kliencka wersja serwer socketa)
                PolaczenieDoKlienta polaczenieDoKlienta = new PolaczenieDoKlienta(socket,nrGracza);
                gracze[nrGracza] = polaczenieDoKlienta;
                Thread t = new Thread(polaczenieDoKlienta);
                t.start();
                nrGracza++;
            }
        }catch(IOException e){
            System.out.println("przechwycono wyjątek z podlaczania klienta ");
        }
    }

    ///////////////////Konstruktor
    public Server(){
        this.maxLiczbaGraczy = 2;
        this.imiona = new String[maxLiczbaGraczy];
        this.imiona[0] = "";
        this.imiona[1] = "";
        this.seed = new Random().nextInt();
        Gra gra = new Gra(maxLiczbaGraczy);
        nrGraczaUWladzy =(short)gra.losujNrGracza();//trzeba przeniesc do zmiany tury czy czegos
        gracze = new PolaczenieDoKlienta[maxLiczbaGraczy];
        nrGracza = 0;
        try{
            socketSerwer = new ServerSocket(51724);

        }catch(IOException e){
            System.out.print("przechwycono wyjątek z kontrukcji socketa");
        }
    }
    private void wyslijDaneServ(){

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
        private void odczytajDaneServ() throws IOException{
            flaga = daneIN.readByte();
            if(nrGraczaUWladzy == playerID){
                if(flaga == 1)
                    kolorBuff = daneIN.readByte();
                if(flaga == 5)
                    currentXBuff = daneIN.readShort();
                if(flaga == 6)
                    currentYBuff = daneIN.readShort();
            }
            if(flaga == 2) {
                otrzymanyNR = daneIN.readShort();
                slowo = daneIN.readUTF();
            }
            if(flaga == 3) {
                otrzymanyNR = daneIN.readShort();
                imiona[otrzymanyNR] = daneIN.readUTF();
                flagaImie = true;

            }
        }
        private void wyslijDaneServ() throws IOException {
            if (!slowo.equals("") && otrzymanyNR != playerID) {
                flaga = 2;
                daneOUT.writeByte(flaga);
                daneOUT.writeShort(otrzymanyNR);
                daneOUT.writeUTF(slowo);
                daneOUT.flush();
                slowo = "";
            }
            if (flagaImie) {
                flaga = 3;
                daneOUT.writeByte(flaga);
                daneOUT.writeUTF(imiona[0]);
                daneOUT.writeUTF(imiona[1]);
                daneOUT.flush();
                flagaImie = false;
            }
            if (nrGraczaUWladzy != playerID) {
                if (kolor != kolorBuff) {
                    kolor = kolorBuff;
                    flaga = 1;
                    daneOUT.writeByte(flaga);
                    daneOUT.writeByte(kolor);
                    daneOUT.flush();
                }
                if (currentX != currentXBuff) {
                    currentX = currentXBuff;
                    flaga = 5;
                    daneOUT.writeByte(flaga);
                    daneOUT.writeShort(currentX);
                    daneOUT.flush();
                }
                if (currentY != currentYBuff) {
                    currentY = currentYBuff;
                    flaga = 6;
                    daneOUT.writeByte(flaga);
                    daneOUT.writeShort(currentY);
                    daneOUT.flush();
                }
            }
        }

        public void run(){

            new Thread(() -> {
                while (true) {
                    Thread.yield();
                    try {
                        wyslijDaneServ();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            try{
                daneOUT.writeShort(playerID);
                daneOUT.writeInt(seed);
                flaga = 0;
                daneOUT.writeByte(flaga);
                daneOUT.writeShort(nrGraczaUWladzy);
                daneOUT.flush();

                while(true){
                    odczytajDaneServ();
                }
            }catch (IOException e){

            }
        }
    }
}
